package dijj.traveltogetherback;

import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import dijj.traveltogetherback.servicio.IGrupoServicio;
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


    @Test
    @DisplayName("No permitir grupo con integrantes negativos")
    void noPermitirIntegrantesNegativos() {
        // Preparar datos para el test
        Grupo grupo = new Grupo();
        grupo.setIntegrantes(-1);  // Integrantes negativos
        grupo.setUsuarios(new HashSet<>());  // Inicializar la lista de usuarios

        Usuario usuario = new Usuario();
        grupo.getUsuarios().add(usuario);  // Agregar un usuario al grupo

        // Ejecutar el método y verificar la excepción
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "El número de integrantes no puede ser 0 o negativo"
        );
    }

    @Test
    @DisplayName("Crear grupo correctamente")
    void crearGrupoCorrectamente() {
        // Preparar datos para el test
        Grupo grupo = new Grupo();
        grupo.setIntegrantes(5);  // Número de integrantes válido
        grupo.setUsuarios(new HashSet<>());  // Inicializar la lista de usuarios

        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");  // Set necessary fields for Usuario

        // Save the Usuario entity
        usuario = usuarioRepositorio.save(usuario);

        // Agregar el usuario al grupo
        grupo.getUsuarios().add(usuario);

        // Ejecutar el método y verificar el resultado
        Grupo resultado = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Verificar que el grupo fue creado correctamente
        assertNotNull(resultado);
        assertTrue(resultado.getUsuarios().contains(usuario));  // Verificar que el usuario está en el grupo
    }


    void nousuarioGrupo() {
        // Preparar datos para el test
        Grupo grupo = new Grupo();
        grupo.setIntegrantes(-1);  // Integrantes negativos
        grupo.setUsuarios(new HashSet<>());  // Inicializar la lista de usuarios

        Usuario usuario = new Usuario();
        grupo.getUsuarios().add(usuario);  // Agregar un usuario al grupo

        // Ejecutar el método y verificar la excepción
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.crearGrupo(grupo, 1L),
                "El número de integrantes no puede ser 0 o negativo"
        );
    }
}
