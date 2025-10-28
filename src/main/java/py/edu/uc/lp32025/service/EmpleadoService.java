// src/main/java/py/edu/uc/lp32025/service/EmpleadoService.java
package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.dto.BatchResponseDTO;
import py.edu.uc.lp32025.dto.ImpuestoDTO;
import py.edu.uc.lp32025.repository.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private final EmpleadoTiempoCompletoRepository tcRepo;
    private final EmpleadoPorHoraRepository phRepo;
    private final ContratistaRepository conRepo;

    public EmpleadoService(
            EmpleadoTiempoCompletoRepository tcRepo,
            EmpleadoPorHoraRepository phRepo,
            ContratistaRepository conRepo) {
        this.tcRepo = tcRepo;
        this.phRepo = phRepo;
        this.conRepo = conRepo;
    }

    // ========================================
    // 4.1 Persistencia en Batch (chunks de 100)
    // ========================================
    @Transactional
    public BatchResponseDTO guardarEnBatch(List<Persona> empleados) {
        List<String> errores = new ArrayList<>();
        List<Persona> validos = new ArrayList<>();

        // Validar todos con polimorfismo
        for (int i = 0; i < empleados.size(); i++) {
            Persona p = empleados.get(i);
            if (p.validarDatosEspecificos()) {
                validos.add(p);
            } else {
                String tipo = p.getClass().getSimpleName();
                String id = p.getId() != null ? p.getId().toString() : "nuevo";
                errores.add("Índice " + i + " [" + tipo + " ID=" + id + "]: datos inválidos");
            }
        }

        // Guardar en chunks de 100
        int chunkSize = 100;
        for (int i = 0; i < validos.size(); i += chunkSize) {
            List<Persona> chunk = validos.subList(i, Math.min(i + chunkSize, validos.size()));
            guardarChunk(chunk);
        }

        return new BatchResponseDTO(
                empleados.size(),
                validos.size(),
                errores.size(),
                errores
        );
    }

    private void guardarChunk(List<Persona> chunk) {
        List<EmpleadoTiempoCompleto> tc = filtrar(chunk, EmpleadoTiempoCompleto.class);
        List<EmpleadoPorHora> ph = filtrar(chunk, EmpleadoPorHora.class);
        List<Contratista> con = filtrar(chunk, Contratista.class);

        if (!tc.isEmpty()) tcRepo.saveAll(tc);
        if (!ph.isEmpty()) phRepo.saveAll(ph);
        if (!con.isEmpty()) conRepo.saveAll(con);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> filtrar(List<Persona> lista, Class<T> tipo) {
        return lista.stream()
                .filter(tipo::isInstance)
                .map(tipo::cast)
                .toList();
    }

    // ========================================
    // 4.2 Cálculo de Nómina Total por Tipo
    // ========================================
    public Map<String, BigDecimal> calcularNominaTotal() {
        Map<String, BigDecimal> nomina = new HashMap<>();

        BigDecimal totalTC = tcRepo.findAll().stream()
                .map(EmpleadoTiempoCompleto::calcularSalario)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        nomina.put("EmpleadoTiempoCompleto", totalTC);

        BigDecimal totalPH = phRepo.findAll().stream()
                .map(EmpleadoPorHora::calcularSalario)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        nomina.put("EmpleadoPorHora", totalPH);

        BigDecimal totalCon = conRepo.findAll().stream()
                .map(Contratista::calcularSalario)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        nomina.put("Contratista", totalCon);

        return nomina;
    }

    // ========================================
    // 4.3 Reporte con Polimorfismo (usa ImpuestoDTO)
    // ========================================
    public List<ImpuestoDTO> generarReporteCompleto() {
        List<Persona> todos = new ArrayList<>();
        todos.addAll(tcRepo.findAll());
        todos.addAll(phRepo.findAll());
        todos.addAll(conRepo.findAll());

        return todos.stream()
                .map(emp -> new ImpuestoDTO(
                        emp.calcularSalario(),
                        emp.calcularDeducciones(),
                        emp.calcularImpuestos(),
                        emp.validarDatosEspecificos(),
                        emp.obtenerInformacionCompleta()
                ))
                .toList();
    }

    // ========================================
    // Obtener empleados por tipo
    // ========================================
    public List<? extends Persona> obtenerPorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "tiempocompleto" -> tcRepo.findAll();
            case "porhora" -> phRepo.findAll();
            case "contratista" -> conRepo.findAll();
            default -> throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        };
    }
}