package dijj.traveltogetherback.controlador;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActividadControlador {

    @PostMapping("/viaje/actividad/nueva")
    public void proponerActividad(){

    }

    @PostMapping("/viaje/actividad/votar")
    public void votarActividad(){

    }
    @GetMapping("/viaje/actividad")
    public void verActividades(){

    }
}
