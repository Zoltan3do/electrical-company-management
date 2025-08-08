package team_3.BW_CRM.payloads;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import team_3.BW_CRM.entities.Indirizzo;
import team_3.BW_CRM.enums.TipoCliente;

public record ClienteDTO(@NotEmpty(message = "La ragione sociale è obbligatoria!")
                         @Size(min = 2, max = 40, message = "La ragione sociale deve essere compreso tra 2 e 40 caratteri!")
                         String ragioneSociale,
                         @NotEmpty(message = "La partita iva è obbligatorio!")
                         @Size(min = 11, max = 11, message = "Lunghezza non valida, deve avere 11 caratteri!")
                         String partitaIva,
                         @NotEmpty(message = "Email obbligatoria!")
                         @Email
                         String email,
                         @NotEmpty(message = "La pec è obbligatoria!")
                         @Email
                         String pec,
                         @NotEmpty(message = "Il numero di cellulare è obbligatorio!")
                         @Size(min = 10, max = 10, message = "Lunghezza non valida, deve avere 10 numeri! ")
                         String telefono,
                         @NotEmpty(message = "La mail di contatto è obbligatoria!")
                         @Email
                         String emailContatto,
                         @NotEmpty(message = "Il nome contatto è obbligatorio!")
                         @Size(min = 2, max = 40, message = "Il nome contatto deve essere compreso tra 2 e 40 caratteri!")
                         String nomeContatto,
                         @NotEmpty(message = "Il cognome contatto è obbligatorio!")
                         @Size(min = 2, max = 40, message = "Il cognome contatto deve essere compreso tra 2 e 40 caratteri!")
                         String cognomeContatto,
                         @NotEmpty(message = "Lo username è obbligatorio!")
                         @Size(min = 10, max = 10, message = "Lunghezza non valida, deve avere 10 numeri!")
                         String telefonoContatto,
                         @Enumerated(EnumType.STRING)
                         TipoCliente tipoCliente,
                         @NotNull
                         IndirizzoDTO indirizzoSedeLegale) {
}
