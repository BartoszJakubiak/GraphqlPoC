package com.accenture.GraphqlPoC.Resolver.PartsResolvers;

import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.accenture.GraphqlPoC.Service.ReturnService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
public class ReturnInfoResolver {
    private final ReturnService returnService;

    public ReturnInfoResolver(ReturnService returnService) {
        this.returnService = returnService;
    }

    @Async
    public CompletableFuture<List<ReturnInfo>> allReturnInfo(DataFetchingEnvironment environment) {
        System.out.println("ReturnInfo resolver - breakpoint");
        return CompletableFuture.completedFuture(returnService.allReturnInfo(environment));
    }
}
