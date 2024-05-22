package com.accenture.GraphqlPoC.Resolver.PartsResolvers;

import com.accenture.GraphqlPoC.Model.Parts.Order;
import com.accenture.GraphqlPoC.Service.OrderService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class OrderResolver {

    private final OrderService orderService;

    public OrderResolver(OrderService orderService) {
        this.orderService = orderService;
    }


    public List<Order> allOrders(DataFetchingEnvironment environment) {
        return orderService.allOrders(environment);
    }
}
