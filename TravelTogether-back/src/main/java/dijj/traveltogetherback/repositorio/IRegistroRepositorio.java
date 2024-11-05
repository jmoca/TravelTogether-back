package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegistroRepositorio extends JpaRepository<Registro, Long> {
}
