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

import java.time.LocalDate;
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
        // Validar datos nulos
        if (actividad == null || idUsuario == null || id_grupo == null) {
            throw new IllegalArgumentException("Los datos de la actividad, usuario o grupo no pueden ser nulos");
        }

        // Validar que el usuario existe
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID proporcionado"));

        // Validar que el grupo existe
        Grupo grupo = grupoRepositorio.findById(id_grupo)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado con el ID proporcionado"));

        // Validar si el usuario es participante del grupo
        if (!grupo.getUsuarios().contains(usuario)) {
            throw new IllegalArgumentException("El usuario no pertenece al grupo especificado");
        }

        // Validar la fecha de la actividad
        if (actividad.getFecha() == null || actividad.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la actividad no puede ser una fecha pasada");
        }

        // Validar el nombre de la actividad
        String nombre = actividad.getNombre();
        if (nombre == null || nombre.length() < 4 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre de la actividad debe tener entre 4 y 50 caracteres");
        }

        // Establecer la relación y guardar la actividad
        actividad.setUsuarios(usuario);
        actividad.setGrupo(grupo);
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
