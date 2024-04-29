package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShipmentService {
    public Shipment specificShipment(Integer id) {
        String url = String.format("http://localhost:3001/shipment/%d", id);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url,Shipment.class);
    }
}
