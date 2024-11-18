package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "votos", schema = "Travel", catalog = "postgres")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voto")
    private Long id_voto;

    @Column(name = "tipo_voto")
    private boolean tipo_voto;

    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_voto")
    private LocalDateTime fechaVoto = LocalDateTime.now();
    public boolean getTipo_voto() {
        return tipo_voto;
    }

    public void setTipo_voto(boolean tipo_voto) {
        this.tipo_voto = tipo_voto;
    }


}
