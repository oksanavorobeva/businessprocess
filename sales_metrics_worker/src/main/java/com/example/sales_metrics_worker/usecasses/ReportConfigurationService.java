package com.example.sales_metrics_worker.usecasses;

import com.example.sales_metrics_worker.persistence.model.ReportConfigurationEntity;
import com.example.sales_metrics_worker.persistence.repository.ReportConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportConfigurationService {

    private final ReportConfigurationRepository reportConfigurationRepository;

    public ReportConfigurationEntity getReportName(String reportName) {
        return reportConfigurationRepository.findByReportName(reportName);
    }
}
