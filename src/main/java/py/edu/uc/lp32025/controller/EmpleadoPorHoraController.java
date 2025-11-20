// src/main/java/py/edu/uc/lp32025/controller/EmpleadoPorHoraController.java
package py.edu.uc.lp32025.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.dto.BaseDTO;
import py.edu.uc.lp32025.repository.EmpleadoPorHoraRepository;

import java.util.List;

@RestController
@RequestMapping("/api/empleados/por-hora")
@RequiredArgsConstructor
public class EmpleadoPorHoraController extends BaseController {

    private final EmpleadoPorHoraRepository repository;

    @GetMapping
    public ResponseEntity<BaseDTO> getAll() {
        List<EmpleadoPorHora> empleados = repository.findAll();
        return ok(empleados, "Lista de empleados por hora");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDTO> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(emp -> ok(emp, "Empleado encontrado"))
                .orElseGet(() -> notFound("Empleado no encontrado con ID " + id));
    }

    @PostMapping
    public ResponseEntity<BaseDTO> create(@RequestBody EmpleadoPorHora empleado) {
        EmpleadoPorHora saved = repository.save(empleado);
        return created(saved, "Empleado por hora creado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseDTO> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return noContent();
        }
        return notFound("Empleado no encontrado con ID " + id);
    }
}