package com.codehustle.chatterpark.exception;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class ChattersParkExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity handleDuplicateRequest(Exception e,HttpServletResponse response) throws IOException {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Optional.of(Map.of("msg", e.getMessage()))));
    }
}
