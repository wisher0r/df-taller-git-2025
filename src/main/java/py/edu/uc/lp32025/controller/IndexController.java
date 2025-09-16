package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import py.edu.uc.lp32025.dto.SaludoDTO;

@RestController
public class IndexController {

    // Redirección de la raíz "/" hacia "/HolaMundo"
    @GetMapping("/")
    public RedirectView redirectToHolaMundo() {
        return new RedirectView("/HolaMundo");
    }

    // Servicio REST que devuelve un DTO con el saludo
    @GetMapping("/HolaMundo")
    public SaludoDTO holaMundo(@RequestParam(defaultValue = "Mundo") String nombre) {
        String mensaje = "Hola " + nombre;
        return new SaludoDTO(mensaje);
    }
}

