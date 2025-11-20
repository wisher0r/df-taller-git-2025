package py.edu.uc.lp32025.domain;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import jakarta.persistence.Entity;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Gerente extends EmpleadoTiempoCompleto implements VacacionesCalculator {

    private int diasVacacionesAdicionales; // Exclusivo para gerentes

    public Gerente() {}

    public Gerente(String nombre, LocalDate fechaNacimiento, String numeroDocumento,
                   BigDecimal salarioMensual, String departamento, int diasVacacionesAdicionales) {
        super(nombre, fechaNacimiento, numeroDocumento, salarioMensual, departamento);
        this.diasVacacionesAdicionales = diasVacacionesAdicionales;
    }

    // Método exclusivo para gerentes
    public void aprobarVacacionesEmpleado(EmpleadoTiempoCompleto empleado, int dias) {
        // TODO: implementar lógica para aprobar vacaciones del empleado
    }

    @Override
    public int calcularDiasVacaciones(int antiguedad) {
        int diasBase = antiguedad * 2;
        return diasBase + diasVacacionesAdicionales;
    }

    @Override
    public void solicitarPermiso(int dias) {
        // Lógica para solicitar permiso
    }

    @Override
    public BigDecimal calcularSalario() {
        return super.calcularSalario().add(new BigDecimal("1000000")); // Bonus gerencial
    }

    @Override
    public boolean validarDatosEspecificos() {
        return super.validarDatosEspecificos() && diasVacacionesAdicionales >= 0;
    }
}
