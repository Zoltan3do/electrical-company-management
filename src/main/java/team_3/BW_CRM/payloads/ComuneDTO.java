package team_3.BW_CRM.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ComuneDTO(
        @NotNull(message = "Il codice provincia non può essere nullo")
        @Pattern(regexp = "\\d{3}", message= "Il codice provincia deve essere un numero di 3 cifre")
        String codProvincia,

        @NotNull(message = "Il codice comune non può essere nullo")
        @Pattern(regexp = "\\d{3}", message = "Il codice comune deve essssere un numero di 3 cifre")
        String codComune,

        @NotEmpty
        String nome,

        @NotEmpty
        String provincia

) {
}
