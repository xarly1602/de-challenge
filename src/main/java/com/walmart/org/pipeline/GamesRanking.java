package com.walmart.org.pipeline;

import com.walmart.org.fn.*;
import com.walmart.org.model.Console;
import com.walmart.org.model.Rank;
import com.walmart.org.options.DataChallengeOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Top;
import org.apache.beam.sdk.transforms.Wait;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GamesRanking implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        PipelineOptionsFactory.register(DataChallengeOptions.class);
        DataChallengeOptions options = PipelineOptionsFactory.fromArgs(args)
                .withValidation()
                .as(DataChallengeOptions.class);
        runPipeline(options);
    }

    private void runPipeline(DataChallengeOptions options) {
        // Build pipeline
        Pipeline p = Pipeline.create(options);
        // Read input files
        PCollection<String> consoles = p.apply(TextIO.read().from(options.getConsolesFile()));
        PCollection<String> ranks = p.apply(TextIO.read().from(options.getResultsFile()));

        // Transform inputs to Key/Value pairs
        PCollection<KV<String, Console>> consolesCollection = consoles.apply(ParDo.of(new ConsoleReaderFn()));
        PCollection<KV<String, Rank>> ranksCollection = ranks.apply(Wait.on(consolesCollection)).apply(ParDo.of(new RankReaderFn()));

        // Get best and worst games for all consoles
        PCollection<List<KV<String, Rank>>> best = ranksCollection.apply(Top.of(10, new BestOfAllFn()));
        PCollection<List<KV<String, Rank>>> worst = ranksCollection.apply(Top.of(10, new WorstOfAllFn()));

        // Get best and worst games per console
        PCollection<KV<String, List<Rank>>> largest10ValuesPerKey = ranksCollection.apply(Top.largestPerKey(10));
        PCollection<KV<String, List<Rank>>> smallest10ValuesPerKey = ranksCollection.apply(Top.smallestPerKey(10));

        // Write all results to output files
        best.apply(ParDo.of(new ShowResultsFn("Best Games"))).apply(TextIO.write().to("/tmp/bestOfAll.txt").withoutSharding());
        worst.apply(ParDo.of(new ShowResultsFn("Worst Games"))).apply(TextIO.write().to("/tmp/worstOfAll.txt").withoutSharding());
        smallest10ValuesPerKey.apply(ParDo.of(new ShowListResultsFn("Worst Games Per console"))).apply(TextIO.write().to("/tmp/worstPerConsole.txt").withoutSharding());
        largest10ValuesPerKey.apply(ParDo.of(new ShowListResultsFn("Best Games Per console"))).apply(TextIO.write().to("/tmp/bestPerConsole.txt").withoutSharding());

        // Run pipeline
        p.run().waitUntilFinish();
    }
}
