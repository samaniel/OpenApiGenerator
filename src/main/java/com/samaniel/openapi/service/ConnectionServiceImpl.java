package com.samaniel.openapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samaniel.openapi.exceptions.NoEndpointException;
import com.samaniel.openapi.model.ResponseDto;
import com.samaniel.openapi.service.contract.ConnectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

/**
 * This class connects with an endpoint wich has to be available, otherwise
 */
@Service
public class ConnectionServiceImpl implements ConnectionService {

    Logger logger = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final static String ERROR_MESSAGE = "Failed, check that the other microservice is up";
    private final static String URL = "http://localhost:8080";
    private final static String ENDPOINT = "/token";

    @Autowired
    public ConnectionServiceImpl(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl(URL).build();
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseDto request() throws JsonProcessingException {
        String body = getDummyBody();
        String responseString = postRequest(body);
        return mapToDto(responseString);
    }

    private String getDummyBody() throws JsonProcessingException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("username", "auth-vivelibre");
        dataMap.put("password", "password");
        return mapToString(dataMap);
    }

    @ExceptionHandler({NoEndpointException.class})
    private String postRequest(String body) {
        return webClient.post()
                .uri(ENDPOINT)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(throwable -> logger.error(ERROR_MESSAGE, throwable))
                .onErrorReturn(ERROR_MESSAGE)
                .block();
    }

    private ResponseDto mapToDto(String responseString) throws JsonProcessingException {
        return objectMapper.readValue(responseString, ResponseDto.class);
    }

    private String mapToString(Map<String, Object> dataMap) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dataMap);
    }

}
