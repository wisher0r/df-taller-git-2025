package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_empleado")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmpleadoTiempoCompleto.class, name = "EmpleadoTiempoCompleto"),
        @JsonSubTypes.Type(value = EmpleadoPorHora.class, name = "EmpleadoPorHora"),
        @JsonSubTypes.Type(value = Contratista.class, name = "Contratista")
})
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private LocalDate fechaNacimiento;

    @Column(unique = true, nullable = false)
    private String numeroDocumento;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    // -------------------------
    // Métodos abstractos
    // -------------------------

    /**
     * Cada subclase debe implementar cómo calcular su salario
     */
    public abstract BigDecimal calcularSalario();

    /**
     * Cada subclase define sus deducciones específicas
     */
    public abstract BigDecimal calcularDeducciones();

    /**
     * Validación específica de la subclase
     */
    public abstract boolean validarDatosEspecificos();

    // -------------------------
    // Métodos concretos
    // -------------------------

    /**
     * Método template: calcula impuestos usando la deducción y el impuesto base
     */
    public BigDecimal calcularImpuestos() {
        BigDecimal salario = calcularSalario();
        BigDecimal deducciones = calcularDeducciones();
        BigDecimal impuestoBase = calcularImpuestoBase(salario);

        // Impuesto final = impuesto base - deducciones
        return impuestoBase.subtract(deducciones).max(BigDecimal.ZERO);
    }

    /**
     * Impuesto base = 10% del salario
     */
    protected BigDecimal calcularImpuestoBase(BigDecimal salario) {
        return salario.multiply(new BigDecimal("0.10"));
    }

    /**
     * Método concreto que puede ser sobrescrito por subclases
     */
    public String obtenerInformacionCompleta() {
        return "ID: " + id +
                ", Nombre: " + nombre +
                ", Fecha Nacimiento: " + fechaNacimiento +
                ", Documento: " + numeroDocumento;
    }
}
