package org.beli.handler;

import org.beli.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGenericException(Exception exception) {
        ResponseDto errorResponse = new ResponseDto<>();
        errorResponse.setCode(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(exception.getMessage());

        return ResponseEntity.status(500).body(errorResponse);
    }
}
