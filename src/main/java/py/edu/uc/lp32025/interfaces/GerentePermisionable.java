package py.edu.uc.lp32025.interfaces;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;

public interface GerentePermisionable extends Permisionable {
    void aprobarSolicitud(EmpleadoTiempoCompleto empleado, int dias, String tipo);
}