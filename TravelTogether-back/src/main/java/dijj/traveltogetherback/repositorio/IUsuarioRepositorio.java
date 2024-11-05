package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {


}
