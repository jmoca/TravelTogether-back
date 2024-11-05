package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {

}
