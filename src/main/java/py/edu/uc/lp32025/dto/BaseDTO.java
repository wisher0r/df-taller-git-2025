package py.edu.uc.lp32025.dto;

import java.time.LocalDateTime;

public class BaseDTO {
    private int status;          // 0 = éxito, 1 = error, etc.
    private String errorCode;    // Código técnico del error
    private String userMessage;  // Mensaje legible para el usuario
    private LocalDateTime timestamp; // Momento de la respuesta

    // Constructor vacío
    public BaseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor completo
    public BaseDTO(int status, String errorCode, String userMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
