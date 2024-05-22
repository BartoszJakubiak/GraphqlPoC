package com.accenture.GraphqlPoC.Service;

import com.accenture.GraphqlPoC.Model.Parts.ReturnInfo;
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
public class ReturnService {

    private final ObjectMapper objectMapper;

    public ReturnService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<ReturnInfo> allReturnInfo(DataFetchingEnvironment environment) {
        /** Here could be different logic of hitting outside API based on environment's content */
        String url = "http://localhost:3001/allReturn";
        RestTemplate restTemplate = new RestTemplate();
        String jsonFile = restTemplate.getForObject(url, String.class);
        List<ReturnInfo> returnInfoList = new ArrayList<>();
        try {
            returnInfoList = objectMapper.readValue(jsonFile, new TypeReference<List<ReturnInfo>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (environment.containsArgument("accepted")) {
            return returnInfoList.stream()
                    .filter(l -> l.accepted().equals(environment.getArgument("accepted")))
                    .collect(Collectors.toList());
        }
        return returnInfoList;
    }
}
