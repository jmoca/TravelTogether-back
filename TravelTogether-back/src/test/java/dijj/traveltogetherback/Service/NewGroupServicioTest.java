package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import dijj.traveltogetherback.servicio.IGrupoServicio;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NewGroupServicioTest {

    @Autowired
    private GrupoServicio grupoServicio;


    @Autowired
    private UsuarioServicio usuarioService;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio1;

    @Mock
    private IGrupoRepositorio grupoRepositorio;

    @InjectMocks
    private GrupoServicio grupoServicio1;


    @Test
    @DisplayName("No permitir grupo con integrantes negativos")
    void noPermitirIntegrantesNegativos() {
        //Given
        // Datos iniciales para hacer el test
        Grupo grupo = new Grupo();
        grupo.setIntegrantes(-1);
        grupo.setUsuarios(new HashSet<>());
        Usuario usuario = new Usuario();
        grupo.getUsuarios().add(usuario);

        //When
        //Cuando llamo el metodo del servicio
        // Then
        // Entonces espero que se lance una excepción
        assertThrows(ResponseStatusException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "Usuario no encontrado"
        );

    }
    @Test
    @DisplayName("Crear grupo correctamente")
    void crearGrupoCorrectamenteConMock() {
        // Given
        // Simulamos un usuario que ya existe
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Test User");

        Mockito.when(usuarioRepositorio1.findById(1L)).thenReturn(Optional.of(usuario));

        // Creamos un grupo
        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje Test");
        grupo.setDescripcion("Viaje de prueba");
        grupo.setIntegrantes(1);
        grupo.setMultimedia("https://example.com/multimedia.jpg");
        grupo.setUsuarios(new HashSet<>());

        // Simulamos el guardado del grupo
        Mockito.when(grupoRepositorio.save(grupo)).thenReturn(grupo);

        // Ejecutar el método
        Grupo resultado = grupoServicio1.crearGrupo(grupo, usuario.getId_usuario());

        // Then
        assertNotNull(resultado, "El grupo creado no debe ser nulo");
        assertTrue(
                resultado.getUsuarios().stream().anyMatch(u -> u.getId_usuario().equals(usuario.getId_usuario())),
                "El grupo debe contener al usuario"
        );
    }

    @Test
    @DisplayName("Crear grupo correctamente")
    void crearGrupoCorrectamente() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Test User");
        usuarioService.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje Test");
        grupo.setDescripcion("Viaje de prueba");
        grupo.setIntegrantes(1);
        grupo.setMultimedia("https://example.com/multimedia.jpg");
        grupo.setUsuarios(new HashSet<>());

        // Ejecutar el método
        Grupo resultado = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Then
        assertNotNull(resultado, "El grupo creado no debe ser nulo");
        assertTrue(
                resultado.getUsuarios().stream().anyMatch(u -> u.getId_usuario().equals(usuario.getId_usuario())),
                "El grupo debe contener al usuario"
        );
    }


    @Test
    @DisplayName("Usuario no existe en la base de datos")
    void testUsuarioNoExiste() {
        Long usuarioIdInexistente = 999L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.obtenerUsuarioPorId(usuarioIdInexistente);
        });

        String expectedMessage = "Usuario no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }


    @Test
    @DisplayName("Nombre del viaje con longitud inválida (menos de 4 o más de 50 caracteres)")
    void testNombreViajeLongitudInvalida() {
        Grupo grupo = new Grupo();

        // Nombre con menos de 4 caracteres
        grupo.setNombre("Via");
        assertThrows(ResponseStatusException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "El nombre del viaje debe tener entre 4 y 50 caracteres");

        // Nombre con más de 50 caracteres
        grupo.setNombre("V".repeat(51));
        assertThrows(ResponseStatusException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "El nombre del viaje debe tener entre 4 y 50 caracteres");
    }

    @Test
    @DisplayName("Validar URL multimedia inválida")
    void testValidarURLMultimediaInvalida() {
        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la montaña");
        grupo.setIntegrantes(5);
        grupo.setMultimedia("ftp://invalid-url"); // URL inválida

        assertThrows(ResponseStatusException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "La URL multimedia debe comenzar con http:// o https:// y ser un dominio válido");
    }
    @Test
    @DisplayName("Validar URL multimedia nula")
    void testValidarURLMultimediaNula() {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Test User");
        usuarioService.crearUsuario(usuario);
        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la montaña");
        grupo.setIntegrantes(5);
        grupo.setMultimedia(null); // URL nula permitida

        assertDoesNotThrow(() -> grupoServicio.crearGrupo(grupo, 1L));
    }
}
