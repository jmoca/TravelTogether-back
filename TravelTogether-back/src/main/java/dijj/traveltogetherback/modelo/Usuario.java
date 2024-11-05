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
    @Column(name = "id_usuario" ,insertable = false, updatable = false)
    private Long idUsuario;

    @Column(name = "nombre")
    private String nombre;

   @ManyToMany
   @JoinTable(
           name = "Amigos",
           joinColumns = @JoinColumn(name = "id_usuario"), // Cambia "usuario" a "id_usuario"
           inverseJoinColumns = @JoinColumn(name = "amigo") // Asume que "amigo" es correcto
   )
   private Set<Usuario> amigos;



}