package com.example.ReportsService.persistence.repository;

import com.example.ReportsService.persistence.model.ReportTemplate;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long> {

    Optional<ReportTemplate> findByReportName(String name);

}
