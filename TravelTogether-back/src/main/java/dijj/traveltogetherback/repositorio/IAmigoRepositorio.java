package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Amigos;
import dijj.traveltogetherback.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IAmigoRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByNombre(String nombre);



}
