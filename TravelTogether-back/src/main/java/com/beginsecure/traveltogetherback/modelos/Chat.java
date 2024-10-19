package com.beginsecure.traveltogetherback.modelos;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "chat", schema = "public", catalog = "postgres")
public class Chat {
    @Id
    @Column(name = "id_chat")
    private Integer id;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha_envio")
    private String fecha;
    @OneToMany
    @JoinColumn(name = "id_grupo", nullable = false)
    private Integer id_grupo;
    @OneToMany
    @JoinColumn(name = "id_usuario", nullable = false)
    private Integer id_usuario;
}
