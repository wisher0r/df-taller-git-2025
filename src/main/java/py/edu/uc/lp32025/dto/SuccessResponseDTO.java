package py.edu.uc.lp32025.dto;

public class SuccessResponseDTO<T> extends BaseDTO {
    private T data;

    public SuccessResponseDTO(T data, String userMessage) {
        super(0, null, userMessage); // status 0 = éxito
        this.data = data;
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
