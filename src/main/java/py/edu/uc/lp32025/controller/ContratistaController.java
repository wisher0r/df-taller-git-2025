package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.repository.ContratistaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contratistas")
public class ContratistaController {

    private final ContratistaRepository contratistaRepository;

    public ContratistaController(ContratistaRepository contratistaRepository) {
        this.contratistaRepository = contratistaRepository;
    }

    // ✅ Obtener todos los contratistas
    @GetMapping
    public List<Contratista> getAll() {
        return contratistaRepository.findAll();
    }

    // ✅ Obtener un contratista por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contratista> getById(@PathVariable Long id) {
        Optional<Contratista> contratista = contratistaRepository.findById(id);
        return contratista.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Crear un nuevo contratista
    @PostMapping
    public ResponseEntity<Contratista> create(@RequestBody Contratista contratista) {
        Contratista saved = contratistaRepository.save(contratista);
        return ResponseEntity.ok(saved);
    }

    // ✅ Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (contratistaRepository.existsById(id)) {
            contratistaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
