package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.IGrupoServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NewPartiGroup {

    @Autowired
    private IGrupoServicio grupoServicio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("Añadir participante válido a un grupo no completo")
    void anadirParticipanteValido() {
        // Given
        final Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test1");
        usuario.setId_usuario(1L);
        final Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
        final Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        grupo.setIntegrantes(5); // Grupo con capacidad para 5 usuarios
        grupo.setUsuarios(new HashSet<>());
        final Grupo grupoCreado = grupoServicio.crearGrupo(grupo, usuarioGuardado.getId_usuario());
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("Usuario Test2");
        usuario2.setId_usuario(2L);
        final Usuario usuarioGuardado2 = usuarioRepositorio.save(usuario2);

        // When
        GrupoDTO resultado = grupoServicio.nuevoPartiGrup(usuarioGuardado2.getId_usuario(), grupoCreado.getId_grupo());

        // Then
        assertNotNull(resultado, "El grupo debe ser creado correctamente");
        assertEquals(1L, resultado.getIdUsuarioCreador(), "El grupo debe contener el usuario creador");
        assertTrue(resultado.getUsuarios().stream().anyMatch(u -> u.getId_usuario().equals(usuarioGuardado2.getId_usuario())), "El grupo debe contener al usuario agregado");
    }

    @Test
    @DisplayName("Fallo al añadir participante: Grupo no existe")
    void falloPorGrupoNoExistente() {
        // Then
        assertThrows(RuntimeException.class,
                () -> grupoServicio.nuevoPartiGrup(1L, 999L), // 999L es un ID de grupo inexistente
                "No existe el grupo");
    }

    @Test
    @DisplayName("Fallo al añadir participante: Usuario no existe")
    void falloPorUsuarioNoExistente() {
        // Given
        final Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        final Grupo grupoCreado = grupoServicio.crearGrupo(grupo, 1L);

        // Then
        assertThrows(RuntimeException.class,
                () -> grupoServicio.nuevoPartiGrup(999L, grupoCreado.getId_grupo()), // 999L es un ID de usuario inexistente
                "No existe el usuario");
    }


    @Test
    @DisplayName("Fallo al añadir participante: Usuario ya en el grupo")
    void falloPorUsuarioYaEnElGrupo() {
        // Given
        final Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        grupo.setIntegrantes(5);
        grupo.setUsuarios(new HashSet<>());
        final Grupo grupoCreado = grupoServicio.crearGrupo(grupo, 1L);

        final Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        final Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        // Añadimos el usuario al grupo
        grupoServicio.nuevoPartiGrup(usuarioGuardado.getId_usuario(), grupoCreado.getId_grupo());

        // Then
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.nuevoPartiGrup(usuarioGuardado.getId_usuario(), grupoCreado.getId_grupo()), // Intentamos agregar al mismo usuario
                "El usuario ya está en el grupo");
    }


    @Test
    @DisplayName("Fallo al añadir participante: Grupo completo")
    void falloPorGrupoCompleto() {
        // Given
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("Usuario 1");
        usuario1.setId_usuario(1L);
        final Usuario usuario1Creado = usuarioRepositorio.save(usuario1);

        final Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Completo");
        grupo.setIntegrantes(2); // Grupo con capacidad para 2 integrantes
        grupo.setUsuarios(new HashSet<>());
        final Grupo grupoCreado = grupoServicio.crearGrupo(grupo, 1L);

        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("Usuario 2");
        usuario2.setId_usuario(2L);
        final Usuario usuario2Creado = usuarioRepositorio.save(usuario2);

        // Añadimos los dos primeros usuarios al grupo
        grupoServicio.nuevoPartiGrup(usuario1Creado.getId_usuario(), grupoCreado.getId_grupo());
        grupoServicio.nuevoPartiGrup(usuario2Creado.getId_usuario(), grupoCreado.getId_grupo());

        // Ahora intentamos añadir un tercer usuario al grupo completo
        final Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Usuario 3");
        final Usuario usuario3Creado = usuarioRepositorio.save(nuevoUsuario);

        // Then
        assertThrows(IllegalStateException.class,
                () -> grupoServicio.nuevoPartiGrup(usuario3Creado.getId_usuario(), grupoCreado.getId_grupo()), // Intentamos agregar a un grupo lleno
                "El grupo ha alcanzado su límite de integrantes");
    }

}
