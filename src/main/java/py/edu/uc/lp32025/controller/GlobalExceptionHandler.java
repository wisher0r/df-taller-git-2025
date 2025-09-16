package py.edu.uc.lp32025.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import py.edu.uc.lp32025.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Errores de negocio (fecha futura)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("INVALID_INPUT", ex.getMessage()));
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidJson(HttpMessageNotReadableException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO("INVALID_JSON", "JSON mal formado o tipo de dato inválido");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    // Cualquier otro error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("INTERNAL_ERROR", "Ocurrió un error inesperado"));
    }
}
