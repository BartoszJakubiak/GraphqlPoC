package com.accenture.GraphqlPoC.DataFetcher;

import com.accenture.GraphqlPoC.Model.Builder;
import com.accenture.GraphqlPoC.Model.Parts.Order;
import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.OrderResolver;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.ReturnInfoResolver;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.ShipmentResolver;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CombinedDataFetcher implements DataFetcher<Object> {

    private final OrderResolver orderResolver;
    private final ReturnInfoResolver returnInfoResolver;
    private final ShipmentResolver shipmentResolver;
    private final Builder builder;

    @Autowired
    public CombinedDataFetcher(OrderResolver orderResolver,
                               ReturnInfoResolver returnInfoResolver,
                               ShipmentResolver shipmentResolver,
                               Builder builder) {
        this.orderResolver = orderResolver;
        this.returnInfoResolver = returnInfoResolver;
        this.shipmentResolver = shipmentResolver;
        this.builder = builder;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        /** Order object is mandatory **/
        String id = environment.getArgument("id");
        Integer orderId = Integer.valueOf(id);
        Order order = orderResolver.order(orderId);
        ReturnInfo returnInfo = null;
        Shipment shipment = null;


        /** ReturnInfo and Shipment are optional - depending on selectionSet's content.
         *  reutrnInfo() and shipment() methods on Resolver level are @Async **/
        DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();
        List<CompletableFuture<?>> asyncParts = new ArrayList<>();
        CompletableFuture<ReturnInfo> returnInfoAsync = null;
        CompletableFuture<Shipment> shipmentAsync = null;

        if (selectionSet.contains("returnInfo")) {
//            CompletableFuture<ReturnInfo> returnInfoAsync = returnInfoResolver.returnInfo(order.returnID());
            returnInfoAsync = returnInfoResolver.returnInfo(order.returnID());
            asyncParts.add(returnInfoAsync);
        }
        if (selectionSet.contains("shipment")) {
//            CompletableFuture<Shipment> shipmentAsync = shipmentResolver.shipment(order.shipmentID());
            shipmentAsync = shipmentResolver.shipment(order.shipmentID());
            asyncParts.add(shipmentAsync);
        }

        CompletableFuture<?>[] asyncPartsThreads = asyncParts.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(asyncPartsThreads).join();

        if (selectionSet.contains("returnInfo")) {
            returnInfo = returnInfoAsync.get();
        }
        if (selectionSet.contains("shipment")) {
            shipment = shipmentAsync.get();
        }

        builder.order(order)
                .shipment(shipment)
                .returnInfo(returnInfo);
        return builder.build();
    }



}
