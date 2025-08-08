package team_3.BW_CRM.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@ToString
@Entity
@Table(name = "comuni")
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String codiceProvincia;
    private String codiceComune;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    @JsonIgnore
    private Provincia provincia;

    @OneToMany(mappedBy = "comune")
    @JsonIgnore
    private List<Indirizzo> indirizzi= new ArrayList<>();

    public Comune(String codiceProvincia, String codiceComune, String nome, Provincia provincia) {
        this.codiceProvincia = codiceProvincia;
        this.codiceComune = codiceComune;
        this.nome = nome;
        this.provincia = provincia;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public List<Indirizzo> getIndirizzi() {
        return indirizzi;
    }

    public void setIndirizzi(List<Indirizzo> indirizzi) {
        this.indirizzi = indirizzi;
    }
}
