package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.modelo.Grupo;

import java.util.Optional;

public interface IGrupoServicio {
    public Grupo crearGrupo(Grupo grupo);

    public GrupoDTO nuevoPartiGrup(Long id_usuario, Long id_grupo);

    public GrupoDTO verPartiGrupo(Long id_grupo);

    public GrupoDTO eliminarPartiGrup(Long id_usuario, Long id_grupo);

    public Optional<Grupo> obtenerGruposPorUsuario(Long id_usuario);
}
