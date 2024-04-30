package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShipmentService {

    private final ObjectMapper objectMapper;

    public ShipmentService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public Shipment specificShipment(Integer id) {
        String url = String.format("http://localhost:3001/shipment/%d", id);
        RestTemplate restTemplate = new RestTemplate();
        String jsonFile = restTemplate.getForObject(url,String.class);
        try {
            return objectMapper.readValue(jsonFile, Shipment.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
