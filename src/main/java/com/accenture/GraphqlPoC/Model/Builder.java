package com.accenture.GraphqlPoC.Model;

import com.accenture.GraphqlPoC.Model.Parts.Order;
import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import org.springframework.stereotype.Component;

@Component
public class Builder {
    Order order;
    Shipment shipment;
    ReturnInfo returnInfo;

    public Builder() {}

    public void order(Order order) {
        this.order = order;
    }

    public void shipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void returnInfo(ReturnInfo returnInfo) {
        this.returnInfo = returnInfo;
    }

    public OrderSummary build() {
        OrderSummary orderSummary = new OrderSummary(this);
        clear();
        return orderSummary;
    }

    public void clear() {
        this.order = null;
        this.shipment = null;
        this.returnInfo = null;
    }
}


