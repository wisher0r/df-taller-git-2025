package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class EmpleadoTiempoCompleto extends Persona {

    @Column(nullable = false)
    private BigDecimal salarioMensual;

    @Column(nullable = false)
    private String departamento;

    // Getters y Setters
    public BigDecimal getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(BigDecimal salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
