package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.repositorio.IActividadRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadServicio implements IActividadServicio {
    private IActividadRepositorio actividadRepositorio;

    public ActividadServicio(IActividadRepositorio actividadRepositorio){
        this.actividadRepositorio = actividadRepositorio;
    }

    @Override
    public List<Actividad> todasLasActividades() {
        return actividadRepositorio.findAll();
    }

    @Override
    public Actividad buscarActividadesPorNombre(String nombre) {
        return actividadRepositorio.findByNombre(nombre);
    }

    @Override
    public Actividad buscarActividadPorId(Long idActividad) {
        return actividadRepositorio.findById(idActividad).orElse(null);
    }

    @Override
    public Actividad guardarActividad(Actividad actividad) {
        return actividadRepositorio.save(actividad);
    }
    public Actividad proponerActividad(Long id_usuario){
        return null;
    }
    public Actividad votarActividad(Long id_usuario, Long id_actividad){
        return null;
    }
}
