// src/main/java/py/edu/uc/lp32025/service/ContratistaService.java
package py.edu.uc.lp32025.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.repository.ContratistaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContratistaService {

    private final ContratistaRepository repository;

    public List<Contratista> findAll() {
        return repository.findAll();
    }

    public Optional<Contratista> findById(Long id) {
        return repository.findById(id);
    }

    public Contratista save(Contratista contratista) {
        return repository.save(contratista);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}