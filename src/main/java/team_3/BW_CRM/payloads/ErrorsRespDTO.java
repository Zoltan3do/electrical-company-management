package team_3.BW_CRM.payloads;

import java.time.LocalDateTime;

public record ErrorsRespDTO(
        String message, LocalDateTime timestamp
) {
}
