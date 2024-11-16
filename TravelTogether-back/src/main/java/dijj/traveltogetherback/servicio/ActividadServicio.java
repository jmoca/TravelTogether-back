package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.ActividadDTO;
import dijj.traveltogetherback.DTO.VotoDTO;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.modelo.Voto;

import dijj.traveltogetherback.repositorio.IActividadRepositorio;
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

    public ActividadServicio(IActividadRepositorio actividadRepositorio, IUsuarioRepositorio usuarioRepositorio, IVotoRepositorio votoRepositorio) {
        this.actividadRepositorio = actividadRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.votoRepositorio = votoRepositorio;
    }

    // Método para crear una nueva actividad
    public ActividadDTO crearActividad(Long idUsuario, Actividad actividad) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            actividad.setUsuarios(usuarioOptional.get());
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
            throw new IllegalArgumentException("Usuario no encontrado con id: " + idUsuario);
        }
    }

    // Método para votar por una actividad
    public VotoDTO votarActividad(Long idUsuario, Long idActividad, boolean tipoVoto) {
        // Buscar el usuario y la actividad
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);
        Optional<Actividad> actividadOptional = actividadRepositorio.findById(idActividad);

        if (usuarioOptional.isPresent() && actividadOptional.isPresent()) {
            // Buscar si el usuario ya ha votado por esta actividad
            Optional<Voto> votoExistente = votoRepositorio.findById(idActividad);

            Voto voto;
            if (votoExistente.isPresent()) {
                // Si el voto ya existe, actualizarlo
                voto = votoExistente.get();
                voto.setTipo_voto(tipoVoto);  // Cambiar el tipo de voto
                voto.setFechaVoto(LocalDateTime.now());  // Actualizar la fecha de voto si lo deseas
            } else {
                // Si no existe, crear un nuevo voto
                voto = new Voto();
                voto.setUsuario(usuarioOptional.get());
                voto.setActividad(actividadOptional.get());
                voto.setTipo_voto(tipoVoto);
                voto.setFechaVoto(LocalDateTime.now());  // Establecer la fecha de voto
            }

            // Guardar el voto (ya sea actualizado o nuevo)
            Voto votoGuardado = votoRepositorio.save(voto);

            // Retornar el DTO con los datos del voto guardado
            return new VotoDTO(
                    votoGuardado.getId_voto(),
                    votoGuardado.isTipo_voto(),
                    votoGuardado.getActividad().getId_actividad(),
                    votoGuardado.getUsuario().getId_usuario(),
                    votoGuardado.getFechaVoto()
            );
        } else {
            throw new IllegalArgumentException("Usuario o Actividad no encontrada con los IDs proporcionados");
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
