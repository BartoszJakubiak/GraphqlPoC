package com.accenture.GraphqlPoC.Resolver.PartsResolvers;

import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.accenture.GraphqlPoC.Service.ReturnService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ReturnInfoResolver {
    private final ReturnService returnService;
    public ReturnInfoResolver(ReturnService returnService) {
        this.returnService = returnService;
    }

//    @QueryMapping
    public ReturnInfo returnInfo(Integer id) {
        System.out.println("ReturnInfo resolver - breakpoint");
        return returnService.specificReturn(id);
    }

}
