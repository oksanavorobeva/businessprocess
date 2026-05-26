package com.example.ReportsService;


import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.Status;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;

import java.time.LocalDate;


public class ReportTestData {

    public static final Long id = 123L;

    public static ReportRequestDto createRequestDto() {
        Long reportId = 123L;
        String userEmail = "test@example.com";
        LocalDate rangeStart = LocalDate.of(2023, 1, 1);
        LocalDate rangeEnd = LocalDate.of(2023, 1, 31);
        return new ReportRequestDto(
                reportId,
                userEmail,
                rangeStart,
                rangeEnd
        );
    }

    public static ReportDto createReportDto() {
        Long reportId = 123L;
        ReportTemplateDto reportTemplateDto = new ReportTemplateDto();
        reportTemplateDto.setReportId(reportId);
        LocalDate rangeStart = LocalDate.of(2023, 1, 1);
        LocalDate rangeEnd = LocalDate.of(2023, 1, 31);
        String userEmail = "test@example.com";
        String filePath = "111bbbb";
        String statusString = "READY";
        Status status = Status.valueOf(statusString);
        LocalDate date = LocalDate.of(2023, 1, 31);
        return new ReportDto(
                reportId,
                reportTemplateDto,
                rangeStart,
                rangeEnd,
                userEmail,
                filePath,
                status,
                date
        );
    }

    public static Report.ReportBuilder createReport() {
        String statusString = "READY";
        return Report.builder()
                .withId(123L)
                .withRangeStart(LocalDate.of(2023, 1, 1))
                .withRangeEnd(LocalDate.of(2023, 1, 31))
                .withUserEmail("test@example.com")
                .withStatus(Status.valueOf(statusString))
                .withFilePath("111bbbb")
                .withDate(LocalDate.of(2023, 1, 1));

    }

    public static ReportCacheDto createReportCacheDto() {
        Long orderId = 123L;
        String reportName = "test";
        LocalDate rangeStart = LocalDate.of(2023, 1, 1);
        LocalDate rangeEnd = LocalDate.of(2023, 1, 31);
        String userEmail = "test@example.com";
        String filePath = "test.pdf";
        return new ReportCacheDto(
                orderId,
                reportName,
                rangeStart,
                rangeEnd,
                userEmail,
                filePath
        );
    }
}
