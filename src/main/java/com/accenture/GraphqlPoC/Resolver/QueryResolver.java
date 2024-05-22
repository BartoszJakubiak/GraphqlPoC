package com.accenture.GraphqlPoC.Resolver;

import com.accenture.GraphqlPoC.DataFetcher.AllOrdersDataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QueryResolver {

    private final AllOrdersDataFetcher allOrdersDataFetcher;

    public QueryResolver(AllOrdersDataFetcher allOrdersDataFetcher) {
        this.allOrdersDataFetcher = allOrdersDataFetcher;
    }

    @QueryMapping
    public Object allOrderSummary(DataFetchingEnvironment environment) {
        try {
            return allOrdersDataFetcher.get(environment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
