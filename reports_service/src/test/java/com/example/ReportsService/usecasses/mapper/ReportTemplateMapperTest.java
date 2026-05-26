package com.example.ReportsService.usecasses.mapper;


import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.ReportsService.ReportTemplateTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ReportTemplateMapperTest {

    @InjectMocks
    private ReportTemplateMapperImpl reportMapper;

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeToDto() {
        ReportTemplate reportTemplate = createReportTemplate();
        ReportTemplateDto reportTemplateDto = reportMapper.toDto(reportTemplate);
        assertEquals(reportTemplate.getReportId(), reportTemplateDto.getReportId());
        assertEquals(reportTemplate.getReportName(), reportTemplateDto.getReportName());
        assertEquals(reportTemplate.getTopic(), reportTemplateDto.getTopic());
    }
}