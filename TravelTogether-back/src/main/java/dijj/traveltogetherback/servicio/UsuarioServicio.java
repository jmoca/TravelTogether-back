package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements IUsuarioService {

    private final IUsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Método para obtener los amigos de un usuario
    public Set<Usuario> obtenerAmigos(Long id) {
        // Buscar el usuario por ID
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(id);
        if (usuarioOpt.isEmpty()) {
            return Set.of(); // Retorna un conjunto vacío si no se encuentra el usuario
        }

        // Obtiene el usuario y su lista de amigos usando el método getAmigos()
        Usuario usuario = usuarioOpt.get();
        return usuario.getAmigos();
    }
    public Set<UsuarioDTO> obtenerAmigosDTO(Long id_usuario) {
        Set<Usuario> amigos = obtenerAmigos(id_usuario);
        return amigos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toSet());
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId_usuario(), usuario.getNombre());
    }

}
