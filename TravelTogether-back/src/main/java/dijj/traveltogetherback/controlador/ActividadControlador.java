package dijj.traveltogetherback.controlador;

import dijj.traveltogetherback.DTO.ActividadDTO;
import dijj.traveltogetherback.DTO.VotoDTO;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.servicio.ActividadServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaje/actividades")
public class ActividadControlador {

    private final ActividadServicio actividadServicio;

    public ActividadControlador(ActividadServicio actividadServicio) {
        this.actividadServicio = actividadServicio;
    }

    // Crear una nueva actividad
    @PostMapping("/nueva")
    public ResponseEntity<ActividadDTO> proponerActividad(@RequestParam Long id_usuario, @RequestParam Long id_grupo , @RequestBody Actividad actividad) {
        ActividadDTO nuevaActividad = actividadServicio.crearActividad(id_usuario, id_grupo ,actividad);
        return ResponseEntity.ok(nuevaActividad);
    }

    // Votar por una actividad
    @PostMapping("/votar")
    public ResponseEntity<VotoDTO> votarActividad(@RequestParam Long id_usuario, @RequestParam Long id_actividad, @RequestParam boolean tipo_voto) {
        VotoDTO resultadoVoto = actividadServicio.votarActividad(id_usuario, id_actividad, tipo_voto);
        return ResponseEntity.ok(resultadoVoto);
    }

    @GetMapping
    public ResponseEntity<List<ActividadDTO>> verActividades(@RequestParam Long id_grupo) {
        List<ActividadDTO> actividades = actividadServicio.obtenerActividades(id_grupo);
        return ResponseEntity.ok(actividades);
    }


}

