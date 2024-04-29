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

    public Builder order(Order order) {
        this.order = order;
        return this;
    }

    public Builder shipment(Shipment shipment) {
        this.shipment = shipment;
        return this;
    }

    public Builder returnInfo(ReturnInfo returnInfo) {
        this.returnInfo = returnInfo;
        return this;
    }

    public OrderSummary build() {
        return new OrderSummary(this);
    }
}


