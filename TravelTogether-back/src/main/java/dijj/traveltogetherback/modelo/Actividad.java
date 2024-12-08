package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "actividades", schema = "travel")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long id_actividad;

    @Column(name = "nombre_actividad")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "multimedia")
    private String multimedia;

    @Column(name = "fecha_actividad_inicio")
    private LocalDate fecha_inicio;

    @Column(name = "fecha_actividad_fin")
    private LocalDate fecha_fin;

    @Column(name = "lugar")
    private String lugar;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuarios;

}
