package com.example.ReportsService.usecasses.mapper;


import com.example.ReportsService.ReportTemplateTestData;
import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.usecasses.ReportTemplateService;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.ReportsService.ReportTestData.createReport;
import static com.example.ReportsService.ReportTestData.createRequestDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ReportMapperImpl.class})
@ExtendWith(MockitoExtension.class)
class ReportMapperTest {

    @MockBean
    private ReportTemplateService reportTemplateService;

    @Autowired
    private ReportMapper reportMapper;

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeToDto() {
        Report report = createReport().build();
        ReportDto reportDto = reportMapper.toDto(report);
        assertEquals(report.getId(), reportDto.getId());
        assertEquals(report.getReportId(), reportDto.getReportId());
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
        Report report = reportMapper.toEntity(reportDto);
        assertEquals(reportDto.getRangeStart(), report.getRangeStart());
        assertEquals(reportDto.getRangeEnd(), report.getRangeEnd());
        assertEquals(reportDto.getUserEmail(), report.getUserEmail());
    }
}