package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAmigoRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByNombre(String nombre);
}
