package com.samaniel.openapi.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoEndpointException extends Exception {

      public NoEndpointException(String message)
      {
         super(message);
      }
 }