package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio  implements IUsuarioService{

    private IUsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Usuario> todosLosAmigos(){
        return usuarioRepositorio.findAll();
    }
}
