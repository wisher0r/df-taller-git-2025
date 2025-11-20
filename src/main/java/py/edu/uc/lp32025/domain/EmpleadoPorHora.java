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
        BigDecimal base = tarifaPorHora.multiply(BigDecimal.valueOf(horasTrabajadas));
        if (horasTrabajadas > 40) {
            int extras = horasTrabajadas - 40;
            base = base.add(tarifaPorHora.multiply(BigDecimal.valueOf(extras)).multiply(new BigDecimal("1.5")));
        }
        return base;
    }

    @Override
    public BigDecimal calcularDeducciones() {
        return calcularSalario().multiply(new BigDecimal("0.09")); // 9%
    }

    @Override
    public boolean validarDatosEspecificos() {
        return tarifaPorHora != null && tarifaPorHora.compareTo(BigDecimal.ZERO) > 0
                && horasTrabajadas >= 0 && horasTrabajadas <= 160;
    }
}
