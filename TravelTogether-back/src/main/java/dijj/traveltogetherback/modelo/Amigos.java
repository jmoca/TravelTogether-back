package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"usuario1", "usuario2"})
@ToString(exclude = {"usuario1", "usuario2"})
@Entity
@Table(name = "amigos", schema = "travel")
public class Amigos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario1")
    private Usuario usuario1;

    @ManyToOne
    @JoinColumn(name = "id_usuario2")
    private Usuario usuario2;

    @Column(name = "fecha_amistad")
    private LocalDate fecha_amistad;

}
