package team_3.BW_CRM.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team_3.BW_CRM.entities.StatoFattura;
import team_3.BW_CRM.repositories.StatoFatturaRepository;

@Component
public class StatoInitializer implements CommandLineRunner {

    @Autowired
    StatoFatturaRepository statoFatturaRepository;


    @Override
    public void run(String... args) {
        if (statoFatturaRepository.findByTipo("EMESSA").isEmpty()) {
            statoFatturaRepository.save(new StatoFattura("EMESSA"));
        }
        if (statoFatturaRepository.findByTipo("DA SALDARE").isEmpty()) {
            statoFatturaRepository.save(new StatoFattura("DA SALDARE"));
        }
        if (statoFatturaRepository.findByTipo("DA PAGARE").isEmpty()) {
            statoFatturaRepository.save(new StatoFattura("DA PAGARE"));
        }
    }
}
