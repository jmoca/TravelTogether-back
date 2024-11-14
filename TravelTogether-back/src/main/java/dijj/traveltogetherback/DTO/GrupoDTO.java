package dijj.traveltogetherback.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDTO {
    private Long id_grupo;
    private String nombre;

    private String descripcion;
    private int integrantes;
    private String ubicacion;

    private String fechaCreacion;



}
