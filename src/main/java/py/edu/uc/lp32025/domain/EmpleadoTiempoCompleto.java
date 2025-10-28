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
    public BigDecimal getSalarioMensual() { return salarioMensual; }
    public void setSalarioMensual(BigDecimal salarioMensual) { this.salarioMensual = salarioMensual; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    // -------------------------
    // Métodos abstractos implementados
    // -------------------------
    @Override
    public BigDecimal calcularSalario() {
        return salarioMensual.subtract(salarioMensual.multiply(new BigDecimal("0.09")));
    }

    @Override
    public BigDecimal calcularDeducciones() {
        return salarioMensual.multiply(new BigDecimal("0.05"));
    }

    @Override
    public boolean validarDatosEspecificos() {
        return salarioMensual.compareTo(new BigDecimal("2899048")) >= 0;
    }

    // -------------------------
    // Método sobrescrito para información completa
    // -------------------------
    @Override
    public String obtenerInformacionCompleta() {
        String infoBase = super.obtenerInformacionCompleta();
        return infoBase +
                ", Departamento: " + departamento +
                ", Salario Mensual: " + salarioMensual;
    }
}
