package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "registro", schema = "Travel")
public class Registro {
    @Id
    @Column(name = "id_registro")
    private Long id_registro;
    @Column(name = "email")
    private String email;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "fecha_registro")
    private Date fecha_registro;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;

}
