package com.example.ReportsService.persistence.model;

import lombok.*;
import jakarta.persistence.*;


@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "report")
public class ReportTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "topic")
    private String topic;
}
