package dijj.traveltogetherback.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import dijj.traveltogetherback.modelo.Actividad;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IActividadRepositorio extends JpaRepository<Actividad, Long> {

    List<Actividad> findByNombre(String nombre);


}
