package team_3.BW_CRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_3.BW_CRM.entities.Provincia;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia,Long> {
    Optional<Provincia> findBySigla(String sigla);
    Optional<Provincia> findByNome(String nome);
}
