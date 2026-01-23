package com.example.ReportsService.usecasses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportCacheDto implements Serializable {
    private Long orderId;
    private String reportName;
    private LocalDate rangeStart;
    private LocalDate rangeEnd;
    private String userEmail;
    private String filePath;
}
