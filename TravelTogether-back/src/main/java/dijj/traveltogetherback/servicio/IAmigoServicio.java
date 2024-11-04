package dijj.traveltogetherback.servicio;

import dijj.traveltogetherback.modelo.Amigo;
import dijj.traveltogetherback.modelo.Usuario;

import java.util.List;

public interface IAmigoServicio {

    List<Amigo> todosLosAmigos();

    Usuario buscarAmigoPorNombre(String nombre);

    void agregarAmigo(String nombre);

    void eliminarAmigo(String nombre);
}
