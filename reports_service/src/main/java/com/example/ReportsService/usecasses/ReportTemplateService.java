package com.example.ReportsService.usecasses;


import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.persistence.repository.ReportTemplateRepository;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import com.example.ReportsService.usecasses.mapper.ReportTemplateMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReportTemplateService {

    private final ReportTemplateRepository reportTemplateRepository;
    private final ReportTemplateMapper reportTemplateMapper;

    public ReportTemplate getReportTemplateById(Long id) {
        return reportTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReportTemplate not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<ReportTemplateDto> getAllReportTemplates(Pageable pageable) {
        return reportTemplateRepository.findAll(pageable)
                .map(reportTemplateMapper::toDto)
                ;
    }

    public String getReportTemplateByName(String name) {
        log.debug("Finding topic name for report template: {}", name);

        return reportTemplateRepository.findByReportName(name)
                .map(template -> template.getTopic().getTopicName()) // Берем имя из вложенной сущности Topic
                .orElseThrow(() -> new EntityNotFoundException("ReportTemplate not found with name: " + name));
    }
}
