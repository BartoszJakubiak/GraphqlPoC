package com.accenture.GraphqlPoC.Resolver.PartsResolvers;

import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import com.accenture.GraphqlPoC.Service.ShipmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ShipmentResolver {
    private final ShipmentService shipmentService;

    public ShipmentResolver(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

//    @QueryMapping
    public Shipment shipment(Integer id) {
        System.out.println("Shipment resolver - breakpoint");
        return shipmentService.specificShipment(id);
    }
}
