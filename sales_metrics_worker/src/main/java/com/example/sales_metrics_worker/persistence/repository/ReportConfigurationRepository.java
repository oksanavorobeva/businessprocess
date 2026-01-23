package com.example.sales_metrics_worker.persistence.repository;

import com.example.sales_metrics_worker.persistence.model.ReportConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportConfigurationRepository extends JpaRepository<ReportConfigurationEntity, Long> {
    ReportConfigurationEntity findByReportName(String reportName);
}
