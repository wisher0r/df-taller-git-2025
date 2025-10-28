// src/main/java/py/edu/uc/lp32025/controller/EmpleadoBatchController.java
package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.dto.BatchResponseDTO;
import py.edu.uc.lp32025.dto.ImpuestoDTO;
import py.edu.uc.lp32025.service.EmpleadoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoBatchController {

    private final EmpleadoService service;

    public EmpleadoBatchController(EmpleadoService service) {
        this.service = service;
    }

    @PostMapping("/batch")
    public ResponseEntity<BatchResponseDTO> batch(@RequestBody List<Persona> empleados) {
        return ResponseEntity.ok(service.guardarEnBatch(empleados));
    }

    @GetMapping("/nomina")
    public ResponseEntity<Map<String, BigDecimal>> nomina() {
        return ResponseEntity.ok(service.calcularNominaTotal());
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<ImpuestoDTO>> reporte() {
        return ResponseEntity.ok(service.generarReporteCompleto());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<?>> porTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.obtenerPorTipo(tipo));
    }
}