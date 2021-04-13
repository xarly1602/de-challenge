package com.walmart.org.fn;

import com.walmart.org.model.Rank;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Arrays;
import java.util.List;

public class ShowResultsFn extends DoFn<List<KV<String, Rank>>, String> {
    private String resultsTitle;

    public ShowResultsFn(String resultsTitle) {
        this.resultsTitle = resultsTitle;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        for (KV<String, Rank> r : c.element()) {
            String output = String.join(",",Arrays.asList(r.getValue().getConsole().getName(),
                    r.getValue().getName(),
                    r.getValue().getMetascore().toString()));
            c.output(output);
        }
    }
}
