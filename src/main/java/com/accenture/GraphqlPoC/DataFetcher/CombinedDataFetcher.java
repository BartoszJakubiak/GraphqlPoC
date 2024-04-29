package com.accenture.GraphqlPoC.DataFetcher;

import com.accenture.GraphqlPoC.Model.Builder;
import com.accenture.GraphqlPoC.Model.Parts.Order;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.OrderResolver;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.ReturnInfoResolver;
import com.accenture.GraphqlPoC.Resolver.PartsResolvers.ShipmentResolver;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        String id = environment.getArgument("id");
        Integer orderId = Integer.valueOf(id);
        Order order = orderResolver.order(orderId);
        builder.order(order);

        if (environment.getSelectionSet().contains("returnInfo")) {
            builder.returnInfo(returnInfoResolver.returnInfo(order.returnID()));
        }

        if (environment.getSelectionSet().contains("shipment")) {
            builder.shipment(shipmentResolver.shipment(order.shipmentID()));
        }

        return builder.build();
    }
}
