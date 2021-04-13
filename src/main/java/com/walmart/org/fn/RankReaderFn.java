package com.walmart.org.fn;

import avro.shaded.com.google.common.collect.ImmutableList;
import com.walmart.org.model.Console;
import com.walmart.org.model.Rank;
import com.walmart.org.utils.DbUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.vendor.grpc.v1p26p0.com.google.common.base.Splitter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class RankReaderFn extends DoFn<String, KV<String, Rank>> {
    List<String> headers = Arrays.asList("metascore","name","console","userscore","date");

    @ProcessElement
    public void processElement(ProcessContext c) {
        Splitter splitter = Splitter.on(Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
        List<String> row = c.element() == null ? Collections.emptyList(): ImmutableList.copyOf(splitter.split(c.element()));
        if (!headers.containsAll(row)) {
            Rank rank = new Rank(null,
                    Integer.parseInt(row.get(0)),
                    row.get(1),
                    new Console(null, row.get(2).replaceAll("\"","").trim(), null ),
                    row.get(3),
                    row.get(4));
            DbUtils.insertRank(rank.getMetascore(), rank.getName(), rank.getConsole().getName(), rank.getUserscore(), rank.getDate());
            c.output(KV.of(rank.getConsole().getName(), rank));
        }
    }
}
