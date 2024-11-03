package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Usuario;
import java.time.LocalDateTime;

@Entity
@Table(name = "Votos")
public class Voto {


    @ManyToOne
    @MapsId("idActividad")
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_voto", nullable = false)
    private LocalDateTime fechaVoto = LocalDateTime.now();



}
