package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;

import java.util.Optional;

public class GrupoServicio implements IGrupoServicio {

    private IGrupoRepositorio grupoRepositorio;
    private IUsuarioRepositorio usuarioRepositorio;

    public GrupoServicio(IGrupoRepositorio grupoRepositorio){
        this.grupoRepositorio = grupoRepositorio;
    }

    public Grupo nuevaGrupo(Grupo grupo){
        return grupoRepositorio.save(grupo);
    }
    public Grupo nuevoPartiGrup(Long id_usuario, Long id_grupo){
        Grupo grupo = grupoRepositorio.findById(id_grupo).orElseThrow(() -> new RuntimeException("No existe el grupo"));
        Usuario usuario = usuarioRepositorio.findById(id_usuario).orElseThrow(()-> new RuntimeException("No existe el grupo"));

        grupo.getUsuarios().add(usuario);
        return grupoRepositorio.save(grupo);
    }
    public Optional<Grupo> verPartiGrupo(Long id_grupo){
        return grupoRepositorio.findById(id_grupo);

    }
}
