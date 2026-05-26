package com.example.ReportsService.persistence.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "report_order")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    ReportTemplate reportTemplate;

    @Column(name = "range_Start")
    private LocalDate rangeStart;

    @Column(name = "range_End")
    private LocalDate rangeEnd;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "date")
    private LocalDate date;
}
