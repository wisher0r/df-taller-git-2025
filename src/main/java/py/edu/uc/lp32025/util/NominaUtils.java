package py.edu.uc.lp32025.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import py.edu.uc.lp32025.domain.Persona;

import java.util.List;
import java.util.stream.Collectors;

public class NominaUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static int calcularTotalDiasSolicitados(List<Persona> empleados) {
        return empleados.stream()
                .mapToInt(p -> p.getDiasVacacionesSolicitados() + p.getDiasPermisosSolicitados())
                .sum();
    }

    public static String generarReporteSuperacion(int umbral, List<Persona> empleados) throws JsonProcessingException {
        List<Persona> superan = empleados.stream()
                .filter(p -> (p.getDiasVacacionesSolicitados() + p.getDiasPermisosSolicitados()) > umbral)
                .collect(Collectors.toList());
        return mapper.writeValueAsString(superan);
    }
}