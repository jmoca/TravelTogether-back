package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.GrupoDTO;
import dijj.traveltogetherback.DTO.ParticipanteDTO;
import dijj.traveltogetherback.DTO.UsuarioCreadorDTO;
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


    public Grupo crearGrupo(Grupo grupo, Long idUsuarioCreador) {
        // Buscar el usuario creador por su ID
        Optional<Usuario> usuarios = usuarioRepositorio.findById(idUsuarioCreador);
        grupo.getUsuarios().add(usuarios.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")));
        return  grupoRepositorio.save(grupo);
    }




    public GrupoDTO nuevoPartiGrup(Long idUsuario, Long id_grupo){

        ArrayList<UsuarioDTO> usuariosdto = new ArrayList<>();
        Grupo grupo = grupoRepositorio.findById(id_grupo).orElseThrow(() -> new RuntimeException("No existe el grupo"));
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(()-> new RuntimeException("No existe el grupo"));
        for (Usuario u: grupo.getUsuarios()){
            UsuarioDTO usedto = new UsuarioDTO();
            usedto.setId(u.getId_usuario());
            usedto.setNombre(u.getNombre());
            usuariosdto.add(usedto);
        }

        grupo.getUsuarios().add(usuario);
        grupoRepositorio.save(grupo);


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
