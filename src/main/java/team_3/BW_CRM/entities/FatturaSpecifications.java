package team_3.BW_CRM.entities;


import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FatturaSpecifications {

    public static Specification<Fattura> hasClienteId(Long clienteId) {
        return (root, query, cb) -> cb.equal(root.get("cliente").get("id"), clienteId);
    }

    public static Specification<Fattura> hasData(LocalDate data) {
        return (root, query, cb) -> cb.equal(root.get("data"), data);
    }

    public static Specification<Fattura> hasAnno(Integer anno) {
        return (root, query, cb) -> {
            // Utilizzare EXTRACT(YEAR FROM data)
            Expression<Integer> year = cb.function("EXTRACT", Integer.class, cb.literal("YEAR FROM"), root.get("data"));
            return cb.equal(year, anno);
        };
    }



    public static Specification<Fattura> hasMinImporto(Double minImporto) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("importo"), minImporto);
    }

    public static Specification<Fattura> hasMaxImporto(Double maxImporto) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("importo"), maxImporto);
    }
}
