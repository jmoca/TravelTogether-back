package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServicio  implements IUsuarioService {

    private IUsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Set<Usuario> todosLosAmigos(Long id_usuario){

            // usuarioRepositorio.findById(id_usuario).get().getAmigos();
        return null;
    }
}
