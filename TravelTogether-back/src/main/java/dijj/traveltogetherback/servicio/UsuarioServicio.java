package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.modelo.Amigos;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.repositorio.IAmigoRepositorio;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioServicio implements IUsuarioService {

    private final IUsuarioRepositorio usuarioRepositorio;
    private final IAmigoRepositorio amigosRepositorio;

    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio, IAmigoRepositorio amigosRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.amigosRepositorio = amigosRepositorio;
    }

    // Método para obtener todos los amigos de un usuario
    //public Set<Usuario> todosLosAmigos(Long id_usuario) {
    //    // Obtener el usuario
    //    Usuario usuario = usuarioRepositorio.findById(id_usuario)
    //            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id_usuario));
//
    //    // Conjunto para almacenar los amigos
    //    Set<Usuario> amigos = new HashSet<>();
//
    //    // Buscar las relaciones de amistad donde el usuario es usuario1 o usuario2
    //    Set<Amigos> relacionesAmigos = amigosRepositorio.findByUsuario1OrUsuario2(usuario, usuario);
//
    //    // Iterar sobre las relaciones y agregar los amigos al conjunto
    //    for (Amigos amistad : relacionesAmigos) {
    //        if (amistad.getUsuario1().equals(usuario)) {
    //            amigos.add(amistad.getUsuario2());
    //        } else {
    //            amigos.add(amistad.getUsuario1());
    //        }
    //    }
//
    //    return amigos;
    //}
}
