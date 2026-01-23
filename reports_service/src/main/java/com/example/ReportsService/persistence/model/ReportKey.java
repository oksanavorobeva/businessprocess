package com.example.ReportsService.persistence.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ReportKey implements Serializable {
    private String reportName;
    private LocalDate rangeStart;
    private LocalDate rangeEnd;
}
