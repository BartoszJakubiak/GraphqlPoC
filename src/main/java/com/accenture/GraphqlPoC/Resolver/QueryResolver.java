package com.accenture.GraphqlPoC.Resolver;

import com.accenture.GraphqlPoC.DataFetcher.AllOrdersDataFetcher;
import com.accenture.GraphqlPoC.DataFetcher.SpecificOrderDataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QueryResolver {

    private final SpecificOrderDataFetcher specificOrderDataFetcher;
    private final AllOrdersDataFetcher allOrdersDataFetcher;

    public QueryResolver(SpecificOrderDataFetcher specificOrderDataFetcher, AllOrdersDataFetcher allOrdersDataFetcher) {
        this.specificOrderDataFetcher = specificOrderDataFetcher;
        this.allOrdersDataFetcher = allOrdersDataFetcher;
    }

    @QueryMapping
    public Object orderSummary(DataFetchingEnvironment environment) {
        try {
            return specificOrderDataFetcher.get(environment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
