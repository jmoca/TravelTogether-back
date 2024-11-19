package dijj.traveltogetherback.controlador;


import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.DTO.ParticipanteDTO;
import dijj.traveltogetherback.DTO.eliminarDTO;
import dijj.traveltogetherback.servicio.GrupoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaje")
public class GrupoControlador {

    private final GrupoServicio grupoServicio;

    public GrupoControlador(GrupoServicio grupoServicio) {
        this.grupoServicio = grupoServicio;
    }
// RequestBody para devovler json
    @PostMapping("/nuevo")
    public ResponseEntity<GrupoDTO> nuevoViaje(@RequestBody GrupoDTO grupo){
        GrupoDTO nuevogrupo = grupoServicio.crearGrupo(grupo);
        return ResponseEntity.ok(nuevogrupo);

    }
    // RequestParam para recibir parametros
    @PostMapping("/participante/nuevo")
    public ResponseEntity<GrupoDTO> nuevoParticipante(@RequestParam Long id_usuario, @RequestParam Long id_grupo){
        GrupoDTO nuevogrupo = grupoServicio.nuevoPartiGrup(id_usuario, id_grupo);
        return ResponseEntity.ok(nuevogrupo);

    }
    @GetMapping("/participantes")
    public ResponseEntity<ParticipanteDTO> verParticipante(@RequestParam Long id_grupo){
        ParticipanteDTO nuevogrupo = grupoServicio.verPartiGrupo(id_grupo);
        return ResponseEntity.ok(nuevogrupo);

    }
    @DeleteMapping ("/participante/eliminar")
    public ResponseEntity<GrupoDTO> eliminarParticipante(@RequestBody eliminarDTO eliminar){
        GrupoDTO nuevogrupo = grupoServicio.eliminarPartiGrup(eliminar.getId_usuario(), eliminar.getId_grupo());
        return ResponseEntity.ok(nuevogrupo);

    }

}
