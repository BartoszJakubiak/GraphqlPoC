package com.accenture.GraphqlPoC.Resolver;

import com.accenture.GraphqlPoC.DataFetcher.CombinedDataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class QueryResolver {

    private final CombinedDataFetcher combinedDataFetcher;

    public QueryResolver(CombinedDataFetcher combinedDataFetcher) {
        this.combinedDataFetcher = combinedDataFetcher;
    }

    @QueryMapping
    public Object orderSummary(DataFetchingEnvironment environment) {
        try {
            return combinedDataFetcher.get(environment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
