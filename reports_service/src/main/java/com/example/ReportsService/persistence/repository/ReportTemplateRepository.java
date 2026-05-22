package com.example.ReportsService.persistence.repository;

import com.example.ReportsService.persistence.model.ReportTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long> {

    Optional<ReportTemplate> findByReportName(String name);

}
