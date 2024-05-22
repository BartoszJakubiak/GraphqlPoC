package com.accenture.GraphqlPoC.DataFetcher;

import com.accenture.GraphqlPoC.Model.Builder;
import com.accenture.GraphqlPoC.Model.OrderSummary;
import com.accenture.GraphqlPoC.Model.Parts.Order;
import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.OrderResolver;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.ReturnInfoResolver;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.ShipmentResolver;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class AllOrdersDataFetcher implements DataFetcher<Object> {

    private final OrderResolver orderResolver;
    private final ReturnInfoResolver returnInfoResolver;
    private final ShipmentResolver shipmentResolver;
    private final Builder builder;

    public AllOrdersDataFetcher(OrderResolver or, ReturnInfoResolver rr, ShipmentResolver sr, Builder b) {
        this.orderResolver = or;
        this.returnInfoResolver = rr;
        this.shipmentResolver = sr;
        this.builder = b;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        List<Order> orders = orderResolver.allOrders(environment);

        DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();
        List<CompletableFuture<?>> asyncParts = new ArrayList<>();
        CompletableFuture<List<ReturnInfo>> returnInfoAsync = null;
        CompletableFuture<List<Shipment>> shipmentAsync = null;

        if (selectionSet.contains("returnInfo")) {
            returnInfoAsync = returnInfoResolver.allReturnInfo(environment);
            asyncParts.add(returnInfoAsync);
        }
        if (selectionSet.contains("shipment")) {
            shipmentAsync = shipmentResolver.allShipment(environment);
            asyncParts.add(shipmentAsync);
        }

        CompletableFuture<?>[] asyncPartsThreads = asyncParts.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(asyncPartsThreads).join();

        /** Stitching results together - returning only complete OrderSummaries*/

        List<OrderSummary> orderSummaries = new ArrayList<>();
        for (Order order : orders) {
            builder.order(order);
            if (selectionSet.contains("returnInfo")) {
                ReturnInfo returnInfo = returnInfoAsync.get().stream()
                        .filter(l -> l.id().equals(order.returnID()))
                        .findFirst()
                        .orElse(null);
                if (returnInfo == null) {
                    builder.clear();
                    continue;
                }
                builder.returnInfo(returnInfo);

            }
            if (selectionSet.contains("shipment")) {
                Shipment shipment = shipmentAsync.get().stream()
                        .filter(l -> l.id().equals(order.shipmentID()))
                        .findFirst()
                        .orElse(null);
                if (shipment == null) {
                    builder.clear();
                    continue;
                }
                builder.shipment(shipment);
            }
            orderSummaries.add(builder.build());
        }
        return orderSummaries;
    }
}
