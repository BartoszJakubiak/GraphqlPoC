package com.accenture.GraphqlPoC.Model;

import com.accenture.GraphqlPoC.Model.Parts.Order;
import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.accenture.GraphqlPoC.Model.Parts.Shipment;

public class OrderSummary {

    private final Order order;
    private final Shipment shipment;
    private final ReturnInfo returnInfo;

    OrderSummary(Builder builder) {
        this.order = builder.order;
        this.shipment = builder.shipment;
        this.returnInfo = builder.returnInfo;
    }

    public Order getOrder() {
        return order;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public ReturnInfo getReturnInfo() {
        return returnInfo;
    }
}

