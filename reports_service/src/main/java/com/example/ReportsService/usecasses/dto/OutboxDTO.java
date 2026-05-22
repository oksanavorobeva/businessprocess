package com.example.ReportsService.usecasses.dto;



import com.example.ReportsService.persistence.model.OutboxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
