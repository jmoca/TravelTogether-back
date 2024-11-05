package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.repositorio.IActividadRepositorio;

import java.util.List;

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
    public List<Actividad> buscarActividadesPorNombre(String nombre) {
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

    @Override
    public void eliminarActividad(Long idActividad) {
        actividadRepositorio.deleteById(idActividad);

    }

    public Actividad nuevaActividad(Long id_usuario){
        return null;
    }
}
