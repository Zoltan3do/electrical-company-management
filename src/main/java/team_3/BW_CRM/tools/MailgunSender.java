package team_3.BW_CRM.tools;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team_3.BW_CRM.entities.Cliente;
import team_3.BW_CRM.entities.Fattura;
import team_3.BW_CRM.entities.Utente;


@Component
public class MailgunSender {
    private String apiKey;
    private String domain;

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey,
                         @Value("${mailgun.domain}") String domain) {
        this.apiKey = apiKey;
        this.domain = domain;
    }

    public void sendRegistrationEmail(Utente recipient) {
        try {
            HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                    .basicAuth("api", this.apiKey)
                    .queryString("from", "epicenergyservices@sandbox1536445f1ef34709aa72232ff038683b.mailgun.org")
                    .queryString("to", recipient.getEmail())
                    .queryString("subject", "Registrazione completata!")
                    .queryString("text", "Benvenuto " + recipient.getNome() + " sulla nostra piattaforma!")
                    .asJson();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Errore durante l'invio dell'email: " + response.getBody().toString());
            }

            System.out.println("Email inviata con successo a " + recipient.getEmail());
        } catch (Exception e) {
            System.err.println("Errore nell'invio dell'email: " + e.getMessage());
        }
    }

        public void sendFatturaEmail(Fattura fattura) {
            Cliente cliente = fattura.getCliente();
            String email = cliente.getEmail();
            String subject = "Dettagli della tua Fattura #" + fattura.getNumero();
            String message = "Gentile " + cliente.getNomeContatto() + ",\n\n" +
                    "Grazie per aver scelto i nostri servizi. Ecco i dettagli della tua fattura:\n\n" +
                    "Data: " + fattura.getData() + "\n" +
                    "Numero: " + fattura.getNumero() + "\n" +
                    "Importo: €" + fattura.getImporto() + "\n" +
                    "Stato: " + fattura.getStatoFattura().getTipo() + "\n\n" +
                    "Cordiali saluti,\nIl team di Epic Energy Services";

            try {
                HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                        .basicAuth("api", this.apiKey)
                        .queryString("from", "epicenergyservices@sandbox1536445f1ef34709aa72232ff038683b.mailgun.org")
                        .queryString("to", email)
                        .queryString("subject", subject)
                        .queryString("text", message)
                        .asJson();

                if (response.getStatus() != 200) {
                    throw new RuntimeException("Errore durante l'invio dell'email: " + response.getBody().toString());
                }

                System.out.println("Email inviata con successo a " + email);
            } catch (Exception e) {
                System.err.println("Errore nell'invio dell'email della fattura: " + e.getMessage());
            }
        }
    public void sendCustomEmailToCliente(Cliente cliente, String subject, String message) {
        String email = cliente.getEmail();

        try {
            HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                    .basicAuth("api", this.apiKey)
                    .queryString("from", "epicenergyservices@sandbox1536445f1ef34709aa72232ff038683b.mailgun.org")
                    .queryString("to", email)
                    .queryString("subject", subject)
                    .queryString("text", message)
                    .asJson();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Errore durante l'invio dell'email: " + response.getBody().toString());
            }

            System.out.println("Email personalizzata inviata con successo a " + email);
        } catch (Exception e) {
            System.err.println("Errore nell'invio dell'email personalizzata: " + e.getMessage());
        }
    }
    }
