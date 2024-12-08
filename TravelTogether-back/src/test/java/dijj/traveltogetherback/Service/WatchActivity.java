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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest
public class WatchActivity {


    @Autowired
    private ActividadServicio actividadServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private GrupoServicio grupoServicio;
    @Mock
    private IUsuarioRepositorio usuarioRepositorio1;

    @Mock
    private IGrupoRepositorio grupoRepositorio1;

    @Mock
    private IActividadRepositorio actividadRepositorio1;

    @InjectMocks
    private ActividadServicio actividadServicio1;


    @Transactional
    @Test
    @DisplayName("Consultar actividades con actividades asociadas")
    void testConsultarActividadesConActividadesAsociadas() {
        // Preparar datos
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Usuario 1");
        usuarioServicio.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L);
        grupo.setNombre("Grupo 1");
        grupo.setDescripcion("Descripción 1");
        grupo.setIntegrantes(5);
        grupoServicio.crearGrupo(grupo, usuario.getId_usuario());


        Actividad actividad1 = new Actividad();
        actividad1.setNombre("Actividad 1");
        actividad1.setDescripcion("Descripción 1");
        actividad1.setFecha_inicio(LocalDate.now());
        actividad1.setGrupo(grupo);

        Actividad actividad2 = new Actividad();
        actividad2.setNombre("Actividad 2");
        actividad2.setDescripcion("Descripción 2");
        actividad2.setFecha_inicio(LocalDate.now());
        actividad2.setGrupo(grupo);


        actividadServicio.crearActividad(1L, 1L, actividad1);
        actividadServicio.crearActividad(1L, 1L, actividad2);


        // Ejecutar
        List<ActividadDTO> actividades = actividadServicio.obtenerActividades(1L);

        // Verificar
        assertEquals(2, actividades.size());
        assertEquals("Actividad 1", actividades.get(0).getNombre());
        assertEquals("Actividad 2", actividades.get(1).getNombre());
    }

    @Test
    @DisplayName("Consultar actividades con grupo inexistente")
    void testConsultarActividadesConGrupoInexistente() {
        // Ejecutar y verificar que se lanza la excepción esperada
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            actividadServicio.obtenerActividades(99L);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El grupo con ID 99 no existe.", exception.getMessage());
    }
    @Transactional
    @Test
    @DisplayName("Consultar actividades con una sola actividad")
    void testConsultarActividadesConUnaSolaActividad() {
        // Preparar datos

        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Usuario único");
        usuarioServicio.crearUsuario(usuario);
        Grupo grupo = new Grupo();

        grupo.setNombre("Grupo único");
        grupo.setDescripcion("Descripción única");
        grupo.setIntegrantes(5);
        grupoServicio.crearGrupo(grupo, 1L);

        Actividad actividad = new Actividad();
        actividad.setNombre("Actividad única");
        actividad.setDescripcion("Descripción única");
        actividad.setFecha_inicio(LocalDate.now());

        actividadServicio.crearActividad(1L, grupo.getId_grupo(), actividad);
        actividad.setGrupo(grupo);

        // Ejecutar
        List<ActividadDTO> actividades = actividadServicio.obtenerActividades(grupo.getId_grupo());

        // Verificar
        assertEquals(1, actividades.size());
        assertEquals("Actividad única", actividades.get(0).getNombre());
    }
    @Transactional
    @Test
    @DisplayName("Consultar actividades sin actividades")
    void testConsultarActividadesSinActividades() {
        // Preparar datos
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario sin actividades");
        usuario = usuarioServicio.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo sin actividades");
        grupo.setDescripcion("Grupo sin actividades asociadas");
        grupo.setIntegrantes(5);
        final Grupo grupo1 = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        // Ejecutar y verificar que se lanza la excepción esperada
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            actividadServicio.obtenerActividades(grupo1.getId_grupo());
        });

        // Verificar el mensaje de la excepción
        assertEquals("El grupo con ID " + grupo.getId_grupo() + " no tiene actividades asociadas.", exception.getMessage());
    }

    @Transactional
    @Test
    @DisplayName("Consultar actividades con una sola actividad (Mockito)")
    void testConsultarActividadesConUnaSolaActividadMockito() {
        // Inicializar Mockito
        MockitoAnnotations.openMocks(this);

        // Crear datos simulados
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Juan");

        Grupo grupo = new Grupo();
        grupo.setId_grupo(1L);
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));

        Actividad actividad = new Actividad();
        actividad.setId_actividad(1L);
        actividad.setNombre("Caminata");
        actividad.setDescripcion("Caminata por la montaña");
        actividad.setFecha_inicio(LocalDate.now());
        actividad.setGrupo(grupo);

        // Configurar comportamiento de los mocks
        when(usuarioRepositorio1.findById(usuario.getId_usuario())).thenReturn(Optional.of(usuario));
        when(grupoRepositorio1.findById(grupo.getId_grupo())).thenReturn(Optional.of(grupo));
        when(actividadRepositorio1.findByGrupo(grupo)).thenReturn(List.of(actividad)); // Este método debe existir en el repositorio.

        // Ejecutar el método
        List<ActividadDTO> actividades = actividadServicio1.obtenerActividades(grupo.getId_grupo());

        // Verificar resultados
        assertNotNull("La lista de actividades no debe ser nula", actividades);
        assertEquals(1, actividades.size(), "Debe haber una actividad en la lista");
        assertEquals("Caminata", actividades.get(0).getNombre(), "El nombre de la actividad debe coincidir");

        // Verificar que los repositorios se llamaron correctamente
        verify(usuarioRepositorio1).findById(1L);
        verify(grupoRepositorio1).findById(1L);
        verify(actividadRepositorio1).findByGrupo(grupo);
    }

}
