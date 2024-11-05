package dijj.traveltogetherback.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import dijj.traveltogetherback.modelo.Actividad;

public interface IActividadRepositorio extends JpaRepository<Actividad, Long> {

    Actividad findByNombre(String nombre);


}
