package com.walmart.org.fn;

import com.walmart.org.model.Company;
import com.walmart.org.model.Console;
import com.walmart.org.utils.DbUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConsoleReaderFn extends DoFn<String, KV<String, Console>> {

    List<String> headers = Arrays.asList("console","company");

    @ProcessElement
    public void processElement(ProcessContext c) {
        String[] row = c.element() == null ?
                (String[]) Collections.emptyList().toArray() : c.element().split(",");
        if (!headers.containsAll(Arrays.asList(row))) {
            Company company = new Company(null, row[1]);
            Long companyId = DbUtils.insertCompany(company.getName());
            Console console = new Console(null, row[0], company);
            DbUtils.insertConsole(console.getName(), companyId);
            c.output(KV.of(console.getName(), console));
        }
    }
}
