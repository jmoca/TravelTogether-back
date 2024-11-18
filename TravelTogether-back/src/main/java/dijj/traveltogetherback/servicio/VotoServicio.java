package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.repositorio.IVotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VotoServicio {

    @Autowired
    private IVotoRepositorio votoRepositorio;


    public List<Map<String, Object>> votostotalActividad(Long id_actividad) {
        List<Object[]> resultados = votoRepositorio.totalvotos();

        // Convertimos el resultado a una lista de mapas para mayor claridad
        List<Map<String, Object>> listatotal = new ArrayList<>();
        for (Object[] resultado : resultados) {
            if (resultado[0].equals(id_actividad)) {
                Map<String, Object> balance = new HashMap<>();
                balance.put("id_actividad", resultado[0]); // ID de la actividad
                balance.put("balanceVotos", resultado[1]); // Balance de votos
                listatotal.add(balance);
            }

        }

        return listatotal;
    }
}



