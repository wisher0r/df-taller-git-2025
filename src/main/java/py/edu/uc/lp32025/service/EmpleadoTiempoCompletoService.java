package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.ImpuestoDTO;
import py.edu.uc.lp32025.repository.EmpleadoTiempoCompletoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmpleadoTiempoCompletoService {
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoTiempoCompletoService.class);

    private final EmpleadoTiempoCompletoRepository repository;

    public EmpleadoTiempoCompletoService(EmpleadoTiempoCompletoRepository repository) {
        this.repository = repository;
    }

    // -------------------------
    // Métodos CRUD básicos
    // -------------------------
    public List<EmpleadoTiempoCompleto> listarTodos() {
        return repository.findAll();
    }

    public Optional<EmpleadoTiempoCompleto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EmpleadoTiempoCompleto guardar(EmpleadoTiempoCompleto empleado) {
            boolean valido = empleado.validarDatosEspecificos();

            if (valido) {
                logger.info("Empleado válido creado: {} (Documento: {})", empleado.getNombre(), empleado.getNumeroDocumento());
            } else {
                logger.warn("Empleado inválido creado: {} (Documento: {})", empleado.getNombre(), empleado.getNumeroDocumento());
            }

            return repository.save(empleado);
        }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // -------------------------
    // Método para obtener DTO de impuestos
    // -------------------------
    public ImpuestoDTO obtenerImpuestosPorId(Long id) {
        EmpleadoTiempoCompleto empleado = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID " + id));

        BigDecimal salario = empleado.calcularSalario();
        BigDecimal deducciones = empleado.calcularDeducciones();
        BigDecimal impuesto = empleado.calcularImpuestos();
        boolean datosValidos = empleado.validarDatosEspecificos();
        String informacionCompleta = empleado.obtenerInformacionCompleta();

        return new ImpuestoDTO(salario, deducciones, impuesto, datosValidos, informacionCompleta);
    }

    // -------------------------
    // Persistencia en Batch
    // -------------------------
    public void guardarEmpleadosEnBatch(List<EmpleadoTiempoCompleto> empleados) {
        final int CHUNK_SIZE = 100;

        for (int i = 0; i < empleados.size(); i += CHUNK_SIZE) {
            int end = Math.min(i + CHUNK_SIZE, empleados.size());
            List<EmpleadoTiempoCompleto> chunk = empleados.subList(i, end);

            for (EmpleadoTiempoCompleto e : chunk) {
                boolean valido = e.validarDatosEspecificos();

                if (valido) {
                    logger.info("Empleado válido en batch: {} (Documento: {})", e.getNombre(), e.getNumeroDocumento());
                } else {
                    logger.warn("Empleado inválido en batch: {} (Documento: {})", e.getNombre(), e.getNumeroDocumento());
                }
            }

            // Guardar solo los válidos
            List<EmpleadoTiempoCompleto> validos = chunk.stream()
                    .filter(EmpleadoTiempoCompleto::validarDatosEspecificos)
                    .toList();

            if (!validos.isEmpty()) {
                repository.saveAll(validos);
                logger.info("Guardados {} empleados válidos en este chunk", validos.size());
            }
        }
    }
    }

