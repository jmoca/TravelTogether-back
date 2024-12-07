package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.modelo.Amigos;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IAmigoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class ShowFriendsTest {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IAmigoRepositorio amigosRepositorio;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio1;

    @Mock
    private IAmigoRepositorio amigosRepositorio1;

    @InjectMocks
    private UsuarioServicio usuarioServicio1;

    @Test
    @Transactional
    @DisplayName("Prueba positiva: Listar amigos para un usuario con amigos existentes")
    void listarAmigosUsuarioConAmigos() {
        // Crear usuarios
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan");
        usuario1.setAmigos1(new ArrayList<>());
        usuario1.setAmigos2(new ArrayList<>());
        usuarioRepositorio.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("María");
        usuario2.setAmigos1(new ArrayList<>());
        usuario2.setAmigos2(new ArrayList<>());
        usuarioRepositorio.save(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Pedro");
        usuario3.setAmigos1(new ArrayList<>());
        usuario3.setAmigos2(new ArrayList<>());
        usuarioRepositorio.save(usuario3);

        Usuario usuario4 = new Usuario();
        usuario4.setNombre("Ana");
        usuario4.setAmigos1(new ArrayList<>());
        usuario4.setAmigos2(new ArrayList<>());
        usuarioRepositorio.save(usuario4);

        // Crear relación de amistad
        Amigos amistad1 = new Amigos();
        amistad1.setUsuario1(usuario1);
        amistad1.setUsuario2(usuario2);
        amistad1.setFecha_amistad(LocalDate.now());
        amigosRepositorio.save(amistad1);

        Amigos amistad2 = new Amigos();
        amistad2.setUsuario1(usuario1);
        amistad2.setUsuario2(usuario3);
        amistad2.setFecha_amistad(LocalDate.now());
        amigosRepositorio.save(amistad2);

        Amigos amistad3 = new Amigos();
        amistad3.setUsuario1(usuario1);
        amistad3.setUsuario2(usuario4);
        amistad3.setFecha_amistad(LocalDate.now());
        amigosRepositorio.save(amistad3);

        // Actualizar las listas de amigos en los usuarios
        usuario1.getAmigos1().add(amistad1);
        usuario1.getAmigos1().add(amistad2);
        usuario1.getAmigos1().add(amistad3);
        usuario2.getAmigos2().add(amistad1);
        usuario3.getAmigos2().add(amistad2);
        usuario4.getAmigos2().add(amistad3);

        usuarioRepositorio.save(usuario1);
        usuarioRepositorio.save(usuario2);
        usuarioRepositorio.save(usuario3);
        usuarioRepositorio.save(usuario4);

        // Verificar amigos de usuario1
        Set<Usuario> amigos = usuarioServicio.obtenerAmigos(usuario1.getId_usuario());
        assertNotNull(amigos.toString(), "La lista de amigos no debe ser nula");
        assertEquals("Debe haber tres amigos en la lista", 3, amigos.size());
    }


    @Test
    @DisplayName("Prueba negativa: Introducir un ID de usuario no existente")
    void listarAmigosUsuarioNoExistente() {
        // Dado que el ID 99 no existe, simulamos que el repositorio retorna Optional.empty()
       usuarioRepositorio.findById(99L);
        // When / Then
        assertThrows(IllegalArgumentException.class,
                () -> usuarioServicio.obtenerAmigos(99L),
                "Se esperaba una excepción por usuario no encontrado"
        );
    }

    @Transactional
    @Test
    @DisplayName("Prueba con límites: Listar amigos para un usuario con exactamente un amigo")
    void listarAmigosConUnSoloAmigo() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan");
        usuario1.setAmigos1(new ArrayList<>());
        usuario1.setAmigos2(new ArrayList<>());
        usuarioRepositorio.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("María");
        usuario2.setAmigos1(new ArrayList<>());
        usuario2.setAmigos2(new ArrayList<>());
        usuarioRepositorio.save(usuario2);

        // Ensure the IDs are set after saving
        usuario1 = usuarioRepositorio.findById(usuario1.getId_usuario()).orElseThrow();
        usuario2 = usuarioRepositorio.findById(usuario2.getId_usuario()).orElseThrow();

        LocalDate fechaAmistad = LocalDate.now();
        // Crear la relación de amistad entre Juan y María
        Amigos amistad1 = new Amigos();
        amistad1.setUsuario1(usuario1);
        amistad1.setUsuario2(usuario2);
        amistad1.setFecha_amistad(fechaAmistad);
        amigosRepositorio.save(amistad1);

        // Añadir la relación de amistad a los usuarios
        usuario1.getAmigos1().add(amistad1);
        usuario2.getAmigos2().add(amistad1);

        usuarioRepositorio.save(usuario1);
        usuarioRepositorio.save(usuario2);

        // When
        final Long usuario2Id = usuario2.getId_usuario();
        Set<UsuarioDTO> amigosDTO = usuarioServicio.obtenerAmigosDTO(usuario1.getId_usuario());

        // Then
        assertNotNull(amigosDTO.toString(), "La lista de amigos no debe ser nula");
        assertEquals("El usuario debe tener exactamente un amigo", 1, amigosDTO.size());
        assertTrue("El amigo María debe estar en la lista", amigosDTO.stream().anyMatch(dto -> dto.getId_usuario() == usuario2Id));
    }


    @Transactional
    @Test
    @DisplayName("Prueba con límites: Listar amigos para un usuario con exactamente un amigo")
    void listarAmigosConUnSoloAmigoMock() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setId_usuario(1L);
        usuario1.setNombre("Juan");
        usuario1.setAmigos1(new ArrayList<>());
        usuario1.setAmigos2(new ArrayList<>());

        Usuario usuario2 = new Usuario();
        usuario2.setId_usuario(2L);
        usuario2.setNombre("María");
        usuario2.setAmigos1(new ArrayList<>());
        usuario2.setAmigos2(new ArrayList<>());

        LocalDate fechaAmistad = LocalDate.now();
        Amigos amistad1 = new Amigos(1L, usuario1, usuario2, fechaAmistad);

        // Simulamos que usuarioRepositorio.findById devuelve usuario1
        Mockito.when(usuarioRepositorio1.findById(1L)).thenReturn(Optional.of(usuario1));

        // Añadir la relación de amistad a los usuarios
        usuario1.getAmigos1().add(amistad1);
        usuario2.getAmigos2().add(amistad1);

        // Simulamos que amigosRepositorio encuentra la relación de amistad para usuario1
        Mockito.when(amigosRepositorio1.findByUsuario1OrUsuario2(usuario1, usuario1))
                .thenReturn(Collections.singletonList(amistad1));

        // When
        Set<UsuarioDTO> amigosDTO = usuarioServicio1.obtenerAmigosDTO(1L);

        // Then
        assertNotNull(amigosDTO.toString(), "La lista de amigos no debe ser nula");
        assertEquals("El usuario debe tener exactamente un amigo", 1, amigosDTO.size());
        assertTrue("El amigo María debe estar en la lista",
                amigosDTO.stream().anyMatch(dto -> dto.getId_usuario() == 2L));

        // Verificar interacciones con los repositorios
        Mockito.verify(usuarioRepositorio1).findById(1L);
        Mockito.verify(amigosRepositorio1).findByUsuario1OrUsuario2(usuario1, usuario1);
    }


}
