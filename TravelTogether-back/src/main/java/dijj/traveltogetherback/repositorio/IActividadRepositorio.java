package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import dijj.traveltogetherback.modelo.Actividad;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IActividadRepositorio extends JpaRepository<Actividad, Long> {
    List<Actividad> findByGrupo(Grupo grupo);

}
