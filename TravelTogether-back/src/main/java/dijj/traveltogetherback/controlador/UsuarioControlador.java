package dijj.traveltogetherback.controlador;



import dijj.traveltogetherback.servicio.UsuarioServicio;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioControlador {
    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }


}
