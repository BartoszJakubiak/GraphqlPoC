package com.accenture.GraphqlPoC.Resolver.PartsResolvers;

import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import com.accenture.GraphqlPoC.Service.ShipmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CompletableFuture;

@Controller
public class ShipmentResolver {
    private final ShipmentService shipmentService;

    public ShipmentResolver(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Async
    @QueryMapping
    public CompletableFuture<Shipment> shipment(@Argument Integer id) {
        System.out.println("Shipment resolver - breakpoint");
        Shipment shipment = shipmentService.specificShipment(id);
        return CompletableFuture.completedFuture(shipment);
    }
}
