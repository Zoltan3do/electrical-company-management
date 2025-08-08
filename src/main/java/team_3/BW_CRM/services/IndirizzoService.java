package team_3.BW_CRM.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_3.BW_CRM.entities.Comune;
import team_3.BW_CRM.entities.Indirizzo;
import team_3.BW_CRM.entities.Provincia;
import team_3.BW_CRM.exceptions.NotFoundException;
import team_3.BW_CRM.payloads.IndirizzoDTO;
import team_3.BW_CRM.repositories.IndirizzoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository ir;

    @Autowired
    private ComuneService cs;

    public List<Indirizzo> getAllIndirizzi() {
        return ir.findAll();
    }

    public Optional<Indirizzo> getIndirizzoById(Long id) {
        if (id == null) {
            System.out.println("l'indirizzo id non puo essere null");
            throw new IllegalArgumentException("Indirizzo id null");
        }
        Optional<Indirizzo> indirizzo = ir.findById(id);
        if (indirizzo.isEmpty()) {
            throw new NotFoundException("Indirizzo non trovato");
        }
        return ir.findById(id);
    }

    public Indirizzo saveIndirizzo(IndirizzoDTO body) {
        Indirizzo indirizzo1 = new Indirizzo();
        indirizzo1.setCap(body.cap());
        indirizzo1.setCivico(body.civico());
        indirizzo1.setVia(body.via());
        indirizzo1.setLocalita(body.localita());
        indirizzo1.setComune(cs.findComuneByNome(body.comune().nome()).orElseThrow(RuntimeException::new));
        return ir.save(indirizzo1);
    }

    public void deleteIndirizzo(Long id) {
        if (this.getIndirizzoById(id).isEmpty()) {
            throw new NotFoundException("Indirizzo da eliminare non trovato");
        }
        ir.deleteById(id);
    }

    public Indirizzo findById(Long id) {
        return ir.findById(id).orElseThrow(() -> new NotFoundException("Nessun indirizzo trovato!"));
    }

}
