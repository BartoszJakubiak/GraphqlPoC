package com.accenture.GraphqlPoC.Model;

public record Order (Integer id,
                     String info,
                     Integer shipmentID,
                     Integer returnID,
                     Shipment shipment,
                     ReturnInfo returnInfo) {
}
