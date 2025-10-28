package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.ImpuestoDTO;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoTiempoCompletoController {

    private final EmpleadoTiempoCompletoService service;

    public EmpleadoTiempoCompletoController(EmpleadoTiempoCompletoService service) {
        this.service = service;
    }

    // Listar todos los empleados
    @GetMapping
    public List<EmpleadoTiempoCompleto> listar() {
        return service.listarTodos();
    }

    // Obtener un empleado por ID
    @GetMapping("/{id}")
    public EmpleadoTiempoCompleto obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID " + id));
    }

    // Crear un nuevo empleado
    @PostMapping
    public EmpleadoTiempoCompleto crear(@RequestBody EmpleadoTiempoCompleto empleado) {
        return service.guardar(empleado);
    }

    // Actualizar un empleado existente
    @PutMapping("/{id}")
    public EmpleadoTiempoCompleto actualizar(@PathVariable Long id, @RequestBody EmpleadoTiempoCompleto empleado) {
        return service.buscarPorId(id)
                .map(existente -> {
                    existente.setNombre(empleado.getNombre());
                    existente.setNumeroDocumento(empleado.getNumeroDocumento());
                    existente.setDepartamento(empleado.getDepartamento());
                    existente.setSalarioMensual(empleado.getSalarioMensual());
                    existente.setFechaNacimiento(empleado.getFechaNacimiento());
                    return service.guardar(existente);
                })
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID " + id));
    }

    // Eliminar un empleado
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    // -------------------------
    // Nuevo endpoint: consultar impuestos
    // -------------------------
    @GetMapping("/{id}/impuestos")
    public ImpuestoDTO consultarImpuestos(@PathVariable Long id) {
        EmpleadoTiempoCompleto empleado = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID " + id));

        BigDecimal salario = empleado.calcularSalario();
        BigDecimal deducciones = empleado.calcularDeducciones();
        BigDecimal impuesto = empleado.calcularImpuestos();
        boolean datosValidos = empleado.validarDatosEspecificos();
        String informacionCompleta = empleado.obtenerInformacionCompleta(); // NUEVO

        return new ImpuestoDTO(salario, deducciones, impuesto, datosValidos, informacionCompleta);
    }



}
