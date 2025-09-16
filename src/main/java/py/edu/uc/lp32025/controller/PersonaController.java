package py.edu.uc.lp32025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.service.PersonService;
import py.edu.uc.lp32025.dto.BaseDTO;
import py.edu.uc.lp32025.dto.SuccessResponseDTO;
import py.edu.uc.lp32025.dto.ErrorResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonService personService;

    // 📌 Crear persona
    @PostMapping
    public ResponseEntity<BaseDTO> createPersona(@RequestBody Persona persona) {
        Persona saved = personService.savePersona(persona);
        return new ResponseEntity<>(new SuccessResponseDTO<>(saved, "Persona creada exitosamente"), HttpStatus.CREATED);
    }

    // 📌 Obtener todas las personas
    @GetMapping
    public ResponseEntity<BaseDTO> getAllPersonas() {
        List<Persona> personas = personService.getAllPersonas();
        return ResponseEntity.ok(new SuccessResponseDTO<>(personas, "Lista de personas obtenida"));
    }

    // 📌 Obtener persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseDTO> getPersonaById(@PathVariable Long id) {
        return personService.getPersonaById(id)
                .<ResponseEntity<BaseDTO>>map(persona ->
                        ResponseEntity.ok(new SuccessResponseDTO<>(persona, "Persona encontrada"))
                )
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ErrorResponseDTO("NOT_FOUND", "Persona no encontrada"))
                );
    }

    // 📌 Actualizar persona por ID
    @PutMapping("/{id}")
    public ResponseEntity<BaseDTO> updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        if (!personService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("NOT_FOUND", "Persona no encontrada"));
        }
        persona.setId(id);
        Persona updated = personService.savePersona(persona);
        return ResponseEntity.ok(new SuccessResponseDTO<>(updated, "Persona actualizada exitosamente"));
    }

    // 📌 Eliminar persona por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseDTO> deletePersona(@PathVariable Long id) {
        if (!personService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("NOT_FOUND", "Persona no encontrada"));
        }
        personService.deletePersona(id);
        return ResponseEntity.ok(new SuccessResponseDTO<>(null, "Persona eliminada correctamente"));
    }
}
