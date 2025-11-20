// src/main/java/py/edu/uc/lp32025/controller/ContratistaController.java
package py.edu.uc.lp32025.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.dto.BaseDTO;
import py.edu.uc.lp32025.repository.ContratistaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/contratistas")
@RequiredArgsConstructor
public class ContratistaController extends BaseController {

    private final ContratistaRepository repository;

    @GetMapping
    public ResponseEntity<BaseDTO> getAll() {
        List<Contratista> contratistas = repository.findAll();
        return ok(contratistas, "Lista de contratistas");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDTO> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(c -> ok(c, "Contratista encontrado"))
                .orElseGet(() -> notFound("Contratista no encontrado con ID " + id));
    }

    @PostMapping
    public ResponseEntity<BaseDTO> create(@RequestBody Contratista contratista) {
        Contratista saved = repository.save(contratista);
        return created(saved, "Contratista creado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseDTO> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return noContent();
        }
        return notFound("Contratista no encontrado con ID " + id);
    }
}