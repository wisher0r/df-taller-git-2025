// src/main/java/py/edu/uc/lp32025/domain/Gerente.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Gerente extends EmpleadoTiempoCompleto implements VacacionesCalculator {

    private int diasVacacionesAdicionales = 10;

    public Gerente() {
        super();
    }

    public Gerente(String nombre, LocalDate fechaNacimiento, String numeroDocumento,
                   BigDecimal salarioMensual, String departamento) {
        super(nombre, fechaNacimiento, numeroDocumento, salarioMensual, departamento);
    }

    public Gerente(String nombre, LocalDate fechaNacimiento, String numeroDocumento,
                   BigDecimal salarioMensual, String departamento, int diasVacacionesAdicionales) {
        super(nombre, fechaNacimiento, numeroDocumento, salarioMensual, departamento);
        this.diasVacacionesAdicionales = diasVacacionesAdicionales;
    }

    // Método exclusivo
    public void aprobarVacacionesEmpleado(EmpleadoTiempoCompleto empleado, int dias) {
        empleado.setDiasVacacionesSolicitados(empleado.getDiasVacacionesSolicitados() + dias);
    }

    @Override
    public int calcularDiasVacaciones(int antiguedad) {
        return antiguedad * 2 + diasVacacionesAdicionales;
    }

    @Override
    public void solicitarPermiso(int dias) {
        setDiasPermisosSolicitados(getDiasPermisosSolicitados() + dias);
    }

    @Override
    public BigDecimal calcularSalario() {
        return super.calcularSalario().add(new BigDecimal("2000000"));
    }
}