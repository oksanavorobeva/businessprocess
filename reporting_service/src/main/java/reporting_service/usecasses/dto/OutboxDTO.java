package reporting_service.usecasses.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reporting_service.persistence.model.OutboxType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutboxDTO {
    private Long id;
    private String payload;
    private LocalDateTime created;
    private OutboxType type;
}
