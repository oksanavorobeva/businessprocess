package com.example.ReportsService.usecasses.impl;


import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.persistence.repository.ReportTemplateRepository;
import com.example.ReportsService.usecasses.ReportTemplateService;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import com.example.ReportsService.usecasses.mapper.ReportTemplateMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.ReportsService.ReportTemplateTestData.*;
import static com.example.ReportsService.ReportTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportTemplatesServiceTest {

    @InjectMocks
    private ReportTemplateService reportTemplateService;

    @Mock
    private ReportTemplateRepository reportTemplateRepository;

    @Mock
    private ReportTemplateMapperImpl reportTemplateMapper;


    @Test
    void getReportTemplateById_whenReportExists_shouldReturnReport() {
        ReportTemplate reportTemplate = createReportTemplate();
        when(reportTemplateRepository.findById(any())).thenReturn(Optional.of(reportTemplate));

        reportTemplateService.getById(any());

        verify(reportTemplateRepository, times(1)).findById(any());
        verifyNoMoreInteractions(reportTemplateRepository);
    }

    @Test
    void getReportTemplateByName_whenReportExists_shouldReturnReport() {
        ReportTemplate reportTemplate = createReportTemplate();
        when(reportTemplateRepository.findByReportName(reportName))
                .thenReturn(Optional.of(reportTemplate));
        String result = reportTemplateService.getReportTemplateByName(reportName);

        verify(reportTemplateRepository, times(1)).findByReportName(reportName);
        verifyNoMoreInteractions(reportTemplateRepository);

        assertNotNull(result);
        assertEquals(reportTemplate.getTopic().getTopicName(), result);
    }

    @Test
    void getAllReportTemplates_shouldReturnPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 10);

        ReportTemplate reportTemplate = createReportTemplate();
        List<ReportTemplate> templates = List.of(reportTemplate);
        Page<ReportTemplate> templatePage = new PageImpl<>(templates, pageable, templates.size());

        ReportTemplateDto expectedDto = createReportTemplateDto();

        when(reportTemplateRepository.findAll(pageable)).thenReturn(templatePage);
        when(reportTemplateMapper.toDto(reportTemplate)).thenReturn(expectedDto);

        Page<ReportTemplateDto> result = reportTemplateService.getAllReportTemplates(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(expectedDto, result.getContent().get(0));

        verify(reportTemplateRepository, times(1)).findAll(pageable);
        verify(reportTemplateMapper, times(1)).toDto(reportTemplate);
        verifyNoMoreInteractions(reportTemplateRepository);
    }
}
