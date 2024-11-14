package dijj.traveltogetherback.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteDTO {
    private Long id_grupo;
    private String nombre;

    private String descripcion;
    private int integrantes;
    private String ubicacion;

    private String fechaCreacion;
    private List<UsuarioDTO> usuarios;
}
