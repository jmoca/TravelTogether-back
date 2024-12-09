package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class RemovePartiTests {

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

    @Mock
    private IUsuarioRepositorio usuarioRepositorio1;

    @Test
    @DisplayName("Prueba positiva: Eliminar un participante existente de un viaje válido con Mockito")
    void eliminarParticipanteExistenteMockito() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setId_usuario(1L);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setId_usuario(2L);
        usuario2.setNombre("María");

        // Simulamos que los usuarios están guardados en el repositorio
        when(usuarioRepositorio1.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepositorio1.findById(2L)).thenReturn(Optional.of(usuario2));

        Grupo grupo = new Grupo();
        grupo.setIdUsuarioCreador(1L);
        grupo.setId_grupo(1L);
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));

        grupo.setUsuarios(new HashSet<>(Set.of(usuario1, usuario2)));

        // Simulamos el comportamiento de los repositorios de grupo
        when(grupoRepositorio1.findById(1L)).thenReturn(Optional.of(grupo));
        when(grupoRepositorio1.save(grupo)).thenReturn(grupo);

        // When
        GrupoDTO resultado = grupoServicio1.eliminarPartiGrup(1L, 1L); // Eliminar el usuario con ID 1

        // Then
        assertNotNull(resultado, "El DTO del grupo actualizado no debe ser nulo");
        assertTrue(grupo.getUsuarios().size() == 1, "El grupo debería tener un participante menos");

        // Verificamos que se ha llamado al repositorio
        verify(grupoRepositorio1, times(1)).save(grupo);
        verify(grupoRepositorio1, times(1)).findById(1L);
        verify(usuarioRepositorio1, times(1)).findById(1L);  // Verificamos que se haya buscado al usuario
    }

    @Transactional
    @Test
    @DisplayName("Prueba positiva: Eliminar un participante existente de un viaje válido")
    void eliminarParticipanteExistente() {
        // Given
        Grupo grupo = new Grupo();
        grupo.setIdUsuarioCreador(1L);
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

        // Guardamos los usuarios en el repositorio de usuarios
        usuarioRepositorio.save(usuario1);
        usuarioRepositorio.save(usuario2);

        // Añadimos los usuarios al grupo
        grupo.setUsuarios(new HashSet<>(Set.of(usuario1, usuario2)));

        // Guardamos el grupo en el repositorio
        grupoRepositorio.save(grupo);

        // When
        GrupoDTO resultado = grupoServicio.eliminarPartiGrup(1L, 1L); // Eliminar el usuario con ID 1

        // Then
        assertNotNull(resultado, "El DTO del grupo actualizado no debe ser nulo");
        Optional<Grupo> grupoActualizado = grupoRepositorio.findById(1L);
        assertTrue(grupoActualizado.isPresent(), "El grupo debería existir en la base de datos");
        assertEquals(1, grupoActualizado.get().getUsuarios().size(), "El grupo debería tener un participante menos");
    }

    @Transactional
    @Test
    @DisplayName("Prueba negativa: Intentar eliminar un participante de un grupo inexistente")
    void eliminarParticipanteIdInexistente() {
        // Given: Se crea un grupo que se guardará en la base de datos
        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L); // Asignar un ID existente en la base de datos
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupo.setUsuarios(new HashSet<>());

        // Guardar el grupo en el repositorio
        grupoRepositorio.save(grupo);

        // When: Intentamos eliminar un participante cuyo ID no existe (en este caso 99)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            grupoServicio.eliminarPartiGrup(1L, 99L); // ID de participante inexistente
        });

        // Then: Verificamos que se lanzó la excepción correcta
        assertEquals("No existe el grupo con el ID: 99", exception.getMessage());

        // Verificar que el grupo no ha sido modificado y sigue teniendo los mismos participantes
        Optional<Grupo> grupoActualizado = grupoRepositorio.findById(grupo.getId_grupo());
        assertTrue(grupoActualizado.isPresent(), "El grupo debería existir");
        assertEquals(0, grupoActualizado.get().getUsuarios().size(), "El grupo no debería tener participantes eliminados");
    }

    @Transactional
    @Test
    @DisplayName("Prueba negativa: Intentar eliminar un participante de un grupo inexistente")
    void eliminarParticipanteViajeInexistente() {
        // Given: Se crea un usuario
        Usuario usuario = new Usuario();
        usuario.setId_usuario(4L);
        usuario.setNombre("Juan");
        usuarioRepositorio.save(usuario);

        // Se crea un grupo con ID 1, el cual está presente en la base de datos
        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L); // Este grupo tiene un ID válido
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupo.setUsuarios(new HashSet<>());

        // Guardamos el grupo en el repositorio
        grupoRepositorio.save(grupo);

        // When: Intentamos eliminar un participante de un grupo que no existe (ID 8)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            grupoServicio.eliminarPartiGrup(4L, 5L); // ID de grupo inexistente
        });

        // Then: Verificamos que se lanza la excepción esperada con el mensaje correcto
        assertEquals("No existe el grupo con el ID: 5", exception.getMessage());

        // Verificamos que el grupo con ID 1 no ha sido afectado
        Optional<Grupo> grupoActualizado = grupoRepositorio.findById(grupo.getId_grupo());
        assertTrue(grupoActualizado.isPresent(), "El grupo con ID 1 debe existir");
        assertEquals(0, grupoActualizado.get().getUsuarios().size(), "El grupo no debe tener participantes eliminados");
    }
    @Transactional
    @Test
    @DisplayName("Prueba con límites: Eliminar todos los participantes de un viaje")
    void eliminarTodosLosParticipantes() {
        // Given: Creamos los usuarios y los guardamos en la base de datos
        Usuario usuario1 = new Usuario();
        usuario1.setId_usuario(1L);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setId_usuario(2L);
        usuario2.setNombre("María");

        // Guardamos los usuarios antes de guardar el grupo
        usuarioRepositorio.save(usuario1);
        usuarioRepositorio.save(usuario2);

        // Creamos el grupo y lo asociamos con los usuarios
        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L);  // Aseguramos que este ID sea consistente con el de la prueba
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));
        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        grupo.setUsuarios(usuarios);

        // Guardamos el grupo en la base de datos
        grupoRepositorio.save(grupo);

        // When: Intentamos eliminar los participantes del grupo
        grupoServicio.eliminarPartiGrup(1L, 1L);  // Eliminar primer participante
        grupoServicio.eliminarPartiGrup(2L, 1L);  // Eliminar segundo participante

        // Then: Verificamos que el grupo ha quedado vacío de usuarios
        Optional<Grupo> grupoActualizado = grupoRepositorio.findById(1L);
        assertTrue(grupoActualizado.isPresent(), "El grupo con ID 1 debe existir");
        assertTrue(grupoActualizado.get().getUsuarios().isEmpty(), "El grupo debería quedarse sin participantes");
    }


}
