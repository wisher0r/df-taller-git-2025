// src/main/java/py/edu/uc/lp32025/domain/Vehiculo.java
package py.edu.uc.lp32025.domain;

public class Vehiculo implements Mapeable {

    private Long id;
    private String marca;
    private String modelo;
    private String placa;
    private String color;

    // Constructor vacío
    public Vehiculo() {}

    // Constructor completo
    public Vehiculo(String marca, String modelo, String placa, String color) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.color = color;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    // Mapeable
    @Override
    public PosicionGPS ubicarElemento() {
        double lat = -25.3000 + (id != null ? id * 0.002 : 0);
        double lon = -57.6000 + (id != null ? id * 0.002 : 0);
        return new PosicionGPS(lat, lon, "Vehículo: " + placa);
    }

    @Override
    public Avatar obtenerImagen() {
        String url = "https://ui-avatars.com/api/?name=" +
                (marca != null ? marca + "+" + modelo : "Auto").replace(" ", "+") +
                "&background=e74c3c&color=fff&size=128";
        return new Avatar(url, placa != null ? placa : "Vehículo", "Vehículo");
    }

    @Override
    public String toString() {
        return "Vehiculo{marca='" + marca + "', modelo='" + modelo + "', placa='" + placa + "'}";
    }
}