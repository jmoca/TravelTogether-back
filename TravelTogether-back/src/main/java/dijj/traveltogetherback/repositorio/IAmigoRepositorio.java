package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Amigo;
import dijj.traveltogetherback.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAmigoRepositorio extends JpaRepository<Amigo, Long> {
    Amigo findByNombre(String nombre);
}
