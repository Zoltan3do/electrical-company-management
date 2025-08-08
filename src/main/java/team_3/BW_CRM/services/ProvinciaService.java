package team_3.BW_CRM.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_3.BW_CRM.entities.Provincia;
import team_3.BW_CRM.exceptions.NotFoundException;
import team_3.BW_CRM.payloads.ProvinciaDTO;
import team_3.BW_CRM.repositories.ProvinciaRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository pr;


    @Autowired
    private Validator validator;


    public Provincia save(ProvinciaDTO body) {
        Provincia newProvince = new Provincia(body.nome(), body.sigla(), body.regione());
        return this.pr.save(newProvince);
    }

    public Optional<Provincia> findProvinciaById(Long id) {
        return pr.findById(id);
    }


    public Optional<Provincia> findBySigla(String sigla) {
        if (sigla == null || sigla.isEmpty()) {
            System.out.println("La sigla non puo essere vuota");
            throw new IllegalArgumentException("La sigla non puo essere vuota");
        }
        Optional<Provincia> provincia = pr.findBySigla(sigla);
        if (provincia.isEmpty()) {
            System.out.println("Not found");
            throw new NotFoundException("Provincia non trovata");
        }
        return provincia;
    }


    public Optional<Provincia> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Il nome non puo essere vuota");
            throw new IllegalArgumentException("Il nome non puo essere vuoto");
        }
        Optional<Provincia> provincia = pr.findByNome(nome);
        if (provincia.isEmpty()) {
            System.out.println("Not found!");
        }
        return provincia;
    }

    public void estrazioneProvinceCsv(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String linea;
        br.readLine();
        linea = br.readLine();
        while (linea != null) {
            String[] colonne = linea.split(";");
            if (colonne.length < 3) {
                System.out.println(("Riga non valida"));
                linea = br.readLine();
                continue;
            }
            String sigla = colonne[0].trim();
            String nome = colonne[1].trim();
            String regione = colonne[2].trim();

            ProvinciaDTO provincia = new ProvinciaDTO(sigla, nome, regione);
            Set<ConstraintViolation<ProvinciaDTO>> violations = validator.validate(provincia);
            if (!violations.isEmpty()) {
                System.out.println("Riga invalida");
                linea = br.readLine();
                continue;
            }
            this.save(provincia);
            linea = br.readLine();
        }
    }


}
