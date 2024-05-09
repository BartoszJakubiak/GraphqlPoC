package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private List<Order> orders = new ArrayList<>();

    public Order specificOrder(Integer id) {
        return orders.get(id);
    }
    public List<Order> allOrders() {
        return orders;
    }

    @PostConstruct
    private void init() {
        orders.add(new Order(0, "order no 0", 0,0));
        orders.add(new Order(1, "order no 1",1,0));
        orders.add(new Order(2, "order no 2",0,1));
    }


}
