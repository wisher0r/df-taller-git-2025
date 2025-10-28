package py.edu.uc.lp32025.dto;

import java.math.BigDecimal;

public class ImpuestoDTO {

    private BigDecimal salario;
    private BigDecimal deducciones;
    private BigDecimal impuesto;
    private boolean datosValidos;
    private String informacionCompleta;

    public ImpuestoDTO(BigDecimal salario, BigDecimal deducciones, BigDecimal impuesto, boolean datosValidos, String informacionCompleta) {
        this.salario = salario;
        this.deducciones = deducciones;
        this.impuesto = impuesto;
        this.datosValidos = datosValidos;
        this.informacionCompleta = informacionCompleta;
    }

    // Getters
    public BigDecimal getSalario() { return salario; }
    public BigDecimal getDeducciones() { return deducciones; }
    public BigDecimal getImpuesto() { return impuesto; }
    public boolean isDatosValidos() { return datosValidos; }
    public String getInformacionCompleta() { return informacionCompleta; }

    // Setters (opcionales)
    public void setSalario(BigDecimal salario) { this.salario = salario; }
    public void setDeducciones(BigDecimal deducciones) { this.deducciones = deducciones; }
    public void setImpuesto(BigDecimal impuesto) { this.impuesto = impuesto; }
    public void setDatosValidos(boolean datosValidos) { this.datosValidos = datosValidos; }
    public void setInformacionCompleta(String informacionCompleta) { this.informacionCompleta = informacionCompleta; }
}