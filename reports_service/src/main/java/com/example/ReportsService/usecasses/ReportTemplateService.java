package com.example.ReportsService.usecasses;


import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.persistence.repository.ReportTemplateRepository;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import com.example.ReportsService.usecasses.mapper.ReportTemplateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReportTemplateService {

    private final ReportTemplateRepository reportTemplateRepository;
    private final ReportTemplateMapper reportTemplateMapper;

    public ReportTemplate getReportTemplateById(Long id) {
        return reportTemplateRepository.getTemplateByReportId(id);
    }

    public List<ReportTemplateDto> getAllReportTemplates() {
        return reportTemplateRepository.findAll().stream()
                .map(reportTemplateMapper::toDto)
                .toList();
    }
}
