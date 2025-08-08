package team_3.BW_CRM.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team_3.BW_CRM.entities.Ruolo;
import team_3.BW_CRM.repositories.RuoloRepository;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RuoloRepository ruoloRepository;

    @Override
    public void run(String... args) {
        if (ruoloRepository.findByTipo("USER").isEmpty()) {
            ruoloRepository.save(new Ruolo("USER"));
        }
        if (ruoloRepository.findByTipo("ADMIN").isEmpty()) {
            ruoloRepository.save(new Ruolo("ADMIN"));
        }
    }
}
