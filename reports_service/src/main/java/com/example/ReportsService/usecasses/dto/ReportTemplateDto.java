package com.example.ReportsService.usecasses.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportTemplateDto {
    private Long reportId;
    private String reportName;
    private String topic;
}
