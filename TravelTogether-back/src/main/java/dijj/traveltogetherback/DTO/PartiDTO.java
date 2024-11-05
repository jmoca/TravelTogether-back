package dijj.traveltogetherback.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartiDTO {
    private Long id_grupo;
    private String nombre;

    private String descripcion;
    private int integrantes;
    private String ubicacion;

    private String fechaCreacion;

    private ArrayList<UsuarioDTO> usuarios;

}
