package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Order;
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

    @PostConstruct
    private void init() {
        orders.add(new Order(0, "order no 0", 1,1, null, null));
        orders.add(new Order(1, "order no 1",2,1, null, null));
        orders.add(new Order(2, "order no 2",1,2, null, null));
    }


}
