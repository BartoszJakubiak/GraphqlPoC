package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.Shipment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Shipment> allShipment(DataFetchingEnvironment environment) {
        /** Here could be different logic of hitting outside API based on environment's content */
        String url = "http://localhost:3001/allShipment";
        RestTemplate restTemplate = new RestTemplate();
        String jsonFile = restTemplate.getForObject(url,String.class);
        List<Shipment> shipmentList = new ArrayList<>();
        try {
            shipmentList = objectMapper.readValue(jsonFile, new TypeReference<List<Shipment>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (environment.containsArgument("delayed")) {
            return shipmentList.stream()
                    .filter(l -> l.delayed().equals(environment.getArgument("delayed")))
                    .collect(Collectors.toList());
        }
        return shipmentList;
    }
}
