package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGrupoRepositorio extends JpaRepository<Grupo, Long> {
    @Query("SELECT g FROM Grupo g JOIN g.usuarios u WHERE u.id_usuario = :id_usuario")
    List<Grupo> findGruposByUsuarioId(@Param("id_usuario") Long id_usuario);
}
