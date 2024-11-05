package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.DTO.PartiDTO;
import dijj.traveltogetherback.DTO.UsuarioDTO;
import dijj.traveltogetherback.modelo.Grupo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IGrupoRepositorio;
import dijj.traveltogetherback.repositorio.IUsuarioRepositorio;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor

public class GrupoServicio implements IGrupoServicio {
    @Autowired
    private IGrupoRepositorio grupoRepositorio;
    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;


    public Grupo crearGrupo(Grupo grupo){
        return grupoRepositorio.save(grupo);
    }
    public PartiDTO nuevoPartiGrup(Long idUsuario, Long id_grupo){

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


        PartiDTO partiDTO = new PartiDTO();
        partiDTO.setId_grupo(grupo.getId_grupo());
        partiDTO.setNombre(grupo.getNombre());
        partiDTO.setDescripcion(grupo.getDescripcion());
        partiDTO.setIntegrantes(grupo.getIntegrantes());
        partiDTO.setUbicacion(grupo.getUbicacion());
        partiDTO.setFechaCreacion(grupo.getFechaCreacion());
        partiDTO.setUsuarios(usuariosdto);

        return partiDTO;
    }
    public  PartiDTO  verPartiGrupo(Long id_grupo){

        Optional<Grupo> grupo = grupoRepositorio.findById(id_grupo);
        ArrayList<UsuarioDTO> usuariosdto = new ArrayList<>();
        for (Usuario u: grupo.get().getUsuarios()){
            UsuarioDTO usedto = new UsuarioDTO();
            usedto.setId(u.getId_usuario());
            usedto.setNombre(u.getNombre());
            usuariosdto.add(usedto);
        }
        PartiDTO partiDTO = new PartiDTO();
        partiDTO.setId_grupo(grupo.get().getId_grupo());
        partiDTO.setNombre(grupo.get().getNombre());
        partiDTO.setDescripcion(grupo.get().getDescripcion());
        partiDTO.setIntegrantes(grupo.get().getIntegrantes());
        partiDTO.setUbicacion(grupo.get().getUbicacion());
        partiDTO.setFechaCreacion(grupo.get().getFechaCreacion());
        partiDTO.setUsuarios(usuariosdto);



        return partiDTO;
    }
    public PartiDTO eliminarPartiGrup(Long id_usuario, Long id_grupo){
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
        PartiDTO partiDTO = new PartiDTO();
        partiDTO.setId_grupo(grupo.getId_grupo());
        partiDTO.setNombre(grupo.getNombre());
        partiDTO.setDescripcion(grupo.getDescripcion());
        partiDTO.setIntegrantes(grupo.getIntegrantes());
        partiDTO.setUbicacion(grupo.getUbicacion());
        partiDTO.setFechaCreacion(grupo.getFechaCreacion());
        partiDTO.setUsuarios(usuariosdto);

        return partiDTO;
    }
    public List<Grupo> todoGrupo(){
        return grupoRepositorio.findAll();
    }
}
