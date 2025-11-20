// src/main/java/py/edu/uc/lp32025/controller/BaseController.java
package py.edu.uc.lp32025.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import py.edu.uc.lp32025.dto.BaseDTO;
import py.edu.uc.lp32025.dto.ErrorResponseDTO;
import py.edu.uc.lp32025.dto.SuccessResponseDTO;

public abstract class BaseController {

    protected <T> ResponseEntity<BaseDTO> ok(T data, String message) {
        return ResponseEntity.ok(new SuccessResponseDTO<>(data, message));
    }

    protected <T> ResponseEntity<BaseDTO> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponseDTO<>(data, message));
    }

    protected ResponseEntity<BaseDTO> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<BaseDTO> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("NOT_FOUND", message));
    }

    protected ResponseEntity<BaseDTO> badRequest(String message) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("BAD_REQUEST", message));
    }
}