package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Actividad;

import java.util.List;

public interface IActividadServicio {

    List<Actividad> todasLasActividades();
    Actividad buscarActividadesPorNombre(String nombre);

    Actividad buscarActividadPorId(Long idActividad);

    Actividad guardarActividad(Actividad actividad);

}
