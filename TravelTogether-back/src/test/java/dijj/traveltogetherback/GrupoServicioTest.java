package dijj.traveltogetherback;

import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import dijj.traveltogetherback.servicio.IGrupoServicio;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GrupoServicioTest {

    @Autowired
    private IGrupoServicio grupoServicio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServicio usuarioService;

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
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "El número de integrantes no puede ser 0 o negativo"
        );
    }

    @Test
    @DisplayName("Crear grupo correctamente")
    void crearGrupoCorrectamente() {
        //Given
        Grupo grupo = new Grupo();
        grupo.setNombre("Via1 Test");
        grupo.setIntegrantes(5);  // Número de integrantes válido
        grupo.setUsuarios(new HashSet<>());  // Inicializar la lista de usuarios
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");  // Set necessary fields for Usuario
        usuario = usuarioRepositorio.save(usuario);
        grupo.getUsuarios().add(usuario);

        //When
        Grupo resultado = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.getUsuarios().contains(usuario));
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
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "El nombre del viaje debe tener entre 4 y 50 caracteres");

        // Nombre con más de 50 caracteres
        grupo.setNombre("V".repeat(51));
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "El nombre del viaje debe tener entre 4 y 50 caracteres");
    }

}
