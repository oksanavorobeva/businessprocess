package com.example.ReportsService.usecasses.dto;


import com.example.ReportsService.persistence.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private Long id;
    private ReportTemplateDto reportId;
    private LocalDate rangeStart;
    private LocalDate rangeEnd;
    private String userEmail;
    private String filePath;
    private Status status;
    private LocalDate date;
}
