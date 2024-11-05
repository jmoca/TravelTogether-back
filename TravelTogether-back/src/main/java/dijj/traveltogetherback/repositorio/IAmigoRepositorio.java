package dijj.traveltogetherback.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAmigoRepositorio extends JpaRepository<Amigo, Long> {
    Amigo findByNombre(String nombre);
}
