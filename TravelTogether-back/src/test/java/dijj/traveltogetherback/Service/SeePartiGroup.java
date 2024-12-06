package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.ParticipanteDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional // Para limpiar datos entre tests
public class SeePartiGroup {

    @Autowired
    private GrupoServicio grupoServicio;

    @Autowired
    private IGrupoRepositorio grupoRepositorio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private GrupoServicio grupoServicio1;

    @Mock
    private IGrupoRepositorio grupoRepositorio1;

    @Test
    @DisplayName("Consultar los participantes de un viaje válido con participantes asignados")
    void consultarParticipantesViajeValidoMockito() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L);
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));

        Usuario usuario1 = new Usuario();
        usuario1.setId_usuario(1L);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setId_usuario(2L);
        usuario2.setNombre("María");

        grupo.setUsuarios(Set.of(usuario1, usuario2));

        // Simulamos el comportamiento del repositorio
        when(grupoRepositorio1.findById(1L)).thenReturn(Optional.of(grupo));

        // When
        ParticipanteDTO participanteDTO = grupoServicio1.verPartiGrupo(1L);

        // Then
        assertNotNull(participanteDTO, "El DTO de participantes no debe ser nulo");
        assertEquals(1L, participanteDTO.getId_grupo(), "El ID del grupo debe coincidir");
        assertEquals(2, participanteDTO.getUsuarios().size(), "El grupo debe tener dos participantes");
        assertEquals("Viaje a la montaña", participanteDTO.getNombre(), "El nombre del grupo debe coincidir");

        // Verificamos que el método del repositorio fue llamado
        verify(grupoRepositorio1, times(1)).findById(1L);
    }
    @Test
    @DisplayName("Consultar los participantes de un viaje válido con participantes asignados")
    void consultarParticipantesViajeValido() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("María");

        usuarioRepositorio.save(usuario1);
        usuarioRepositorio.save(usuario2);

        grupo.setUsuarios(new HashSet<>(usuarioRepositorio.findAll()));
        grupoRepositorio.save(grupo);

        // When
        ParticipanteDTO participanteDTO = grupoServicio.verPartiGrupo(grupo.getId_grupo());

        // Then
        assertNotNull(participanteDTO, "El DTO de participantes no debe ser nulo");
        assertEquals(grupo.getId_grupo(), participanteDTO.getId_grupo(), "El ID del grupo debe coincidir");
        assertEquals(2, participanteDTO.getUsuarios().size(), "El grupo debe tener dos participantes");
        assertEquals("Viaje a la montaña", participanteDTO.getNombre(), "El nombre del grupo debe coincidir");
    }

    @Test
    @DisplayName("Consultar un viaje sin participantes")
    void consultarViajeSinParticipantes() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje sin participantes");
        grupo.setDescripcion("Un viaje vacío");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupo.setUsuarios(new HashSet<>()); // Sin usuarios
        grupoRepositorio.save(grupo);

        // When
        ParticipanteDTO participanteDTO = grupoServicio.verPartiGrupo(grupo.getId_grupo());

        // Then
        assertNotNull(participanteDTO, "El DTO de participantes no debe ser nulo");
        assertEquals(grupo.getId_grupo(), participanteDTO.getId_grupo(), "El ID del grupo debe coincidir");
        assertTrue(participanteDTO.getUsuarios().isEmpty(), "El grupo no debe tener participantes");
    }

    @Test
    @DisplayName("Consultar un viaje con exactamente un participante")
    void consultarViajeConUnParticipante() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje con un participante");
        grupo.setDescripcion("Viaje con un único usuario");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));

        Usuario usuario = new Usuario();
        usuario.setNombre("Pedro");

        usuarioRepositorio.save(usuario);

        grupo.setUsuarios(new HashSet<>(usuarioRepositorio.findAll()));
        grupoRepositorio.save(grupo);

        // When
        ParticipanteDTO participanteDTO = grupoServicio.verPartiGrupo(grupo.getId_grupo());

        // Then
        assertNotNull(participanteDTO, "El DTO de participantes no debe ser nulo");
        assertEquals(grupo.getId_grupo(), participanteDTO.getId_grupo(), "El ID del grupo debe coincidir");
        assertEquals(1, participanteDTO.getUsuarios().size(), "El grupo debe tener exactamente un participante");
        assertEquals("Pedro", participanteDTO.getUsuarios().get(0).getNombre(), "El nombre del único participante debe coincidir");
    }
}
