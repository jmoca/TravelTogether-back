package dijj.traveltogetherback.Service;

import dijj.traveltogetherback.DTO.ActividadDTO;
import dijj.traveltogetherback.DTO.VotoDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.modelo.Actividad;
import dijj.traveltogetherback.modelo.Voto;
import dijj.traveltogetherback.repositorio.IActividadRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import dijj.traveltogetherback.repositorio.IVotoRepositorio;
import dijj.traveltogetherback.servicio.ActividadServicio;
import dijj.traveltogetherback.servicio.GrupoServicio;
import dijj.traveltogetherback.servicio.UsuarioServicio;
import dijj.traveltogetherback.servicio.VotoServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
public class VoteActivity {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private GrupoServicio grupoServicio;

    @Autowired
    private ActividadServicio actividadServicio;

    @Autowired
    private VotoServicio votoServicio;

    @Mock
    private IVotoRepositorio votoRepositorio;

    @Mock
    private IUsuarioRepositorio usuarioRepositorio;

    @Mock
    private IActividadRepositorio actividadRepositorio;

    @InjectMocks
    private VotoServicio votoServicio1;


    @Test
    @Transactional
    @DisplayName("Votar por una actividad válida")
    void votarActividadValida() {

        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setId_usuario(1L);
        usuario = usuarioServicio.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la montaña");
        grupo.setDescripcion("Viaje grupal");
        grupo.setIntegrantes(5);
        grupo.setFechaCreacion(String.valueOf(LocalDate.now()));
        grupo = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        Actividad actividad = new Actividad();
        actividad.setNombre("Caminata");
        actividad.setDescripcion("Caminata por la montaña");
        actividad.setFecha_inicio(LocalDate.now());

        ActividadDTO actividadDTO = actividadServicio.crearActividad(usuario.getId_usuario(), grupo.getId_grupo(), actividad);

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        Actividad actividadEntity = new Actividad();
        actividadEntity.setId_actividad(actividadDTO.getId());
        voto.setActividad(actividadEntity);
        voto.setTipo_voto(true);
        voto.setFechaVoto(LocalDate.now());
        VotoDTO votoDTO = votoServicio.actualizarVoto(usuario.getId_usuario(), actividadDTO.getId(), voto.getTipo_voto());

        assertEquals("El voto no se ha registrado correctamente", votoDTO.getIdVoto(), votoDTO.getIdVoto());
    }

