package com.example.ReportsService.usecasses.mapper;


import com.example.ReportsService.ReportTemplateTestData;
import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.ReportsService.ReportTestData.createReport;
import static com.example.ReportsService.ReportTestData.createRequestDto;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ReportMapperTest {

    @InjectMocks
    private ReportMapperImpl reportMapper;

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeToDto() {
        Report report = createReport().build();
        ReportDto reportDto = reportMapper.toDto(report);
        assertEquals(report.getId(), reportDto.getId());
        assertEquals(report.getReportTemplate(), reportDto.getReportId());
        assertEquals(report.getRangeStart(), reportDto.getRangeStart());
        assertEquals(report.getRangeEnd(), reportDto.getRangeEnd());
        assertEquals(report.getDate(), reportDto.getDate());
        assertEquals(report.getUserEmail(), reportDto.getUserEmail());
        assertEquals(report.getFilePath(), reportDto.getFilePath());
        assertEquals(report.getStatus(), reportDto.getStatus());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeToEntity() {
        ReportRequestDto reportDto = createRequestDto();
        ReportTemplate reportTemplate = ReportTemplateTestData.createReportTemplate();
        Report report = reportMapper.toEntity(reportDto, reportTemplate);
        assertEquals(reportDto.getRangeStart(), report.getRangeStart());
        assertEquals(reportDto.getRangeEnd(), report.getRangeEnd());
        assertEquals(reportDto.getUserEmail(), report.getUserEmail());
    }
}