package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.DTO.ParticipanteDTO;

import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor

@Service
public class GrupoServicio implements IGrupoServicio {
    @Autowired
    private IGrupoRepositorio grupoRepositorio;
    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Override
    public Grupo crearGrupo(Grupo grupo, Long id_usuario) {
        Usuario usuario = usuarioRepositorio.findById(id_usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (usuario.getId_usuario() == null) {
            usuario = usuarioRepositorio.save(usuario);
        }

        grupo.getUsuarios().add(usuario);
        grupo.setIdUsuarioCreador(id_usuario);
        usuarioRepositorio.save(usuario);

        if (grupo.getIntegrantes() <= 0) {
            throw new IllegalArgumentException("El número de integrantes no puede ser 0 o negativo");
        }

        // Validación de la longitud del nombre del grupo
        String nombre = grupo.getNombre();
        if (nombre == null || nombre.length() < 4 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre del grupo debe tener entre 4 y 50 caracteres");
        }
        // Validación de la URL del campo multimedia
        String multimedia = grupo.getMultimedia();
        if (multimedia != null && !multimedia.matches("^(https?://).*")) {
            throw new IllegalArgumentException("La URL multimedia debe comenzar con http:// o https:// y ser un dominio válido");
        }

        return grupoRepositorio.save(grupo);
    }

    @Transactional
    public GrupoDTO nuevoPartiGrup(Long idUsuario, Long id_grupo) {
        ArrayList<UsuarioDTO> usuariosdto = new ArrayList<>();

        Grupo grupo = grupoRepositorio.findById(id_grupo)
                .orElseThrow(() -> new RuntimeException("No existe el grupo"));

        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe el usuario"));

        // Verificar si el usuario ya es parte del grupo
        if (grupo.getUsuarios().contains(usuario)) {
            throw new IllegalArgumentException("El usuario ya está en el grupo");
        }

        // Verificar si el grupo ha alcanzado el límite de integrantes
        if (grupo.getUsuarios().size() >= grupo.getIntegrantes()) {
            throw new IllegalStateException("El grupo ha alcanzado su límite de integrantes");
        }

        grupo.getUsuarios().add(usuario);
        grupoRepositorio.save(grupo);
        usuarioRepositorio.save(usuario);

        for (Usuario u : grupo.getUsuarios()) {
            UsuarioDTO usedto = new UsuarioDTO();
            usedto.setId(u.getId_usuario());
            usedto.setNombre(u.getNombre());
            usuariosdto.add(usedto);
        }

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setId_grupo(grupo.getId_grupo());
        grupoDTO.setNombre(grupo.getNombre());
        grupoDTO.setDescripcion(grupo.getDescripcion());
        grupoDTO.setIntegrantes(grupo.getIntegrantes());
        grupoDTO.setFechaCreacion(grupo.getFechaCreacion());
        grupoDTO.setIdUsuarioCreador(grupo.getIdUsuarioCreador()); // Mantener el idUsuarioCreador
        grupoDTO.setUsuarios(usuariosdto);

        return grupoDTO;
    }

    public ParticipanteDTO verPartiGrupo(Long id_grupo){

        Grupo grupo = grupoRepositorio.findById(id_grupo).orElseThrow(() -> new RuntimeException("No existe el grupo"));
        ArrayList<UsuarioDTO> usuariosdto = new ArrayList<>();
        for (Usuario u: grupo.getUsuarios()){
            UsuarioDTO usedto = new UsuarioDTO();
            usedto.setId(u.getId_usuario());
            usedto.setNombre(u.getNombre());
            usuariosdto.add(usedto);
        }
        ParticipanteDTO participanteDTO = new ParticipanteDTO();
        participanteDTO.setId_grupo(grupo.getId_grupo());
        participanteDTO.setNombre(grupo.getNombre());
        participanteDTO.setDescripcion(grupo.getDescripcion());
        participanteDTO.setIntegrantes(grupo.getIntegrantes());

        participanteDTO.setFechaCreacion(grupo.getFechaCreacion());
        participanteDTO.setUsuarios(usuariosdto);

        return participanteDTO;
    }
    public GrupoDTO eliminarPartiGrup(Long id_usuario, Long id_grupo) {
        // Validar que los IDs no sean nulos
        if (id_usuario == null || id_grupo == null) {
            throw new IllegalArgumentException("El ID del usuario o del grupo no puede ser nulo");
        }

        // Buscar el grupo y lanzar excepción si no existe
        Grupo grupo = grupoRepositorio.findById(id_grupo)
                .orElseThrow(() -> new RuntimeException("No existe el grupo con el ID: " + id_grupo));

        // Buscar el usuario y lanzar excepción si no existe
        Usuario usuario = usuarioRepositorio.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con el ID: " + id_usuario));

        // Validar si el grupo tiene usuarios asociados
        if (grupo.getUsuarios() == null || grupo.getUsuarios().isEmpty()) {
            throw new RuntimeException("El grupo no tiene usuarios asignados");
        }

        // Validar si el usuario pertenece al grupo
        if (!grupo.getUsuarios().contains(usuario)) {
            throw new RuntimeException("El usuario con ID: " + id_usuario + " no pertenece al grupo con ID: " + id_grupo);
        }

        // Remover al usuario del grupo
        grupo.getUsuarios().remove(usuario);

        // Guardar los cambios en el repositorio
        grupoRepositorio.save(grupo);

        // Crear el DTO de los usuarios restantes
        ArrayList<UsuarioDTO> usuariosdto = new ArrayList<>();
        for (Usuario u : grupo.getUsuarios()) {
            UsuarioDTO usedto = new UsuarioDTO();
            usedto.setId(u.getId_usuario());
            usedto.setNombre(u.getNombre());
            usuariosdto.add(usedto);
        }

        // Crear el DTO del grupo actualizado
        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setId_grupo(grupo.getId_grupo());
        grupoDTO.setNombre(grupo.getNombre());
        grupoDTO.setDescripcion(grupo.getDescripcion());
        grupoDTO.setIntegrantes(grupo.getIntegrantes());
        grupoDTO.setFechaCreacion(grupo.getFechaCreacion());
        grupoDTO.setUsuarios(usuariosdto);

        return grupoDTO;
    }

    public List<GrupoDTO> obtenerGruposPorUsuario(Long id_usuario){
        List<Grupo> grupos = grupoRepositorio.findGruposByUsuarioId(id_usuario);
        List<GrupoDTO> grupoDTOs = new ArrayList<>();
        for (Grupo grupo : grupos) {
            GrupoDTO grupoDTO = new GrupoDTO();
            grupoDTO.setId_grupo(grupo.getId_grupo());
            grupoDTO.setNombre(grupo.getNombre());
            grupoDTO.setDescripcion(grupo.getDescripcion());
            grupoDTO.setIntegrantes(grupo.getIntegrantes());

            grupoDTO.setFechaCreacion(grupo.getFechaCreacion());
            grupoDTOs.add(grupoDTO);
        }
        return grupoDTOs;
    }
}
