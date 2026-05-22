package com.example.ReportsService.usecasses.dto;

import com.example.ReportsService.persistence.model.Topic;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportTemplateDto {
    private Long reportId;
    private String reportName;
    private Topic topic;
}
