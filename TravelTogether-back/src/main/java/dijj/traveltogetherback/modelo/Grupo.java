package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "grupos", schema = "travel")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Long id_grupo;

    @Column(name = "nombre_grupo")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "integrantes")
    private int integrantes;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "multimedia")
    private String multimedia;

    @Column(name = "can_voto")
    private int canVoto;

    @ManyToMany
    @JoinTable(
            name = "usuarios_grupos",
            schema = "travel",
            joinColumns = @JoinColumn(name = "grupo"),
            inverseJoinColumns = @JoinColumn(name = "usuario")
    )
    private Set<Usuario> usuarios = new HashSet<>();

    @Column(name = "id_usuario_creador")
    private Long idUsuarioCreador;
}