package dijj.traveltogetherback.Service;


import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.modelo.Amigos;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class ShowFriends {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("Prueba positiva: Listar amigos para un usuario con amigos existentes")
    void listarAmigosUsuarioConCuatroAmigos() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setId_usuario(1L);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setId_usuario(2L);
        usuario2.setNombre("María");

        Usuario usuario3 = new Usuario();
        usuario3.setId_usuario(3L);
        usuario3.setNombre("Carlos");

        Usuario usuario4 = new Usuario();
        usuario4.setId_usuario(4L);
        usuario4.setNombre("Laura");

        Usuario usuario5 = new Usuario();
        usuario5.setId_usuario(5L);
        usuario5.setNombre("Pedro");

        LocalDate fechaAmistad = LocalDate.now();

        // Crear las relaciones de amistad entre Juan y los demás usuarios
        Amigos amistad1 = new Amigos(1L, usuario1, usuario2, fechaAmistad); // Juan y María
        Amigos amistad2 = new Amigos(2L, usuario1, usuario3, fechaAmistad); // Juan y Carlos
        Amigos amistad3 = new Amigos(3L, usuario1, usuario4, fechaAmistad); // Juan y Laura
        Amigos amistad4 = new Amigos(4L, usuario1, usuario5, fechaAmistad); // Juan y Pedro

        // Añadir las relaciones de amistad a los usuarios
        usuario1.setAmigos1(List.of(amistad1, amistad2, amistad3, amistad4));
        usuario2.setAmigos2(List.of(amistad1));
        usuario3.setAmigos2(List.of(amistad2));
        usuario4.setAmigos2(List.of(amistad3));
        usuario5.setAmigos2(List.of(amistad4));

        // Simulamos que el usuario existe en el repositorio
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario1));

        // When
        Set<UsuarioDTO> amigosDTO = usuarioServicio.obtenerAmigosDTO(1L);

        // Then
        assertNotNull(amigosDTO.toString(), "La lista de amigos no debe ser nula");
        assertEquals("El usuario debe tener exactamente 4 amigos", 4, amigosDTO.size());
        assertTrue("El amigo María debe estar en la lista", amigosDTO.stream().anyMatch(dto -> dto.getId_usuario() == 2L));
        assertTrue("El amigo Carlos debe estar en la lista", amigosDTO.stream().anyMatch(dto -> dto.getId_usuario() == 3L));
        assertTrue("El amigo Laura debe estar en la lista", amigosDTO.stream().anyMatch(dto -> dto.getId_usuario() == 4L));
        assertTrue("El amigo Pedro debe estar en la lista", amigosDTO.stream().anyMatch(dto -> dto.getId_usuario() == 5L));
    }


    @Test
    @DisplayName("Prueba negativa: Introducir un ID de usuario no existente")
    void listarAmigosUsuarioNoExistente() {
        // Dado que el ID 99 no existe, simulamos que el repositorio retorna Optional.empty()
        when(usuarioRepositorio.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(IllegalArgumentException.class,
                () -> usuarioServicio.obtenerAmigos(99L),
                "Se esperaba una excepción por usuario no encontrado"
        );
    }


    @Test
    @DisplayName("Prueba con límites: Listar amigos para un usuario con exactamente un amigo")
    void listarAmigosConUnSoloAmigo() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setId_usuario(1L);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setId_usuario(2L);
        usuario2.setNombre("María");

        LocalDate fechaAmistad = LocalDate.now();
        // Crear la relación de amistad entre Juan y María
        Amigos amistad1 = new Amigos(1L, usuario1, usuario2, fechaAmistad); // Relación de amistad de usuario1 con usuario2
        Amigos amistad2 = new Amigos(2L, usuario2, usuario1, fechaAmistad); // Relación de amistad de usuario2 con usuario1

        // Añadir la relación de amistad a los usuarios
        usuario1.setAmigos1(List.of(amistad1));  // Lista de amigos de usuario1
        usuario2.setAmigos2(List.of(amistad2));  // Lista de amigos de usuario2

        // Simulamos que el usuario existe en el repositorio
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario1));

        // When
        Set<UsuarioDTO> amigosDTO = usuarioServicio.obtenerAmigosDTO(1L);

        // Then
        assertNotNull(amigosDTO.toString(), "La lista de amigos no debe ser nula");
        assertEquals("El usuario debe tener exactamente un amigo", 1, amigosDTO.size());
        assertTrue("El amigo María debe estar en la lista", amigosDTO.stream().anyMatch(dto -> dto.getId_usuario() == 2L));
    }




}
