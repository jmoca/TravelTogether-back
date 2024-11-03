package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.repositorio.IActividadRepositorio;

public class ActividadServicio {
    private IActividadRepositorio actividadRepositorio;

    public ActividadServicio(IActividadRepositorio actividadRepositorio){
        this.actividadRepositorio = actividadRepositorio;
    }

}
