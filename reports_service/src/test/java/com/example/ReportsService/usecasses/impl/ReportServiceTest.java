package com.example.ReportsService.usecasses.impl;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import com.example.ReportsService.ReportCreatedEventData;
import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.persistence.model.Status;
import com.example.ReportsService.persistence.repository.ReportRepository;
import com.example.ReportsService.usecasses.OutboxService;
import com.example.ReportsService.usecasses.RedisService;
import com.example.ReportsService.usecasses.ReportService;
import com.example.ReportsService.usecasses.ReportTemplateService;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import com.example.ReportsService.usecasses.mapper.OrderCreatedEventMapper;
import com.example.ReportsService.usecasses.mapper.ReportMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.ReportsService.ReadyReportEventData.*;
import static com.example.ReportsService.ReportTemplateTestData.*;
import static com.example.ReportsService.ReportTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private RedisService redisService;

    @Mock
    private ReportMapperImpl reportMapper;

    @Mock
    private ReportTemplateService reportTemplateService;

    @Mock
    private OrderCreatedEventMapper orderCreatedEventMapper;

    @Mock
    OutboxService outboxService;

    @Test
    void shouldCreateNewReportWhenNotFoundInRedis() {
        ReportCreatedEvent createdEvent = ReportCreatedEventData.createdReportCreatedEvent();
        ReportTemplate reportTemplate = createReportTemplate();
        ReportRequestDto requestDto = createRequestDto();
        ReportDto reportDto = createReportDto();

        Report report = createReport().build();
        when(reportTemplateService.getById(anyLong())).thenReturn(reportTemplate);
        when(reportMapper.toEntity(requestDto, reportTemplate)).thenReturn(report);
        when(redisService.checkReport(report)).thenReturn(null);
        when(reportRepository.save(report)).thenReturn(report);
        when(reportMapper.toDto(report)).thenReturn(reportDto);
        when(orderCreatedEventMapper.toOrderCreatedEvent(reportDto)).thenReturn(createdEvent);

        String result = reportService.createReport(requestDto);

        assertEquals("Отчет отправлен в обработку", result);
        verify(outboxService).createReportCreatedEvent(createdEvent);
        verify(outboxService, never()).createReportReadyEvent(any());
        verify(reportRepository).save(report);
        verify(reportMapper).toDto(report);
        verify(orderCreatedEventMapper).toOrderCreatedEvent(reportDto);
    }

    @Test
    void shouldUseCachedReportWhenFoundInRedis() {
        ReportTemplate reportTemplate = createReportTemplate();
        ReportRequestDto requestDto = createRequestDto();
        Report report = createReport().build();
        ReportCacheDto cachedReport = createReportCacheDto();
        ReadyReportEvent readyEvent = createdReadyReportEvent();

        when(reportTemplateService.getById(anyLong())).thenReturn(reportTemplate);
        when(reportMapper.toEntity(requestDto, reportTemplate)).thenReturn(report);
        when(redisService.checkReport(report)).thenReturn(cachedReport);
        when(redisService.processCachedReport(report, cachedReport)).thenReturn(readyEvent);

        String result = reportService.createReport(requestDto);

        assertEquals("Отчет отправлен на почту", result);
        verify(outboxService).createReportReadyEvent(readyEvent);
        verify(outboxService, never()).createReportCreatedEvent(any());
        verify(reportRepository, never()).save(any());
        verify(reportMapper, never()).toDto(any());
        verify(orderCreatedEventMapper, never()).toOrderCreatedEvent(any());
    }

    @Test
    void getReportById_whenReportExists_shouldReturnReport() {
        Report report = createReport().build();
        when(reportRepository.findById(any())).thenReturn(Optional.of(report));

        reportService.getReportById(any());

        verify(reportRepository, times(1)).findById(any());
        verifyNoMoreInteractions(reportRepository);
    }

    @Test
    void updateReport_whenReportExists_shouldUpdateStatusToReady() {
        ReadyReportEvent readyReportEvent = createdReadyReportEvent();
        Report existingReport = createReport().build();
        Report updatedReport = createReport()
                .withStatus(Status.READY)
                .withFilePath(readyReportEvent.getFilePath())
                .build();
        ReportDto reportDto = createReportDto();

        when(reportRepository.findByIdForUpdate(readyReportEvent.getOrderId()))
                .thenReturn(Optional.of(existingReport));
        when(reportRepository.saveAndFlush(any(Report.class)))
                .thenReturn(updatedReport);
        when(reportMapper.toDto(updatedReport))
                .thenReturn(reportDto);
        doNothing().when(redisService).saveReportToCache(any(ReportDto.class), any(ReadyReportEvent.class));

        ReportDto result = reportService.updateReport(readyReportEvent);

        assertNotNull(result);
        assertEquals(reportDto, result);
        verify(reportRepository, times(1)).findByIdForUpdate(readyReportEvent.getOrderId());
        verify(reportRepository, times(1)).saveAndFlush(any(Report.class));
        verify(reportMapper, times(1)).toDto(updatedReport);
        verify(redisService, times(1)).saveReportToCache(any(ReportDto.class), any(ReadyReportEvent.class));
    }
}
