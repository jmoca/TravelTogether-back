package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.modelo.Amigos;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IAmigoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements IUsuarioService {

    private final IUsuarioRepositorio usuarioRepositorio;
    private final IAmigoRepositorio amigoRepositorio;

    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio, IAmigoRepositorio amigoRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.amigoRepositorio = amigoRepositorio;
    }

    // Método para crear un usuario
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    // Método para obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    // Método para obtener los amigos de un usuario
    public Set<Usuario> obtenerAmigos(Long id) {
        // Buscar el usuario por ID
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // Obtiene el usuario y su lista de amigos usando el método findByUsuario1OrUsuario2
        Usuario usuario = usuarioOpt.get();
        Set<Usuario> amigos = amigoRepositorio.findByUsuario1OrUsuario2(usuario, usuario).stream()
                .flatMap(amigo -> Set.of(amigo.getUsuario1(), amigo.getUsuario2()).stream())
                .filter(u -> !u.equals(usuario))
                .collect(Collectors.toSet());

        return amigos;
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