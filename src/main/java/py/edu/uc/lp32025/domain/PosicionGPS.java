// src/main/java/py/edu/uc/lp32025/domain/PosicionGPS.java
package py.edu.uc.lp32025.domain;

public class PosicionGPS {
    private Double latitud;
    private Double longitud;
    private String nombreLugar;

    public PosicionGPS() {}

    public PosicionGPS(Double latitud, Double longitud, String nombreLugar) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombreLugar = nombreLugar;
    }

    public Double getLatitude() { return latitud; }
    public void setLatitude(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public String getNombreLugar() { return nombreLugar; }
    public void setNombreLugar(String nombreLugar) { this.nombreLugar = nombreLugar; }

    @Override
    public String toString() {
        return String.format("PosicionGPS{lat=%.4f, lon=%.4f, lugar='%s'}", latitud, longitud, nombreLugar);
    }
}