package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.Order;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private List<Order> orders = new ArrayList<>();

    public List<Order> allOrders(DataFetchingEnvironment environment) {
        List<Order> orderList = new ArrayList<>();

        if (environment.containsArgument("id")) {
            orderList = orders.stream()
                    .filter(l -> l.id().equals(Integer.valueOf(environment.getArgument("id"))))
                    .collect(Collectors.toList());
            return orderList;
        }
        orderList.addAll(orders);
        return orderList;
    }

    @PostConstruct
    private void init() {
        orders.add(new Order(0, "order no 0", 0,0));
        orders.add(new Order(1, "order no 1",1,0));
        orders.add(new Order(2, "order no 2",0,1));
    }


}
