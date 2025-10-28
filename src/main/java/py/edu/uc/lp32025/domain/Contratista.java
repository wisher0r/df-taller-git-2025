package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Contratista extends Persona {

    @Column(nullable = false)
    private BigDecimal montoPorProyecto;

    @Column(nullable = false)
    private Integer proyectosCompletados;

    @Column(nullable = false)
    private LocalDate fechaFinContrato;

    // Getters y Setters
    public BigDecimal getMontoPorProyecto() {
        return montoPorProyecto;
    }

    public void setMontoPorProyecto(BigDecimal montoPorProyecto) {
        this.montoPorProyecto = montoPorProyecto;
    }

    public Integer getProyectosCompletados() {
        return proyectosCompletados;
    }

    public void setProyectosCompletados(Integer proyectosCompletados) {
        this.proyectosCompletados = proyectosCompletados;
    }

    public LocalDate getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(LocalDate fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
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
