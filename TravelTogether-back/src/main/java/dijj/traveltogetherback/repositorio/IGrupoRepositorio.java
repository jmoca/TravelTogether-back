package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGrupoRepositorio extends JpaRepository<Grupo, Long> {

}
