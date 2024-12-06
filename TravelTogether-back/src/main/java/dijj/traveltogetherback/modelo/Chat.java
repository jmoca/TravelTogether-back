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
@Table(name = "chat", schema = "Travel")
public class Chat {
    @Id
    @Column(name = "id_chat")
    private Long id_chat;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_envio")
    private String fecha;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo id_grupo;


}
