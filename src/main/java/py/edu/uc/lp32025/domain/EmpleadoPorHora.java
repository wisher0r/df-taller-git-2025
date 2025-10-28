package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class EmpleadoPorHora extends Persona {

    @Column(nullable = false)
    private BigDecimal tarifaPorHora;

    @Column(nullable = false)
    private Integer horasTrabajadas;

    // Getters y Setters
    public BigDecimal getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(BigDecimal tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public BigDecimal calcularSalario() {
        return null;
    }

    @Override
    public BigDecimal calcularDeducciones() {
        return null;
    }

    @Override
    public boolean validarDatosEspecificos() {
        return false;
    }
}
