package com.accenture.GraphqlPoC.Model.Parts;

public record Order (Integer id,
                     String info,
                     Integer shipmentID,
                     Integer returnID) {
}
