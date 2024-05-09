package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReturnService {

    private final ObjectMapper objectMapper;

    public ReturnService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public ReturnInfo specificReturn(Integer id) {
        String url = String.format("http://localhost:3001/return/%d",id);
//        String url = "http://localhost:3001/return/1";
        RestTemplate restTemplate = new RestTemplate();
        String jsonFile = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readValue(jsonFile, ReturnInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ReturnInfo> allReturnInfo() {
        String url = "http://localhost:3001/allReturn";
        RestTemplate restTemplate = new RestTemplate();
        String jsonFile = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readValue(jsonFile, new TypeReference<List<ReturnInfo>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
