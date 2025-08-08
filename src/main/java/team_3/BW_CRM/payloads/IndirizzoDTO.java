package team_3.BW_CRM.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import team_3.BW_CRM.entities.Comune;

public record IndirizzoDTO(
        @NotEmpty
        String via,
        @NotEmpty
        String civico,
        @NotEmpty
        String localita,
        @NotEmpty
        String cap,
        @NotNull
        ComuneDTO comune
) {
}
