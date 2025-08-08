package team_3.BW_CRM.payloads;

import jakarta.validation.constraints.*;
import team_3.BW_CRM.entities.Cliente;
import team_3.BW_CRM.entities.StatoFattura;

import java.time.LocalDate;

public record NewFatturaDTO(
        @Future(message = "La data deve essere futura!")
        LocalDate data,

        @NotNull(message = "L'importo è obbligatorio!")
        @Positive(message = "L'importo deve essere positivo!")
        Double importo,

        @NotNull(message = "Il numero fattura è obbligatorio!")
        Integer numero,

        @NotNull(message = "Il cliente è obbligatorio!")
        Long clienteId,

        @NotNull(message = "Lo stato fattura è obbligatorio!")
        String statoFattura
) {

}
