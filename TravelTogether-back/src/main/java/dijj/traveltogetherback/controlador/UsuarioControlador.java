package dijj.traveltogetherback.controlador;



import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class UsuarioControlador {
    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/amigos")
    public ResponseEntity<List<Usuario>> listarAmigos(@RequestParam Long id_usuario) {
        Set<Usuario> amigos = usuarioServicio.todosLosAmigos(id_usuario);
        if (!amigos.isEmpty()) {
            return ResponseEntity.ok(List.copyOf(amigos));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
