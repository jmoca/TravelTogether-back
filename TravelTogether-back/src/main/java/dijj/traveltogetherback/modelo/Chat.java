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
@Table(name = "chat", schema = "public", catalog = "postgres")
public class Chat {
    @Id
    @Column(name = "id_chat")
    private Long id_chat;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha_envio")
    private String fecha;
    @OneToMany
    @JoinColumn(name = "id_grupo", nullable = false)
    private Set<Grupo> id_grupo;
    @OneToMany
    @JoinColumn(name = "id_usuario", nullable = false)
    private Set<Usuario> id_usuario;
}
