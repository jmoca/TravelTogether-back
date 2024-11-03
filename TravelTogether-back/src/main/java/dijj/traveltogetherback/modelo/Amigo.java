package dijj.traveltogetherback.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Amigos")
public class Amigo {


    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
