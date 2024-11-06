package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.VotoDTO;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.modelo.Voto;

import dijj.traveltogetherback.repositorio.IActividadRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.repositorio.IVotoRepositorio;

import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Actividad crearActividad(Long idUsuario, Actividad actividad) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            actividad.setUsuarios(usuarioOptional.get());
            return actividadRepositorio.save(actividad);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con id: " + idUsuario);
        }
    }

    // Método para votar por una actividad
    public VotoDTO votarActividad(Long idUsuario, Long idActividad, boolean tipoVoto) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);
        Optional<Actividad> actividadOptional = actividadRepositorio.findById(idActividad);

        if (usuarioOptional.isPresent() && actividadOptional.isPresent()) {
            Voto voto = new Voto();
            voto.setUsuario(usuarioOptional.get());
            voto.setActividad(actividadOptional.get());
            voto.setTipo_voto(tipoVoto);
            Voto votoGuardado = votoRepositorio.save(voto);

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


    public Optional<Actividad> obtenerActividades(Long idGrupo) {
        return actividadRepositorio.findById(idGrupo);
    }
}
