package dijj.traveltogetherback.Service;


import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.util.AssertionErrors.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ShowGroupTest {

    @Autowired
    private GrupoServicio grupoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio1;

    @Mock
    private IGrupoRepositorio grupoRepositorio1;

    @InjectMocks
    private GrupoServicio grupoServicio1;

    @Test
    @DisplayName("Listar viajes para un usuario con varios viajes asociados")
    void listarViajesConVariosViajes() {
        // Configurar usuario con viajes
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Juan");
        usuario = usuarioServicio.crearUsuario(usuario);

        Grupo grupo1 = new Grupo();
        grupo1.setNombre("Viaje a la montaña");
        grupo1.setDescripcion("Viaje grupal");
        grupo1.setIntegrantes(5);
        grupo1.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupoServicio.crearGrupo(grupo1, usuario.getId_usuario());

        Grupo grupo2 = new Grupo();
        grupo2.setNombre("Viaje a la playa");
        grupo2.setDescripcion("Viaje grupal");
        grupo2.setIntegrantes(5);
        grupo2.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupoServicio.crearGrupo(grupo2, usuario.getId_usuario());

        Grupo grupo3 = new Grupo();
        grupo3.setNombre("Viaje a la ciudad");
        grupo3.setDescripcion("Viaje grupal");
        grupo3.setIntegrantes(5);
        grupo3.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupoServicio.crearGrupo(grupo3, usuario.getId_usuario());

        // Verificar que tiene varios viajes
        List<GrupoDTO> viajes = grupoServicio.obtenerGruposPorUsuario(usuario.getId_usuario());
        assertEquals("El usuario tiene varios viajes asociados", 3, viajes.size());


    }

    @Test
    @DisplayName("Listar viajes para un usuario sin viajes asociados")
    void listarViajesUsuarioSinViajes() {
        // Configurar usuario sin viajes
        Usuario usuario = new Usuario();
        usuario.setId_usuario(2L);
        usuario.setNombre("María");
        usuarioServicio.crearUsuario(usuario);
        // Verificar que no tiene viajes
        List<GrupoDTO> viajes = grupoServicio.obtenerGruposPorUsuario(usuario.getId_usuario());
        assertEquals("El usuario no tiene viajes asociados", 0, viajes.size());

    }

    @Test
    @DisplayName("Listar viajes para un usuario con exactamente un viaje")
    void listarViajesConUnViaje() {
        // Configurar usuario con un solo viaje
        Usuario usuario = new Usuario();
        usuario.setId_usuario(3L);
        usuario.setNombre("Pedro");
        usuario = usuarioServicio.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la playa");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Verificar que tiene un solo viaje
        List<GrupoDTO> viajes = grupoServicio.obtenerGruposPorUsuario(usuario.getId_usuario());
        assertEquals("El usuario tiene un solo viaje asociado", 1, viajes.size());

    }

    @Test
    @DisplayName("Intentar listar viajes para un usuario no existente")
    void listarViajesUsuarioNoExistente() {
        // Simular un usuario no existente
        Usuario usuario = new Usuario();
        usuario.setId_usuario(2L);
        usuario.setNombre("Pedro");

        // Verificar que no se puede listar viajes para un usuario nulo
        assertThrows(IllegalArgumentException.class, () -> {
            grupoServicio.obtenerGruposPorUsuario(usuario.getId_usuario());
        }, "Se esperaba una excepción por usuario inexistente");
    }

    @Test
    @DisplayName("Listar viajes para un usuario con exactamente un viaje utilizando Mockito")
    void listarViajesConUnViajeMockito() {
        // Mock del usuario
        Usuario usuario = new Usuario();
        usuario.setId_usuario(3L);
        usuario.setNombre("Pedro");

        // Mock del grupo
        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L);
        grupo.setNombre("Viaje a la playa");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));

        // Simular el repositorio de usuarios para que devuelva que el usuario existe
        when(usuarioRepositorio1.existsById(usuario.getId_usuario())).thenReturn(true);

        // Simular el repositorio de grupos para devolver una lista con un solo grupo
        when(grupoRepositorio1.findGruposByUsuarioId(usuario.getId_usuario())).thenReturn(List.of(grupo));

        // Llamar al método de servicio
        List<GrupoDTO> viajes = grupoServicio1.obtenerGruposPorUsuario(usuario.getId_usuario());

        // Verificar el resultado
        assertEquals("El usuario debe tener exactamente un viaje asociado", 1, viajes.size());

        // Verificar que se llamaron los métodos simulados
        verify(usuarioRepositorio1).existsById(usuario.getId_usuario());
        verify(grupoRepositorio1).findGruposByUsuarioId(usuario.getId_usuario());
    }

}
