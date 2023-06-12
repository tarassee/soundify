package com.tarasiuk.soundify.exception.handler;

import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.exception.StorageInfoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(StorageInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> audioNotFoundException(Exception ex) {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> invalidRequestException(Exception ex) {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(Exception ex) {
        return createResponse(HttpStatus.BAD_REQUEST, "Validation failed.");
    }

    private ResponseEntity<ErrorResponse> createResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ErrorResponse(httpStatus.value(), message, Instant.now().toString()), httpStatus);
    }

    private record ErrorResponse(int status, String message, String timestamp) {
    }
}
