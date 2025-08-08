package team_3.BW_CRM.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team_3.BW_CRM.services.ComuneService;
import team_3.BW_CRM.services.ProvinciaService;

@Component
public class TablesPopulationRunner implements CommandLineRunner {
    @Autowired
    private ComuneService cs;

    @Autowired
    private ProvinciaService ps;

    @Override
    public void run(String... args) throws Exception {
        String pathProvince = "src/main/resources/comuni & province/province-italiane.csv";
        String pathComuni = "src/main/resources/comuni & province/comuni-italiani.csv";

//        ps.estrazioneProvinceCsv(pathProvince);
//        cs.estrazioneComuniCsv(pathComuni);


    }
}
