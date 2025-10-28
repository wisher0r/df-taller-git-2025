// src/main/java/py/edu/uc/lp32025/controller/GlobalExceptionHandler.java
package py.edu.uc.lp32025.controller;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, Object> body = Map.of(
                "errorCode", "SERVER_ERROR",
                "userMessage", ex.getMessage(),
                "status", 1,
                "timestamp", Instant.now().toString()
        );
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonErrors(HttpMessageNotReadableException ex) {
        Map<String, Object> body = Map.of(
                "errorCode", "INVALID_JSON",
                "userMessage", "JSON mal formado o tipo de dato inválido: " + ex.getMostSpecificCause().getMessage(),
                "status", 1,
                "timestamp", Instant.now().toString()
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}