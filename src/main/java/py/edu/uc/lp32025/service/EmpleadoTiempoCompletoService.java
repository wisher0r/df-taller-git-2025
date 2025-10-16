package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.repository.EmpleadoTiempoCompletoRepository;

@Service
public class EmpleadoTiempoCompletoService {

    private final EmpleadoTiempoCompletoRepository repository;

    public EmpleadoTiempoCompletoService(EmpleadoTiempoCompletoRepository repository) {
        this.repository = repository;
    }

    public List<EmpleadoTiempoCompleto> listarTodos() {
        return repository.findAll();
    }

    public Optional<EmpleadoTiempoCompleto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EmpleadoTiempoCompleto guardar(EmpleadoTiempoCompleto empleado) {
        return repository.save(empleado);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
