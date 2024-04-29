package com.accenture.GraphqlPoC.Resolver;

import com.accenture.GraphqlPoC.Model.Order;
import com.accenture.GraphqlPoC.Model.Shipment;
import com.accenture.GraphqlPoC.Service.ShipmentService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ShipmentResolver implements GraphQLResolver<Order> {
    private final ShipmentService shipmentService;

    public ShipmentResolver(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @QueryMapping
    public Shipment shipment(Order order) {
        return shipmentService.specificShipment(order.shipmentID());
    }
}
