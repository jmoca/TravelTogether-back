package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuario", schema = "travel", catalog = "postgres")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "usuario1", cascade = CascadeType.ALL)
    private Set<Amigos> amigos1; // Relación de usuario1 en la tabla Amigos

    @OneToMany(mappedBy = "usuario2", cascade = CascadeType.ALL)
    private Set<Amigos> amigos2; // Relación de usuario2 en la tabla Amigos

}
