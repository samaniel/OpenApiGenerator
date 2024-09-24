package com.samaniel.openapi.service.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.samaniel.openapi.model.ResponseDto;

public interface ConnectionService {

    ResponseDto request() throws JsonProcessingException;

}
