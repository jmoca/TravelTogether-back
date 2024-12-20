package dijj.traveltogetherback.controlador;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.servicio.GrupoServicio;
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
    private final GrupoServicio grupoServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio, GrupoServicio grupoServicio) {
        this.usuarioServicio = usuarioServicio;
        this.grupoServicio = grupoServicio;
    }

    @GetMapping("/amigos")
    public Set<UsuarioDTO> obtenerAmigos(@RequestParam Long id_usuario) {
        return usuarioServicio.obtenerAmigosDTO(id_usuario);
    }

    @GetMapping("/viajes")
    public ResponseEntity<List<GrupoDTO>> listarGrupos(@RequestParam Long id_usuario) {
        List<GrupoDTO> grupos = grupoServicio.obtenerGruposPorUsuario(id_usuario);
        return ResponseEntity.ok(grupos);

    }
}
