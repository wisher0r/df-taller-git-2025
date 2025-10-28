// src/main/java/py/edu/uc/lp32025/dto/BatchResponseDTO.java
package py.edu.uc.lp32025.dto;

import java.util.List;

public record BatchResponseDTO(
        int totalRecibidos,
        int guardados,
        int fallidos,
        List<String> errores
) {}