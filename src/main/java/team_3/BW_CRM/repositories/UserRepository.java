package team_3.BW_CRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_3.BW_CRM.entities.Ruolo;
import team_3.BW_CRM.entities.Utente;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
    Optional<Utente> findById(long id);
    Optional<Utente> findByUsername(String username);
    List<Utente> findByRuoli(Ruolo ruolo);
}
