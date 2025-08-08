package team_3.BW_CRM.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@ToString
@Entity
@Table(name = "ruoli")
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String tipo;

    @Setter
    @ManyToMany(mappedBy = "ruoli")
    @JsonIgnore
    private List<Utente> utenti;


    public Ruolo(String tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }


    public String getTipo() {
        return tipo;
    }


    public List<Utente> getUtenti() {
        return utenti;
    }

}
