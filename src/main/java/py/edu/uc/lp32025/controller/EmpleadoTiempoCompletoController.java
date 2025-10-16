package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoTiempoCompletoController {

    private final EmpleadoTiempoCompletoService service;

    public EmpleadoTiempoCompletoController(EmpleadoTiempoCompletoService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmpleadoTiempoCompleto> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public EmpleadoTiempoCompleto obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID " + id));
    }

    @PostMapping
    public EmpleadoTiempoCompleto crear(@RequestBody EmpleadoTiempoCompleto empleado) {
        return service.guardar(empleado);
    }

    @PutMapping("/{id}")
    public EmpleadoTiempoCompleto actualizar(@PathVariable Long id, @RequestBody EmpleadoTiempoCompleto empleado) {
        return service.buscarPorId(id)
                .map(existente -> {
                    existente.setNombre(empleado.getNombre());
                    existente.setNumeroDocumento(empleado.getNumeroDocumento());
                    existente.setDepartamento(empleado.getDepartamento());
                    existente.setSalarioMensual(empleado.getSalarioMensual());
                    existente.setFechaNacimiento(empleado.getFechaNacimiento());
                    return service.guardar(existente);
                })
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID " + id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
