package dijj.traveltogetherback.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {
    private Long idVoto;
    private Boolean tipo_voto;
    private Long idActividad;
    private Long idUsuario;
    private LocalDate fechaVoto;
}