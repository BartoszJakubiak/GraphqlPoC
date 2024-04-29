package com.accenture.GraphqlPoC.Resolver;

import com.accenture.GraphqlPoC.Model.Order;
import com.accenture.GraphqlPoC.Model.ReturnInfo;
import com.accenture.GraphqlPoC.Service.ReturnService;
import graphql.kickstart.tools.GraphQLResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ReturnInfoResolver implements GraphQLResolver<ReturnInfo> {
    private final ReturnService returnService;
    public ReturnInfoResolver(ReturnService returnService) {
        this.returnService = returnService;
    }

    @QueryMapping
    public ReturnInfo returnInfo(@Argument Integer id) {
        System.out.println("ReturnInfo resolver - breakpoint");
        return returnService.specificReturn(id);
    }

}
