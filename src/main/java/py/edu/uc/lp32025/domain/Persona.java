// src/main/java/py/edu/uc/lp32025/domain/Persona.java
package py.edu.uc.lp32025.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_empleado")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmpleadoTiempoCompleto.class, name = "EmpleadoTiempoCompleto"),
        @JsonSubTypes.Type(value = EmpleadoPorHora.class, name = "EmpleadoPorHora"),
        @JsonSubTypes.Type(value = Contratista.class, name = "Contratista"),
        @JsonSubTypes.Type(value = Gerente.class, name = "Gerente") // 👈 agregado para serialización correcta
})
public abstract class Persona implements Mapeable {
    private int diasVacacionesSolicitados = 0;
    private int diasPermisosSolicitados = 0;

    // AÑADE ESTOS GETTERS Y SETTERS
    public int getDiasVacacionesSolicitados() {
        return diasVacacionesSolicitados;
    }

    public void setDiasVacacionesSolicitados(int diasVacacionesSolicitados) {
        this.diasVacacionesSolicitados = diasVacacionesSolicitados;
    }

    public int getDiasPermisosSolicitados() {
        return diasPermisosSolicitados;
    }

    public void setDiasPermisosSolicitados(int diasPermisosSolicitados) {
        this.diasPermisosSolicitados = diasPermisosSolicitados;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private LocalDate fechaNacimiento;

    @Column(unique = true, nullable = false)
    private String numeroDocumento;

    // CONSTRUCTOR CON PARÁMETROS
    public Persona(String nombre, LocalDate fechaNacimiento, String numeroDocumento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroDocumento = numeroDocumento;
    }

    // CONSTRUCTOR VACÍO (JPA)
    public Persona() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    // MÉTODOS ABSTRACTOS
    public abstract BigDecimal calcularSalario();
    public abstract BigDecimal calcularDeducciones();
    public abstract boolean validarDatosEspecificos();

    // TEMPLATE METHOD - NULL-SAFE
    public BigDecimal calcularImpuestos() {
        BigDecimal salario = calcularSalario();
        BigDecimal deducciones = calcularDeducciones();

        if (salario == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal impuestoBase = calcularImpuestoBase(salario);
        return impuestoBase.subtract(deducciones != null ? deducciones : BigDecimal.ZERO)
                .max(BigDecimal.ZERO);
    }

    // NULL-SAFE
    protected BigDecimal calcularImpuestoBase(BigDecimal salario) {
        if (salario == null) {
            return BigDecimal.ZERO;
        }
        return salario.multiply(new BigDecimal("0.10"));
    }

    public String obtenerInformacionCompleta() {
        return "ID: " + (id != null ? id : "sin ID") +
                ", Nombre: " + (nombre != null ? nombre : "sin nombre") +
                ", Fecha Nacimiento: " + fechaNacimiento +
                ", Documento: " + numeroDocumento;
    }

    // MAPEABLE - NULL-SAFE
    @Override
    public PosicionGPS ubicarElemento() {
        double baseLat = -25.2637;
        double baseLon = -57.5759;
        double offset = (id != null ? id : 0L) * 0.001;
        return new PosicionGPS(baseLat + offset, baseLon + offset,
                "Oficina UC - " + getClass().getSimpleName());
    }

    @Override
    public Avatar obtenerImagen() {
        String nombreAvatar = nombre != null ? nombre : "Empleado";
        String url = "https://ui-avatars.com/api/?name=" + nombreAvatar.replace(" ", "+") +
                "&background=0D8ABC&color=fff&size=128";
        return new Avatar(url, nombreAvatar, getClass().getSimpleName());
    }
}
