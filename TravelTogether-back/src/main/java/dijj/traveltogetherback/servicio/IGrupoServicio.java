package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.DTO.ParticipanteDTO;
import dijj.traveltogetherback.modelo.Grupo;

import java.util.List;

public interface IGrupoServicio {
    public Grupo crearGrupo(Grupo grupo);

    public GrupoDTO nuevoPartiGrup(Long id_usuario, Long id_grupo);

    public ParticipanteDTO verPartiGrupo(Long id_grupo);

    public GrupoDTO eliminarPartiGrup(Long id_usuario, Long id_grupo);

    public List<GrupoDTO> obtenerGruposPorUsuario(Long id_usuario);
}
