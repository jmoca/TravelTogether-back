package dijj.traveltogetherback.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private String lugar;
    private String multimedia;
    private String estado;




}
