// src/main/java/py/edu/uc/lp32025/domain/Avatar.java
package py.edu.uc.lp32025.domain;

public class Avatar {
    private String imagenUrl;
    private String nombre;
    private String rol;

    public Avatar() {}

    public Avatar(String imagenUrl, String nombre, String rol) {
        this.imagenUrl = imagenUrl;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getImagen() { return imagenUrl; }
    public void setImagen(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public String getNick() { return nombre; }
    public void setNick(String nombre) { this.nombre = nombre; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return String.format("Avatar{nick='%s', imagen='%s', rol='%s'}", nombre, imagenUrl, rol);
    }
}