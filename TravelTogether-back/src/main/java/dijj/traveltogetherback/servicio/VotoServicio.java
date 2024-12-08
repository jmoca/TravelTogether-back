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

import java.time.LocalDate;
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

    public VotoDTO actualizarVoto(Long id_usuario, Long id_actividad, Boolean tipo_voto) {
        // Validar que el usuario exista
        Usuario usuario = usuarioRepositorio.findById(id_usuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id_usuario));

        // Validar que la actividad exista
        Actividad actividad = actividadRepositorio.findById(id_actividad)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + id_actividad));

        // Validar que el tipo de voto no sea nulo
        if (tipo_voto == null) {
            throw new IllegalArgumentException("El tipo de voto no puede ser nulo");
        }

        // Verificar si el usuario ya votó en la actividad
        Voto votoExistente = votoRepositorio.findvotoId(id_usuario, id_actividad);
        if (votoExistente != null) {
            // Actualizar el voto existente
            votoExistente.setTipo_voto(tipo_voto);
            votoRepositorio.save(votoExistente);
            return new VotoDTO(votoExistente.getId_voto(), votoExistente.getTipo_voto(),
                    votoExistente.getActividad().getId_actividad(),
                    votoExistente.getUsuario().getId_usuario(), votoExistente.getFechaVoto());
        }

        // Validar el número máximo de votos para la actividad
        int totalVotos = votoRepositorio.countByActividadId(id_actividad);
        final int MAX_VOTOS = 10;
        if (totalVotos >= MAX_VOTOS) {
            throw new IllegalStateException("Se ha alcanzado el número máximo de votos para esta actividad");
        }

        // Crear un nuevo voto
        Voto nuevoVoto = new Voto();
        nuevoVoto.setTipo_voto(tipo_voto);
        nuevoVoto.setUsuario(usuario);
        nuevoVoto.setActividad(actividad);
        nuevoVoto.setFechaVoto(LocalDate.now());

        votoRepositorio.save(nuevoVoto);

        return new VotoDTO(nuevoVoto.getId_voto(), nuevoVoto.getTipo_voto(),
                nuevoVoto.getActividad().getId_actividad(),
                nuevoVoto.getUsuario().getId_usuario(), nuevoVoto.getFechaVoto());
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



