package com.accenture.GraphqlPoC.Resolver;

import com.accenture.GraphqlPoC.Model.Order;
import com.accenture.GraphqlPoC.Service.OrderService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
public class OrderResolver implements GraphQLQueryResolver {

    private final OrderService orderService;

    public OrderResolver(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryMapping
    public Order order(@Argument Integer id) {
        System.out.println("Order resolver - breakpoint");
        return orderService.specificOrder(id);
    }
}