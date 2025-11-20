package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import py.edu.uc.lp32025.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DiasInsuficientesException.class)
    public ResponseEntity<ErrorResponseDTO> handleDiasInsuficientes(DiasInsuficientesException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("DIAS_INSUFICIENTES", ex.getMessage()));
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmpleadoNoEncontrado(EmpleadoNoEncontradoException ex) {
        return ResponseEntity.status(404)
                .body(new ErrorResponseDTO("EMPLEADO_NO_ENCONTRADO", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneral(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDTO("ERROR_INTERNO", "Ocurrió un error inesperado"));
    }

    @ExceptionHandler(FechaNacimientoInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleFechaNacimientoInvalida(FechaNacimientoInvalidaException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("FECHA_NACIMIENTO_INVALIDA", ex.getMessage()));
    }
}