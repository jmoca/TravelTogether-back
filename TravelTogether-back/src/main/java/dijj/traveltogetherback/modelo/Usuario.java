package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"usuario1", "usuario2"})
@ToString(exclude = {"usuario1", "usuario2"})
@Entity
@Table(name = "usuario", schema = "travel")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "usuario1", cascade = CascadeType.ALL)
    private List<Amigos> amigos1; // Relación de usuario1 en la tabla Amigos

    @OneToMany(mappedBy = "usuario2", cascade = CascadeType.ALL)
    private List<Amigos> amigos2; // Relación de usuario2 en la tabla Amigos

    public Set<Usuario> getAmigos() {
        // Utiliza un conjunto para evitar duplicados
        Set<Usuario> amigos = new HashSet<>();

        // Agrega los amigos de la relación amigos1
        for (Amigos amigo : amigos1) {
            amigos.add(amigo.getUsuario2());
        }

        // Agrega los amigos de la relación amigos2
        for (Amigos amigo : amigos2) {
            amigos.add(amigo.getUsuario1());
        }

        return amigos;
    }


}
