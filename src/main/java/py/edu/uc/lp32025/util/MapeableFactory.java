// src/main/java/py/edu/uc/lp32025/util/MapeableFactory.java
package py.edu.uc.lp32025.util;

import py.edu.uc.lp32025.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapeableFactory {

    private static final Logger log = LoggerFactory.getLogger(MapeableFactory.class);

    public static List<Mapeable> crearElementosDemo() {
        log.info("Iniciando creación de elementos demo...");

        List<Mapeable> elementos = new ArrayList<>();

        // Empleado Tiempo Completo
        log.debug("Creando EmpleadoTiempoCompleto...");
        EmpleadoTiempoCompleto empTC = new EmpleadoTiempoCompleto(
                "Ana López", LocalDate.of(1990, 5, 15), "1111111",
                new BigDecimal("6000000"), "IT"
        );
        empTC.setId(1L);
        elementos.add(empTC);
        log.debug("EmpleadoTiempoCompleto creado: {}", empTC.getNombre());

        // Empleado Por Hora
        log.debug("Creando EmpleadoPorHora...");
        EmpleadoPorHora empPH = new EmpleadoPorHora();
        empPH.setId(2L);
        empPH.setNombre("Luis García");
        empPH.setNumeroDocumento("2222222");
        empPH.setFechaNacimiento(LocalDate.of(1985, 8, 20));
        empPH.setTarifaPorHora(new BigDecimal("50000"));
        empPH.setHorasTrabajadas(45);
        elementos.add(empPH);

        // Contratista
        log.debug("Creando Contratista...");
        Contratista contratista = new Contratista();
        contratista.setId(3L);
        contratista.setNombre("Pedro Ruiz");
        contratista.setNumeroDocumento("3333333");
        contratista.setFechaNacimiento(LocalDate.of(1978, 3, 10));
        contratista.setMontoPorProyecto(new BigDecimal("1200000"));
        contratista.setProyectosCompletados(3);
        contratista.setFechaFinContrato(LocalDate.now().plusMonths(6));
        elementos.add(contratista);

        // Edificio
        log.debug("Creando Edificio...");
        Edificio edificio = new Edificio("Torre UC", "Av. España 123", 15, new BigDecimal("750000000"));
        edificio.setId(10L);
        elementos.add(edificio);

        // Vehículo
        log.debug("Creando Vehiculo...");
        Vehiculo auto = new Vehiculo("Honda", "Civic", "XYZ-789", "Negro");
        auto.setId(20L);
        elementos.add(auto);

        log.info("Elementos demo creados: {} elementos", elementos.size());
        return elementos;
    }
}