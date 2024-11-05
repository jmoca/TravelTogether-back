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

    //@ManyToMany
    //@JoinTable(
    //        name = "Amigos",
    //        joinColumns = @JoinColumn(name = "id_usuario"),
    //        inverseJoinColumns = @JoinColumn(name = "amigo")
    //)
    //private Set<Usuario> amigos;



}