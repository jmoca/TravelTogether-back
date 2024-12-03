package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.IGrupoServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class NewPartiGroup {

    @Autowired
    private IGrupoServicio grupoServicio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("Añadir participante válido a un grupo no completo")
    void añadirParticipanteValido() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        grupo.setIntegrantes(5);
        grupo.setUsuarios(new HashSet<>());
        grupo = grupoServicio.crearGrupo(grupo, 1L);

        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario = usuarioRepositorio.save(usuario);

        // When
        GrupoDTO resultado = grupoServicio.nuevoPartiGrup(usuario.getId_usuario(), grupo.getId_grupo());

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getUsuarios().size());
        assertTrue(resultado.getUsuarios().stream().anyMatch(u -> u.getId().equals(usuario.getId_usuario())));
    }

    @Test
    @DisplayName("Fallo al añadir participante: Grupo no existe")
    void falloPorGrupoNoExistente() {
        // Then
        assertThrows(RuntimeException.class,
                () -> grupoServicio.nuevoPartiGrup(1L, 999L),
                "No existe el grupo");
    }

    @Test
    @DisplayName("Fallo al añadir participante: Usuario no existe")
    void falloPorUsuarioNoExistente() {
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        grupo = grupoRepositorio.save(grupo);

        // Then
        assertThrows(RuntimeException.class,
                () -> grupoServicio.nuevoPartiGrup(999L, grupo.getId_grupo()),
                "No existe el usuario");
    }

    @Test
    @DisplayName("Fallo al añadir participante: Usuario ya en el grupo")
    void falloPorUsuarioYaEnElGrupo() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        grupo.setIntegrantes(5);
        grupo.setUsuarios(new HashSet<>());

        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        grupo.getUsuarios().add(usuario);
        grupo = grupoRepositorio.save(grupo);
        usuario = usuarioRepositorio.save(usuario);

        // Then
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.nuevoPartiGrup(usuario.getId_usuario(), grupo.getId_grupo()),
                "El usuario ya está en el grupo");
    }
    @Test
    @DisplayName("Fallo al añadir participante: Grupo completo")
    void falloPorGrupoCompleto() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Completo");
        grupo.setIntegrantes(2);
        grupo.setUsuarios(new HashSet<>());

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Usuario 1");
        usuario1 = usuarioRepositorio.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Usuario 2");
        usuario2 = usuarioRepositorio.save(usuario2);

        grupo.getUsuarios().add(usuario1);
        grupo.getUsuarios().add(usuario2);
        grupo = grupoRepositorio.save(grupo);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Usuario 3");
        nuevoUsuario = usuarioRepositorio.save(nuevoUsuario);

        // Then
        assertThrows(IllegalStateException.class,
                () -> grupoServicio.nuevoPartiGrup(nuevoUsuario.getId_usuario(), grupo.getId_grupo()),
                "El grupo ha alcanzado su límite de integrantes");
    }



}
