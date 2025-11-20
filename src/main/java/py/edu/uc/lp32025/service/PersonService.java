package py.edu.uc.lp32025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.controller.FechaNacimientoInvalidaException;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.repository.PersonaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    public Optional<Persona> getPersonaById(Long id) {
        return personaRepository.findById(id);
    }

    // En PersonService.savePersona
    public Persona savePersona(Persona persona) {
        if (persona.getFechaNacimiento() != null && persona.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new FechaNacimientoInvalidaException("La fecha de nacimiento no puede estar en el futuro");
        }
        return personaRepository.save(persona);
    }

    public void deletePersona(Long id) {
        personaRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return personaRepository.existsById(id);
    }
}
