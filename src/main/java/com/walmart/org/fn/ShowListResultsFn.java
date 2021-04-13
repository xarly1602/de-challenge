package com.walmart.org.fn;

import com.walmart.org.model.Rank;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Arrays;
import java.util.List;

public class ShowListResultsFn extends DoFn<KV<String, List<Rank>>, String> {
    private String resultsTitle;

    public ShowListResultsFn(String resultsTitle) {
        this.resultsTitle = resultsTitle;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        for (Rank r : c.element().getValue()) {
            String output = String.join(",", Arrays.asList(r.getConsole().getName(),
                    r.getName(),
                    r.getMetascore().toString()));
            //System.out.println(output);
            c.output(output);
        }
    }
}
