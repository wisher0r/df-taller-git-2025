package py.edu.uc.lp32025.dto;

public class ErrorResponseDTO extends BaseDTO {
    public ErrorResponseDTO(String errorCode, String userMessage) {
        super(1, errorCode, userMessage); // status 1 = error
    }
}