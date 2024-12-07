package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Amigos;
import dijj.traveltogetherback.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface IAmigoRepositorio extends JpaRepository<Amigos, Long> {
    List<Amigos> findByUsuario1OrUsuario2(Usuario usuario1, Usuario usuario2);



}
