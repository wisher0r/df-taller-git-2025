// src/main/java/py/edu/uc/lp32025/domain/EmpleadoTiempoCompleto.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class EmpleadoTiempoCompleto extends Persona {

    @Column(nullable = false)
    private BigDecimal salarioMensual;

    @Column(nullable = false)
    private String departamento;

    // CONSTRUCTOR CON PARÁMETROS (CORREGIDO)
    public EmpleadoTiempoCompleto(String nombre, LocalDate fechaNacimiento, String numeroDocumento,
                                  BigDecimal salarioMensual, String departamento) {
        super(nombre, fechaNacimiento, numeroDocumento);
        this.salarioMensual = salarioMensual;
        this.departamento = departamento;
    }

    // CONSTRUCTOR VACÍO (OBLIGATORIO PARA JPA)
    public EmpleadoTiempoCompleto() {
        super();
    }

    // Getters y Setters
    public BigDecimal getSalarioMensual() { return salarioMensual; }
    public void setSalarioMensual(BigDecimal salarioMensual) { this.salarioMensual = salarioMensual; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    // MÉTODOS ABSTRACTOS
    @Override
    public BigDecimal calcularSalario() {
        // DEVUELVE SALARIO BRUTO (correcto)
        return salarioMensual;
    }

    @Override
    public BigDecimal calcularDeducciones() {
        // 5% de deducciones
        return salarioMensual.multiply(new BigDecimal("0.05"));
    }

    @Override
    public boolean validarDatosEspecificos() {
        // Salario ≥ 2,899,048
        return salarioMensual != null && salarioMensual.compareTo(new BigDecimal("2899048")) >= 0
                && departamento != null && !departamento.isBlank();
    }

    @Override
    public String obtenerInformacionCompleta() {
        return super.obtenerInformacionCompleta() +
                ", Departamento: " + departamento +
                ", Salario Mensual: " + salarioMensual;
    }
}