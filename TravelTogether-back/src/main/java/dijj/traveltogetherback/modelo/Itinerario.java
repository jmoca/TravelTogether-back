package dijj.traveltogetherback.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "itinerario", schema = "public", catalog = "postgres")
public class Itinerario {
    @Id
    @Column (name = "id_itinerario")
    private Long id;
    @Column (name = "ubicacion")
    private String Ubicacion;
    @Column (name = "descripcion_detallada")
    private String Descripcion;
    @Column (name = "fecha_itinerario")
    private Date Fecha;
    @Column (name = "hora_inicio")
    private Date Hora_inicio;
    @Column (name = "hora_fin")
    private Date Hora_fin;
    @ManyToAny
    @JoinColumn(name = "id_actividad", nullable = false)
    private Integer id_actividad;
}
