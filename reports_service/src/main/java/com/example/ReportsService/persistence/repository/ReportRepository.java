package com.example.ReportsService.persistence.repository;

import com.example.ReportsService.persistence.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findReportById(long id);
}
