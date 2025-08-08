package team_3.BW_CRM.entities;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClienteSpecifications {
    public static Specification<Cliente> withCriteria(Double fatturatoMinimo, LocalDate dataInserimento, LocalDate dataUltimoContatto,
                                                      String parteDelNome, String nomeContatto, String cognomeContatto) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (fatturatoMinimo != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("fatturatoAnnuale"), fatturatoMinimo));
            }
            if (dataInserimento != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dataInserimento"), dataInserimento));
            }
            if (dataUltimoContatto != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dataUltimoContatto"), dataUltimoContatto));
            }
            if (parteDelNome != null && !parteDelNome.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("ragioneSociale"), "%" + parteDelNome + "%"));
            }
            if (nomeContatto != null && !nomeContatto.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nomeContatto"), "%" + nomeContatto + "%"));
            }
            if (cognomeContatto != null && !cognomeContatto.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("cognomeContatto"), "%" + cognomeContatto + "%"));
            }

            return predicate;
        };
    }
}
