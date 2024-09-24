package com.samaniel.openapi.controller;

import com.samaniel.openapi.api.TokenApiDelegate;
import com.samaniel.openapi.model.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.samaniel.openapi.service.contract.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Controller implements TokenApiDelegate {

    private final ConnectionService connectionService;

    @Override
    public ResponseEntity<ResponseDto> token() {
        ResponseDto response = null;
        HttpStatus httpStatus;
        try {
            response = connectionService.request();
            httpStatus = HttpStatus.OK;
        } catch (JsonProcessingException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

}
