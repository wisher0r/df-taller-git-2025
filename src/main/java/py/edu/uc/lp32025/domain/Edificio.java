// src/main/java/py/edu/uc/lp32025/domain/Edificio.java
package py.edu.uc.lp32025.domain;

import java.math.BigDecimal;

public class Edificio implements Mapeable {

    private Long id;
    private String nombre;
    private String direccion;
    private int pisos;
    private BigDecimal valor;

    // Constructor vacío
    public Edificio() {}

    // Constructor completo
    public Edificio(String nombre, String direccion, int pisos, BigDecimal valor) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.pisos = pisos;
        this.valor = valor;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public int getPisos() { return pisos; }
    public void setPisos(int pisos) { this.pisos = pisos; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    // Mapeable
    @Override
    public PosicionGPS ubicarElemento() {
        double lat = -25.2800 + (id != null ? id * 0.001 : 0);
        double lon = -57.6300 + (id != null ? id * 0.001 : 0);
        return new PosicionGPS(lat, lon, "Edificio: " + nombre);
    }

    @Override
    public Avatar obtenerImagen() {
        String url = "https://ui-avatars.com/api/?name=" +
                (nombre != null ? nombre.replace(" ", "+") : "Edificio") +
                "&background=34495e&color=fff&size=128";
        return new Avatar(url, nombre != null ? nombre : "Edificio", "Edificio");
    }

    @Override
    public String toString() {
        return "Edificio{nombre='" + nombre + "', pisos=" + pisos + ", valor=" + valor + "}";
    }
}