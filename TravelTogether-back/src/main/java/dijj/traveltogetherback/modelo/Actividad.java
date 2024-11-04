package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Table(name = "actividades", schema = "public", catalog = "postgres")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long id;
    @Column(name = "nombre_actividad")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_actividad")
    private String fecha;
    // Relación con Grupos
    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Set<Grupos> grupo;
}
