package dijj.traveltogetherback.repositorio;

import dijj.traveltogetherback.modelo.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IVotoRepositorio extends JpaRepository<Voto, Long> {

    @Query("SELECT v.actividad.id_actividad AS idActividad, " +
            "SUM(CASE WHEN v.tipo_voto = true THEN 1 ELSE -1 END) AS balanceVotos " +
            "FROM Voto v " +
            "GROUP BY v.actividad.id_actividad")
    List<Object[]> totalvotos();
}
