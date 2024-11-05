package dijj.traveltogetherback.controlador;


import dijj.traveltogetherback.modelo.Grupo;
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
    public ResponseEntity<Grupo> nuevoViaje(@RequestBody Grupo grupo){
        Grupo nuevogrupo = grupoServicio.crearGrupo(grupo);
        return ResponseEntity.ok(nuevogrupo);

    }
    // RequestParam para recibir parametros
    @PostMapping("/participante/nuevo")
    public ResponseEntity<Grupo> nuevoParticipante(@RequestParam Long id_usuario,@RequestParam Long id_grupo){
        Grupo nuevogrupo = grupoServicio.nuevoPartiGrup(id_usuario, id_grupo);
        return ResponseEntity.ok(nuevogrupo);

    }
    @GetMapping("/participantes")
    public ResponseEntity<Grupo> verParticipante(@RequestParam Long id_grupo){
        Grupo nuevogrupo = grupoServicio.verPartiGrupo(id_grupo).orElseThrow(() -> new RuntimeException("No existe el grupo"));
        return ResponseEntity.ok(nuevogrupo);

    }
    @PostMapping("/participante/eliminar")
    public ResponseEntity<Grupo> eliminarParticipante(@RequestParam Long id_usuario,@RequestParam Long id_grupo){
        Grupo nuevogrupo = grupoServicio.eliminarPartiGrup(id_usuario, id_grupo);
        return ResponseEntity.ok(nuevogrupo);

    }

}
