package team_3.BW_CRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_3.BW_CRM.entities.Ruolo;
import team_3.BW_CRM.entities.Utente;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByTipo(String tipo);
    List<Utente> findUtentiByTipo(String tipo);
}
