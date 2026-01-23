package com.example.ReportsService.persistence.repository;

import com.example.ReportsService.persistence.model.ReportTemplate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long> {
    ReportTemplate getTemplateByReportId(Long id);
}
