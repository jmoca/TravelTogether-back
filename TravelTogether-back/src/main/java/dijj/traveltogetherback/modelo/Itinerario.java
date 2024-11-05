package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "itinerario", schema = "Travel", catalog = "postgres")
public class Itinerario {
    @Id
    @Column (name = "id_itinerario")
    private Long id_itinerario;
    @Column (name = "ubicacion")
    private String Ubicacion;
    @Column (name = "descripcion_detallada")
    private String Descripcion;
    @Column (name = "fecha_itinerario")
    private LocalDateTime  Fecha;
    @Column (name = "hora_inicio")
    private LocalDateTime Hora_inicio;
    @Column (name = "hora_fin")
    private LocalDateTime  Hora_fin;
    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

}
