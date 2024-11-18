package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.VotoDTO;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.modelo.Voto;
import dijj.traveltogetherback.repositorio.IActividadRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
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

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IActividadRepositorio actividadRepositorio;

    public VotoDTO actualizarVoto(Long id_usuario, Long id_actividad, boolean tipo_voto) {
        // Verificamos si el usuario ya ha votado en la actividad
        Voto votoExistente = votoRepositorio.findvotoId(id_usuario, id_actividad);

        // Si existe, actualizamos el voto
        if (votoExistente != null) {
            votoExistente.setTipo_voto(tipo_voto);  // Actualizamos el tipo de voto
            votoRepositorio.save(votoExistente);  // Guardamos el voto actualizado
            // Devolvemos un VotoDTO con los datos del voto actualizado
            return new VotoDTO(votoExistente.getId_voto(), votoExistente.getTipo_voto(),
                    votoExistente.getActividad().getId_actividad(),
                    votoExistente.getUsuario().getId_usuario(), votoExistente.getFechaVoto());
        } else {
            // Si no existe, creamos un nuevo voto
            Voto nuevoVoto = new Voto();
            nuevoVoto.setTipo_voto(tipo_voto);  // Establecemos el tipo de voto

            // Aquí tendríamos que asignar la actividad y el usuario al nuevo voto
            // Si estás recibiendo estos valores desde algún lugar, asegúrate de asignarlos
            Actividad actividad = new Actividad();
            actividad.setId_actividad(id_actividad);
            Usuario usuario = new Usuario();
            usuario.setId_usuario(id_usuario);

            nuevoVoto.setActividad(actividad);
            nuevoVoto.setUsuario(usuario);

            // Guardamos el nuevo voto
            votoRepositorio.save(nuevoVoto);

            // Devolvemos un VotoDTO con los datos del nuevo voto
            return new VotoDTO(nuevoVoto.getId_voto(), nuevoVoto.getTipo_voto(),
                    nuevoVoto.getActividad().getId_actividad(),
                    nuevoVoto.getUsuario().getId_usuario(), nuevoVoto.getFechaVoto());
        }
    }

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



