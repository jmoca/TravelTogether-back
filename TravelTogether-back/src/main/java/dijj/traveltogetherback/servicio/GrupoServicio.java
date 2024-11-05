package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GrupoServicio implements IGrupoServicio {

    private IGrupoRepositorio grupoRepositorio;
    private IUsuarioRepositorio usuarioRepositorio;

    public GrupoServicio(IGrupoRepositorio grupoRepositorio){
        this.grupoRepositorio = grupoRepositorio;
    }

    public Grupo crearGrupo(Grupo grupo){
        return grupoRepositorio.save(grupo);
    }
    public Grupo nuevoPartiGrup(Long idUsuario, Long id_grupo){
        Grupo grupo = grupoRepositorio.findById(id_grupo).orElseThrow(() -> new RuntimeException("No existe el grupo"));
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(()-> new RuntimeException("No existe el grupo"));

        grupo.getUsuarios().add(usuario);
        return grupoRepositorio.save(grupo);
    }
    public Optional<Grupo> verPartiGrupo(Long id_grupo){
        return grupoRepositorio.findById(id_grupo);
    }
    public Grupo eliminarPartiGrup(Long id_usuario, Long id_grupo){
        Grupo grupo = grupoRepositorio.findById(id_grupo).orElseThrow(() -> new RuntimeException("No existe el grupo"));
        Usuario usuario = usuarioRepositorio.findById(id_usuario).orElseThrow(()-> new RuntimeException("No existe el grupo"));

        grupo.getUsuarios().remove(usuario);
        return grupoRepositorio.save(grupo);
    }
    public List<Grupo> todoGrupo(){
        return grupoRepositorio.findAll();
    }
}
