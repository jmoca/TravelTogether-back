package dijj.traveltogetherback.repositorio;

import  dijj.traveltogetherback.modelo.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChatRepositorio extends JpaRepository<Chat, Long> {
}
