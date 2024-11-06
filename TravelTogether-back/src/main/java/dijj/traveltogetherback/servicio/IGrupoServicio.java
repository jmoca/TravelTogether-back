package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.PartiDTO;
import dijj.traveltogetherback.modelo.Grupo;

import java.util.Optional;

public interface IGrupoServicio {
    public Grupo crearGrupo(Grupo grupo);

    public PartiDTO nuevoPartiGrup(Long id_usuario, Long id_grupo);

    public PartiDTO verPartiGrupo(Long id_grupo);

    public PartiDTO eliminarPartiGrup(Long id_usuario, Long id_grupo);

    public Optional<Grupo> obtenerGruposPorUsuario(Long id_usuario);
}
