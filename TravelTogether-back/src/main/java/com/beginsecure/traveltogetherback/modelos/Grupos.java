package com.beginsecure.traveltogetherback.modelos;

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


@Table(name = "grupo", schema = "public", catalog = "postgres")
public class Grupos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer id;

    @Column(name = "nombre_grupo")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "integrantes")
    private int integrantes;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    // Relación inversa: Un grupo puede tener muchas actividades
    @OneToMany
    @JoinColumn(name = "id_actividad", nullable = false)
    private List<Actividades> actividades;
}
