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
@Table(name = "usuario", schema = "public", catalog = "postgres")
public class Usuario {
    @Id
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "Amigos",
            joinColumns = @JoinColumn(name = "id_usuario1"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario2")
    )
    private Set<Usuario> amigos;


}
