package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import dijj.traveltogetherback.servicio.IGrupoServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashSet;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NewPartiGroup {

    @Autowired
    private IGrupoServicio grupoServicio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;


    @Mock
    private IGrupoRepositorio grupoRepositorio1;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio1;

    @InjectMocks
    private GrupoServicio grupoServiciomock;


    @Test
    @DisplayName("Añadir participante válido a un grupo no completo")
    void anadirParticipanteValidoConMockito() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setId_usuario(1L);
        usuario1.setNombre("Usuario 1");
        when(usuarioRepositorio1.findById(1L)).thenReturn(Optional.of(usuario1));

        Grupo grupo = new Grupo();
        grupo.setIdUsuarioCreador(1L);
        grupo.setNombre("Grupo Test");
        grupo.setIntegrantes(5);
        grupo.setUsuarios(new HashSet<>());
        grupo.setId_grupo(1L); // Ensure the group has an ID
        when(grupoRepositorio1.save(any(Grupo.class))).thenReturn(grupo);
        when(grupoRepositorio1.findById(1L)).thenReturn(Optional.of(grupo)); // Mock findById to return the group

        Usuario usuario2 = new Usuario();
        usuario2.setId_usuario(2L);
        usuario2.setNombre("Usuario 2");
        when(usuarioRepositorio1.findById(2L)).thenReturn(Optional.of(usuario2));

        // When
        GrupoDTO resultado = grupoServiciomock.nuevoPartiGrup(2L, grupo.getId_grupo());

        // Then
        assertNotNull(resultado, "El grupo debe ser creado correctamente");
        assertEquals(1L, resultado.getIdUsuarioCreador(), "El grupo debe contener el usuario creador");
        assertTrue(resultado.getUsuarios().stream().anyMatch(u -> u.getId().equals(2L)), "El grupo debe contener al usuario agregado");

        verify(grupoRepositorio1).findById(1L);
        verify(usuarioRepositorio1, times(1)).save(any(Usuario.class));
        verify(usuarioRepositorio1).findById(2L);
    }
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
        final Usuario usuarioCreador = new Usuario();
        usuarioCreador.setId_usuario(1L);
        usuarioCreador.setNombre("Usuario Test");
        usuarioRepositorio.save(usuarioCreador);

        final Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        grupo.setIntegrantes(5);
        grupo.setUsuarios(new HashSet<>());
        final Grupo grupoCreado = grupoServicio.crearGrupo(grupo, usuarioCreador.getId_usuario());

        // When & Then
        assertThrows(RuntimeException.class, // Cambia según la excepción esperada
                () -> grupoServicio.nuevoPartiGrup(999L, grupoCreado.getId_grupo()),
                "Se esperaba una excepción al intentar añadir un usuario inexistente");
    }



    @Test
    @DisplayName("Fallo al añadir participante: Usuario ya en el grupo")
    void falloPorUsuarioYaEnElGrupo() {
        // Given
        final Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setId_usuario(1L);
        final Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        final Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        grupo.setIntegrantes(5);
        grupo.setUsuarios(new HashSet<>());
        final Grupo grupoCreado = grupoServicio.crearGrupo(grupo, 1L);

        // Then
        assertThrows(IllegalArgumentException.class,
                () -> grupoServicio.nuevoPartiGrup(usuarioGuardado.getId_usuario(), grupoCreado.getId_grupo()),
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
        final Grupo grupoCreado = grupoServicio.crearGrupo(grupo, usuario1.getId_usuario());

        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("Usuario 2");
        usuario2.setId_usuario(2L);
        final Usuario usuario2Creado = usuarioRepositorio.save(usuario2);

        // Añadimos los dos primeros usuarios al grupo

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
