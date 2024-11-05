package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Votos")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voto")
    private Long id_voto;

    @Column(name = "tipo_voto")
    private boolean tipo_voto;

    @ManyToOne
    @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad", nullable = false)
    private Actividad actividad;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_voto", nullable = false)
    private LocalDateTime fechaVoto = LocalDateTime.now();

}
