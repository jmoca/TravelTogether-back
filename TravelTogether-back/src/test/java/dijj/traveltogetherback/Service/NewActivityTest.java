package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.ActividadDTO;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IActividadRepositorio;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.servicio.ActividadServicio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;


import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class NewActivityTest {

    @Autowired
    private GrupoServicio grupoServicio;

    @Autowired
    private ActividadServicio actividadServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio1;

    @Mock
    private IGrupoRepositorio grupoRepositorio1;

    @Mock
    private IActividadRepositorio actividadRepositorio1;

    @InjectMocks
    private ActividadServicio actividadServicio1;

    @Test
    @DisplayName("Proponer una actividad válida para un viaje existente")
    void proponerActividadValidaMockito() {
        // Crear datos simulados
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Juan");

        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L);
        grupo.setNombre("Viaje a la montaña");
        grupo.getUsuarios().add(usuario);

        Actividad actividad = new Actividad();
        actividad.setNombre("Caminata");
        actividad.setDescripcion("Caminata al aire libre");
        actividad.setFecha_fin(LocalDate.now().plusDays(1));
        actividad.setLugar("Montaña");

        // Simular repositorios
        when(usuarioRepositorio1.findById(usuario.getId_usuario())).thenReturn(Optional.of(usuario));
        when(grupoRepositorio1.findById(grupo.getId_grupo())).thenReturn(Optional.of(grupo));
        when(actividadRepositorio1.save(any(Actividad.class))).thenReturn(actividad);

        // Ejecutar el método
        ActividadDTO actividadDTO = actividadServicio1.crearActividad(usuario.getId_usuario(), grupo.getId_grupo(), actividad);

        // Verificar resultados
        assertNotNull("La actividad no debe ser nula", actividadDTO);
        assertEquals("El nombre de la actividad debe coincidir", actividad.getNombre(), actividadDTO.getNombre());
        verify(actividadRepositorio1, times(1)).save(actividad);
    }
    @Transactional
    @Test
    @DisplayName("Proponer una actividad válida para un viaje existente")
    void proponerActividadValida() {
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos");
        usuario = usuarioServicio.crearUsuario(usuario);

        // Crear grupo/viaje
        Grupo grupo = new Grupo();
        grupo.setNombre("Excursión a las montañas");
        grupo.setDescripcion("Viaje de senderismo");
        grupo.setIntegrantes(10);
        grupo.setFechaCreacion(LocalDate.now().toString());
        grupo = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Crear actividad
        Actividad actividad = new Actividad();
        actividad.setNombre("Caminata en el bosque");
        actividad.setDescripcion("Actividad para explorar la fauna local");
        actividad.setFecha_inicio(LocalDate.now()); // Fecha válida
        actividad.setLugar("Bosque Nacional");

        ActividadDTO actividadCreada = actividadServicio.crearActividad(usuario.getId_usuario(), grupo.getId_grupo(), actividad);

        // Validar que la actividad fue creada
        assertNotNull("La actividad no debe ser nula", actividadCreada);
        assertEquals("El nombre de la actividad debe coincidir", "Caminata en el bosque", actividadCreada.getNombre());
        assertEquals("La descripción de la actividad debe coincidir", "Actividad para explorar la fauna local", actividadCreada.getDescripcion());
        assertEquals("La fecha de la actividad debe coincidir", LocalDate.now().plusDays(1), actividadCreada.getFecha_inicio());
    }
    @Transactional
    @Test
    @DisplayName("Intentar crear una actividad con un usuario no participante en el grupo")
    void actividadUsuarioNoParticipante() {
        // Crear dos usuarios
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("Carlos");
        final Usuario usuario1save = usuarioServicio.crearUsuario(usuario1);

        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("María");
        final Usuario usuario2save = usuarioServicio.crearUsuario(usuario2);

        // Crear grupo/viaje asociado al usuario1
        final Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la playa");
        grupo.setDescripcion("Un día soleado");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(LocalDate.now().toString());
        final Grupo gruposave = grupoServicio.crearGrupo(grupo, usuario1.getId_usuario());

        // Crear actividad con usuario2 (no participante)
        final Actividad actividad = new Actividad();
        actividad.setNombre("Actividades en la arena");
        actividad.setDescripcion("Construir castillos de arena");
        actividad.setFecha_fin(LocalDate.now().plusDays(1));
        actividad.setLugar("Playa");

        // Intentar crear actividad
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actividadServicio.crearActividad(usuario2save.getId_usuario(), gruposave.getId_grupo(), actividad);
        });

        // Verificar el mensaje de error
        Assertions.assertEquals("El usuario no pertenece al grupo especificado", exception.getMessage());
    }
    @Transactional
    @Test
    @DisplayName("Proponer una actividad para una fecha que ya ha pasado")
    void actividadFechaPasada() {
        // Crear usuario
        final Usuario usuario = new Usuario();
        usuario.setNombre("Carlos");
        final Usuario usuariosave = usuarioServicio.crearUsuario(usuario);

        // Crear grupo/viaje
        final Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Explorar senderos");
        grupo.setIntegrantes(8);
        grupo.setFechaCreacion(LocalDate.now().toString());
        final Grupo gruposave = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Crear actividad con una fecha pasada
        final Actividad actividad = new Actividad();
        actividad.setNombre("Caminata nocturna");
        actividad.setDescripcion("Explorar la montaña de noche");
        actividad.setFecha_fin(LocalDate.now().minusDays(1)); // Fecha pasada
        actividad.setLugar("Montaña");

        // Intentar crear actividad
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actividadServicio.crearActividad(usuariosave.getId_usuario(), gruposave.getId_grupo(), actividad);
        });

        // Verificar el mensaje de error
        Assertions.assertEquals("La fecha de la actividad no puede ser una fecha pasada", exception.getMessage());
    }
    @Transactional
    @Test
    @DisplayName("Crear actividad para un grupo con nombre de 4 a 50 caracteres")
    void actividadConNombreGrupoLimite() {
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos");
        usuario = usuarioServicio.crearUsuario(usuario);

        // Crear grupo/viaje con nombre límite (exactamente 4 caracteres)
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo");
        grupo.setDescripcion("Exploración límite de caracteres");
        grupo.setIntegrantes(6);
        grupo.setFechaCreacion(LocalDate.now().toString());
        grupo = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Crear actividad
        Actividad actividad = new Actividad();
        actividad.setNombre("Senderismo");
        actividad.setDescripcion("Caminata en senderos");
        actividad.setFecha_fin(LocalDate.now().plusDays(1)); // Fecha válida
        actividad.setLugar("Montaña");

        ActividadDTO actividadCreada = actividadServicio.crearActividad(usuario.getId_usuario(), grupo.getId_grupo(), actividad);

        // Validar que la actividad fue creada
        assertNotNull("La actividad no debe ser nula", actividadCreada);
        assertEquals("El nombre de la actividad debe coincidir", "Senderismo", actividadCreada.getNombre());
        assertEquals("La descripción de la actividad debe coincidir", "Caminata en senderos", actividadCreada.getDescripcion());
    }
}