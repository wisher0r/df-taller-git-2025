// src/main/java/py/edu/uc/lp32025/service/GerenteService.java
package py.edu.uc.lp32025.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.uc.lp32025.controller.EmpleadoNoEncontradoException;
import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.repository.GerenteRepository;
import py.edu.uc.lp32025.repository.PersonaRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GerenteService {

    private final GerenteRepository repository;
    private final EmpleadoService empleadoService; // Para aprobar
    private final PersonaRepository personaRepository;
    public List<Gerente> findAll() {
        return repository.findAll();
    }

    public Gerente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Gerente no encontrado: " + id));
    }

    @Transactional
    public Gerente save(Gerente gerente) {
        if (!gerente.validarDatosEspecificos()) {
            throw new IllegalArgumentException("Datos inválidos");
        }
        return repository.save(gerente);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new EmpleadoNoEncontradoException("Gerente no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public void aprobarSolicitud(Long gerenteId, Long empleadoId, int dias, String tipo) {
        Gerente gerente = findById(gerenteId);

        // Busca el empleado genéricamente
        Persona empleado = personaRepository.findById(empleadoId)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Empleado no encontrado: " + empleadoId));

        // Verifica que sea un EmpleadoTiempoCompleto (o PorHora) antes del cast
        if (!(empleado instanceof EmpleadoTiempoCompleto emp)) {
            throw new IllegalArgumentException("Solo se pueden aprobar vacaciones a empleados de tiempo completo o por hora");
        }


        gerente.aprobarVacacionesEmpleado(emp, dias);
    }
}