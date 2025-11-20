// src/main/java/py/edu/uc/lp32025/controller/GerenteController.java
package py.edu.uc.lp32025.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Gerente;
import py.edu.uc.lp32025.dto.BaseDTO;
import py.edu.uc.lp32025.controller.EmpleadoNoEncontradoException;
import py.edu.uc.lp32025.service.GerenteService;

import java.util.List;

@RestController
@RequestMapping("/api/gerentes")
@RequiredArgsConstructor
public class GerenteController extends BaseController {

    private final GerenteService service;

    @GetMapping
    public ResponseEntity<BaseDTO> getAll() {
        List<Gerente> list = service.findAll();
        return ok(list, "Lista de gerentes");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDTO> getById(@PathVariable Long id) {
        try {
            Gerente gerente = service.findById(id);
            return ok(gerente, "Gerente encontrado");
        } catch (EmpleadoNoEncontradoException e) {
            return notFound(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<BaseDTO> create(@RequestBody Gerente g) {
        return created(service.save(g), "Gerente creado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseDTO> update(@PathVariable Long id, @RequestBody Gerente g) {
        g.setId(id);
        return ok(service.save(g), "Gerente actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseDTO> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return noContent();
        } catch (EmpleadoNoEncontradoException e) {
            return notFound(e.getMessage());
        }
    }

    @PostMapping("/{gerenteId}/aprobar/{empleadoId}")
    public ResponseEntity<BaseDTO> aprobar(
            @PathVariable Long gerenteId,
            @PathVariable Long empleadoId,
            @RequestParam int dias,
            @RequestParam String tipo) {
        service.aprobarSolicitud(gerenteId, empleadoId, dias, tipo);
        return ok(null, "Solicitud aprobada");
    }
}