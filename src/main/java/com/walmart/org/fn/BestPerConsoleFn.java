package com.walmart.org.fn;

import com.walmart.org.model.Console;
import com.walmart.org.model.Rank;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.values.KV;

public class BestPerConsoleFn extends DoFn<KV<String, CoGbkResult>, String> {
    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<String, CoGbkResult> e = c.element();
        String console = e.getKey();
        Iterable<Console> consoleIter = e.getValue().getAll("consolesTag");
        Iterable<Rank> ranksIter = e.getValue().getAll("ranksTag");
        c.output("");
    }
}
