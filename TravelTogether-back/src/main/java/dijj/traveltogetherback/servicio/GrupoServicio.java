package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.DTO.ParticipanteDTO;

import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
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
    public GrupoDTO eliminarPartiGrup(Long id_usuario, Long id_grupo){
        ArrayList<UsuarioDTO> usuariosdto = new ArrayList<>();
        Grupo grupo = grupoRepositorio.findById(id_grupo).orElseThrow(() -> new RuntimeException("No existe el grupo"));
        Usuario usuario = usuarioRepositorio.findById(id_usuario).orElseThrow(()-> new RuntimeException("No existe el grupo"));

        grupo.getUsuarios().remove(usuario);
        grupoRepositorio.save(grupo);
        for (Usuario u: grupo.getUsuarios()){
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
