package com.example.sales_metrics_worker.persistence.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "report_configurations")
public class ReportConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_name", unique = true, nullable = false)
    private String reportName;

    @Column(name = "strategy_bean_name", nullable = false)
    private String strategyBeanName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;
}