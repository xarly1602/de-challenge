package com.walmart.org.options;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface DataChallengeOptions extends PipelineOptions {
    @Description("Input for the pipeline")
    @Default.String("/tmp/consoles.csv")
    String getConsolesFile();
    void setConsolesFile(String consolesFile);

    @Description("Output for the pipeline")
    @Default.String("/tmp/result.csv")
    String getResultsFile();
    void setResultsFile(String resultsFile);
}
