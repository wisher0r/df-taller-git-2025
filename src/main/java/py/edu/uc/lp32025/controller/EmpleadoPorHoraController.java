package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.repository.EmpleadoPorHoraRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados/por-horas")
public class EmpleadoPorHoraController {

    private final EmpleadoPorHoraRepository empleadoPorHoraRepository;

    public EmpleadoPorHoraController(EmpleadoPorHoraRepository empleadoPorHoraRepository) {
        this.empleadoPorHoraRepository = empleadoPorHoraRepository;
    }

    // ✅ Obtener todos los empleados por horas
    @GetMapping
    public List<EmpleadoPorHora> getAll() {
        return empleadoPorHoraRepository.findAll();
    }

    // ✅ Obtener un empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoPorHora> getById(@PathVariable Long id) {
        Optional<EmpleadoPorHora> empleado = empleadoPorHoraRepository.findById(id);
        return empleado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Crear un nuevo empleado por horas
    @PostMapping
    public ResponseEntity<EmpleadoPorHora> create(@RequestBody EmpleadoPorHora empleadoPorHora) {
        EmpleadoPorHora saved = empleadoPorHoraRepository.save(empleadoPorHora);
        return ResponseEntity.ok(saved);
    }

    // ✅ Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (empleadoPorHoraRepository.existsById(id)) {
            empleadoPorHoraRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
