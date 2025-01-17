package com.walmart.org.fn;

import com.walmart.org.model.Rank;
import org.apache.beam.sdk.transforms.SerializableComparator;
import org.apache.beam.sdk.values.KV;

public class BestOfAllFn implements SerializableComparator<KV<String, Rank>> {
    @Override
    public int compare(KV<String, Rank> stringRankKV, KV<String, Rank> t1) {
        return stringRankKV.getValue().compareTo(t1.getValue());    }
}
