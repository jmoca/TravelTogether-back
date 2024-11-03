package dijj.traveltogetherback.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Table(name = "usuario", schema = "public", catalog = "postgres")
public class Usuario {
    @Id
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
}
