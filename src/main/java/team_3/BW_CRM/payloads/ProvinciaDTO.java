package team_3.BW_CRM.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProvinciaDTO (
    @NotEmpty
    String sigla,
    @NotEmpty
    String nome,
    @NotEmpty
    @Size(min=3)
    String regione
){

}
