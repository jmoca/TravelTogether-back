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
public class eliminarDTO {
    private Long id_usuario;
    private Long id_grupo;
    private ArrayList<UsuarioDTO> usuarios;


}
