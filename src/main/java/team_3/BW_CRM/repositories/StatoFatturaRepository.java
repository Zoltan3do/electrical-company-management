package team_3.BW_CRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team_3.BW_CRM.entities.StatoFattura;

import java.util.Optional;

public interface StatoFatturaRepository extends JpaRepository<StatoFattura, Long > {
    Optional<StatoFattura> findByTipo(String tipo);
}
