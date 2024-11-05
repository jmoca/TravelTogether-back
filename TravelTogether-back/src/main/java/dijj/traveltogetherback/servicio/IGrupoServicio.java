package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Grupo;

import java.util.List;
import java.util.Optional;

public interface IGrupoServicio {
    public Grupo crearGrupo(Grupo grupo);

    public Grupo nuevoPartiGrup(Long id_usuario, Long id_grupo);

    public Optional<Grupo> verPartiGrupo(Long id_grupo);

    public Grupo eliminarPartiGrup(Long id_usuario, Long id_grupo);

    public List<Grupo> todoGrupo();
}
