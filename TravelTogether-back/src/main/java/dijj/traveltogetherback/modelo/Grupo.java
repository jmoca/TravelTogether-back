package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "grupos", schema = "public", catalog = "postgres")
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

    // Relación inversa: Un grupo puede tener muchas actividades
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actividad> actividades;

    @ManyToMany
    @JoinTable(
            name = "usuarios_grupos",
            schema = "public",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_grupo")
    )
    private Set<Usuario> usuarios;
}
