package dijj.traveltogetherback.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {
    private Long idVoto;
    private boolean tipoVoto;
    private Long idActividad;
    private Long idUsuario;
    private LocalDateTime fechaVoto;
}