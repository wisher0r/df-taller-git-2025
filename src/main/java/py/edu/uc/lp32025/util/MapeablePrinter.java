// src/main/java/py/edu/uc/lp32025/util/MapeablePrinter.java
package py.edu.uc.lp32025.util;

import py.edu.uc.lp32025.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class MapeablePrinter {

    private static final Logger log = LoggerFactory.getLogger(MapeablePrinter.class);

    public static void imprimirElementos(List<Mapeable> elementos) {
        if (elementos == null || elementos.isEmpty()) {
            log.warn("No hay elementos mapeables para mostrar.");
            return;
        }

        log.info("Procesando {} elementos mapeables", elementos.size());
        log.info("{}", "=".repeat(70));

        for (Mapeable e : elementos) {
            PosicionGPS gps = e.ubicarElemento();
            Avatar avatar = e.obtenerImagen();

            log.info("Tipo: {}", e.getClass().getSimpleName());
            log.info("  GPS → Lat: {:.6f}, Lon: {:.6f}, Lugar: {}",
                    gps.getLatitude(), gps.getLongitud(), gps.getNombreLugar());
            log.info("  Avatar → Nick: '{}', Rol: '{}'", avatar.getNick(), avatar.getRol());
            log.info("  Imagen → {}", avatar.getImagen());

            if (e instanceof Persona p) {
                log.info("  Nómina → Bruto: {}, Deducciones: {}, Impuestos: {}, Válido: {}",
                        p.calcularSalario(), p.calcularDeducciones(), p.calcularImpuestos(), p.validarDatosEspecificos());
            } else {
                log.info("  Info → {}", e.toString());
            }
            log.info("{}", "-".repeat(70));
        }
    }
}