package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "grupos", schema = "travel", catalog = "postgres")
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



    @ManyToMany
    @JoinTable(
            name = "usuarios_grupos",
            schema = "travel",
            joinColumns = @JoinColumn(name = "grupo"),
            inverseJoinColumns = @JoinColumn(name = "usuario")
    )
    private List<Usuario> usuarios = new ArrayList<>();

}
