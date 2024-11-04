package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Amigo;
import dijj.traveltogetherback.modelo.Usuario;
import dijj.traveltogetherback.repositorio.IAmigoRepositorio;

import java.util.List;

public class AmigoServicio implements IAmigoServicio {

    private IAmigoRepositorio amigoRepositorio;

    public AmigoServicio(IAmigoRepositorio amigoRepositorio){
        this.amigoRepositorio = amigoRepositorio;
    }
    public List<Amigo> todosLosAmigos() {
        return amigoRepositorio.findAll();
    }

    public Amigo buscarAmigoPorNombre(String nombre) {
        return amigoRepositorio.findByNombre(nombre);
    }

    public void agregarAmigo(String nombre) {

    }

    public void eliminarAmigo(String nombre) {
        Amigo amigo = amigoRepositorio.findByNombre(nombre);
        if (amigo != null) {
            amigoRepositorio.delete(amigo);
        }
    }
}
