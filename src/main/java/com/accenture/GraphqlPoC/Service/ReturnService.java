package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReturnService {

    private final ObjectMapper objectMapper;

    public ReturnService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public ReturnInfo specificReturn(Integer id) {
//        String url = String.format("http://localhost:3001/return/%d",id);
        String url = "http://localhost:3001/return/1";
        RestTemplate restTemplate = new RestTemplate();
        String jsonFile = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readValue(jsonFile, ReturnInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
