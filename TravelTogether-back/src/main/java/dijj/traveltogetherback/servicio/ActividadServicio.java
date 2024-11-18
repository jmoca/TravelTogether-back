package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.ActividadDTO;
import dijj.traveltogetherback.DTO.VotoDTO;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.modelo.Voto;

import dijj.traveltogetherback.repositorio.IActividadRepositorio;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.repositorio.IVotoRepositorio;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActividadServicio {

    private final IActividadRepositorio actividadRepositorio;
    private final IUsuarioRepositorio usuarioRepositorio;
    private final IVotoRepositorio votoRepositorio;
    private final IGrupoRepositorio grupoRepositorio;

    public ActividadServicio(IActividadRepositorio actividadRepositorio, IUsuarioRepositorio usuarioRepositorio, IVotoRepositorio votoRepositorio, IGrupoRepositorio grupoRepositorio) {
        this.actividadRepositorio = actividadRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.votoRepositorio = votoRepositorio;
        this.grupoRepositorio = grupoRepositorio;
    }

    // Método para crear una nueva actividad
    public ActividadDTO crearActividad(Long idUsuario, Long id_grupo, Actividad actividad) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);
        Optional<Grupo> grupoOptional = grupoRepositorio.findById(id_grupo);

        if (usuarioOptional.isPresent() && grupoOptional.isPresent()) {
            actividad.setUsuarios(usuarioOptional.get());
            actividad.setGrupo(grupoOptional.get());
            actividadRepositorio.save(actividad);
            return new ActividadDTO(
                    actividad.getId_actividad(),
                    actividad.getNombre(),
                    actividad.getDescripcion(),
                    actividad.getFecha(),
                    actividad.getLugar(),
                    actividad.getMultimedia(),
                    null
            );
        } else {
            throw new IllegalArgumentException("Usuario o Grupo no encontrado con los IDs proporcionados");
        }
    }

    




    public List<ActividadDTO> obtenerActividades(Long id_grupo) {
        List<Actividad> actividades = actividadRepositorio.findAll();
        return actividades.stream()
                .filter(actividad -> actividad.getGrupo() != null && actividad.getGrupo().getId_grupo().equals(id_grupo))
                .map(actividad -> new ActividadDTO(
                        actividad.getId_actividad(),
                        actividad.getNombre(),
                        actividad.getDescripcion(),
                        actividad.getFecha(),
                        actividad.getLugar(),
                        actividad.getMultimedia(),
                        null
                ))
                .collect(Collectors.toList());
    }



}
