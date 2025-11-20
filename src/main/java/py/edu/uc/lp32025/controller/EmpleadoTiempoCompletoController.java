// src/main/java/py/edu/uc/lp32025/controller/EmpleadoTiempoCompletoController.java
package py.edu.uc.lp32025.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.BaseDTO;
import py.edu.uc.lp32025.dto.ImpuestoDTO;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;

import java.util.List;

@RestController
@RequestMapping("/api/empleados/tiempo-completo")
@RequiredArgsConstructor
public class EmpleadoTiempoCompletoController extends BaseController {

    private final EmpleadoTiempoCompletoService service;

    @GetMapping
    public ResponseEntity<BaseDTO> listar() {
        List<EmpleadoTiempoCompleto> empleados = service.listarTodos();
        return ok(empleados, "Lista de empleados de tiempo completo");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDTO> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(emp -> ok(emp, "Empleado encontrado"))
                .orElseGet(() -> notFound("Empleado no encontrado con ID " + id));
    }

    @PostMapping
    public ResponseEntity<BaseDTO> crear(@RequestBody EmpleadoTiempoCompleto empleado) {
        EmpleadoTiempoCompleto saved = service.guardar(empleado);
        return created(saved, "Empleado creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseDTO> actualizar(@PathVariable Long id, @RequestBody EmpleadoTiempoCompleto empleado) {
        return service.buscarPorId(id)
                .map(existente -> {
                    existente.setNombre(empleado.getNombre());
                    existente.setNumeroDocumento(empleado.getNumeroDocumento());
                    existente.setDepartamento(empleado.getDepartamento());
                    existente.setSalarioMensual(empleado.getSalarioMensual());
                    existente.setFechaNacimiento(empleado.getFechaNacimiento());
                    EmpleadoTiempoCompleto updated = service.guardar(existente);
                    return ok(updated, "Empleado actualizado");
                })
                .orElseGet(() -> notFound("Empleado no encontrado con ID " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseDTO> eliminar(@PathVariable Long id) {
        if (service.existe(id)) {
            service.eliminar(id);
            return noContent();
        }
        return notFound("Empleado no encontrado con ID " + id);
    }

    @GetMapping("/{id}/impuestos")
    public ResponseEntity<BaseDTO> consultarImpuestos(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(emp -> {
                    ImpuestoDTO dto = new ImpuestoDTO(
                            emp.calcularSalario(),
                            emp.calcularDeducciones(),
                            emp.calcularImpuestos(),
                            emp.validarDatosEspecificos(),
                            emp.obtenerInformacionCompleta()
                    );
                    return ok(dto, "Cálculo de impuestos");
                })
                .orElseGet(() -> notFound("Empleado no encontrado"));
    }
}