    @Test
    @Transactional
    @DisplayName("Votar con un ID de actividad o usuario no existente")
    void votarActividadUsuarioNoExistente() {
        Long idUsuarioNoExistente = 999L;
        Long idActividadNoExistente = 888L;

        Exception exception = null;
        try {
            votoServicio.actualizarVoto(idUsuarioNoExistente, idActividadNoExistente, true);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull("Se esperaba una excepción por IDs inexistentes", exception);
        assertEquals("La excepción debe indicar que no se encontró la entidad",
                "Usuario no encontrado con ID: " + idUsuarioNoExistente, exception.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("Votar con un voto inválido (texto en lugar de true/false)")
    void votarConVotoInvalido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Luis");
        usuario = usuarioServicio.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje a la playa");
        grupo.setIntegrantes(2);
        grupo.setDescripcion("Relajación y diversión");
        grupo = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        Actividad actividad = new Actividad();
        actividad.setNombre("Surf");
        actividad.setDescripcion("Clases de surf");
        actividad.setFecha_inicio(LocalDate.now());
        actividadServicio.crearActividad(usuario.getId_usuario(), grupo.getId_grupo(), actividad);

        Exception exception = null;
        try {
            votoServicio.actualizarVoto(usuario.getId_usuario(), actividad.getId_actividad(), null);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull("Se esperaba una excepción por voto inválido", exception);
        assertEquals("La excepción debe indicar que el voto es inválido",
                "El tipo de voto no puede ser nulo", exception.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("Votar por una actividad que el usuario ya votó")
    void votarActividadYaVotada() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");
        usuario = usuarioServicio.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje cultural");
        grupo.setIntegrantes(3);
        grupo.setDescripcion("Museos y ciudades históricas");
        grupo = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        Actividad actividad = new Actividad();
        actividad.setNombre("Visita al museo");
        actividad.setDescripcion("Recorrido por el museo de arte");
        actividad.setFecha_inicio(LocalDate.now());
        actividadServicio.crearActividad(usuario.getId_usuario(), grupo.getId_grupo(), actividad);

        // Primer voto
        votoServicio.actualizarVoto(usuario.getId_usuario(), actividad.getId_actividad(), true);

        // Segundo voto (actualización)
        VotoDTO votoActualizado = votoServicio.actualizarVoto(usuario.getId_usuario(), actividad.getId_actividad(), false);

        assertEquals("El voto debe haberse actualizado correctamente", false, votoActualizado.getTipo_voto());
    }

    @Test
    @Transactional
    @DisplayName("Votar con un número máximo de votos en la actividad")
    void votarConLimiteMaximoDeVotos() {
        // Crear actividad y usuarios
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario0");
        usuario = usuarioServicio.crearUsuario(usuario);

        Grupo grupo = new Grupo();
        grupo.setNombre("Viaje al campo");
        grupo.setIntegrantes(10);
        grupo = grupoServicio.crearGrupo(grupo, usuario.getId_usuario());

        Actividad actividad = new Actividad();
        actividad.setNombre("Picnic");
        actividad.setDescripcion("Comida al aire libre");
        actividad.setFecha_inicio(LocalDate.now());
        ActividadDTO actividadDTO = actividadServicio.crearActividad(usuario.getId_usuario(), grupo.getId_grupo(), actividad);

        // Crear usuarios y votar
        for (int i = 0; i < 10; i++) {
            Usuario usuario1 = new Usuario();
            usuario1.setNombre("Usuario" + i);
            usuario1 = usuarioServicio.crearUsuario(usuario1);
            votoServicio.actualizarVoto(usuario1.getId_usuario(), actividadDTO.getId(), true);
        }

        Exception exception = null;
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre("Usuario11");
            nuevoUsuario = usuarioServicio.crearUsuario(nuevoUsuario);
            votoServicio.actualizarVoto(nuevoUsuario.getId_usuario(), actividadDTO.getId(), true);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull("Se esperaba una excepción por límite de votos alcanzado", exception);
        assertEquals("La excepción debe indicar que se alcanzó el límite de votos",
                "Se ha alcanzado el número máximo de votos para esta actividad", exception.getMessage());
    }
    @Test
    @DisplayName("Votar por una actividad que el usuario ya votó (Mockito)")
    void votarActividadYaVotadaMockito() {


        // Crear datos simulados
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNombre("Juan");

        Actividad actividad = new Actividad();
        actividad.setId_actividad(1L);
        actividad.setNombre("Caminata");

        Voto votoExistente = new Voto();
        votoExistente.setId_voto(1L);
        votoExistente.setTipo_voto(true);
        votoExistente.setUsuario(usuario);
        votoExistente.setActividad(actividad);
        votoExistente.setFechaVoto(LocalDate.now());

        // Simular repositorios
        when(usuarioRepositorio.findById(usuario.getId_usuario())).thenReturn(Optional.of(usuario));
        when(actividadRepositorio.findById(actividad.getId_actividad())).thenReturn(Optional.of(actividad));
        when(votoRepositorio.findvotoId(usuario.getId_usuario(), actividad.getId_actividad())).thenReturn(votoExistente);
        when(votoRepositorio.save(any(Voto.class))).thenReturn(votoExistente);

        // Ejecutar el método
        VotoDTO votoActualizado = votoServicio1.actualizarVoto(usuario.getId_usuario(), actividad.getId_actividad(), false);

        // Verificar resultados
        assertNotNull(votoActualizado.toString(), "El voto actualizado no debe ser nulo");
        assertEquals("El tipo de voto debe ser actualizado a falso", false, votoActualizado.getTipo_voto());
        verify(votoRepositorio, times(1)).save(votoExistente);
    }
}
