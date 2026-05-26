package com.example.ReportsService.usecasses;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import com.example.ReportsService.api.exception.ReportNotFoundException;
import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.Status;
import com.example.ReportsService.persistence.repository.ReportRepository;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import com.example.ReportsService.usecasses.mapper.OrderCreatedEventMapper;
import com.example.ReportsService.usecasses.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;



@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportMapper reportMapper;
    private final ReportRepository reportRepository;
    private final OrderCreatedEventMapper orderCreatedEventMapper;
    private final OutboxService outboxService;
    private final RedisService redisService;

    @Transactional
    public String createReport(ReportRequestDto requestDto) {
        Report report = reportMapper.toEntity(requestDto);
        ReportCacheDto cachedReport = redisService.checkReport(report);
        if (cachedReport != null) {
            ReadyReportEvent readyReportEvent = redisService.processCachedReport(report, cachedReport);
            outboxService.createReportReadyEvent(readyReportEvent);
            return "Отчет отправлен на почту";
        } else {
            report.setDate(LocalDate.now());
            report.setStatus(Status.PROCESSING);
            Report savedReport = reportRepository.save(report);
            ReportDto dto = reportMapper.toDto(savedReport);
            ReportCreatedEvent event = orderCreatedEventMapper.toOrderCreatedEvent(dto);
            outboxService.createReportCreatedEvent(event);
        }
        return "Отчет отправлен в обработку";
    }

    @Transactional
    public ReportDto getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("No report found"));
        return reportMapper.toDto(report);
    }

    @Transactional(readOnly = true)
    public Page<ReportDto> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable)
                .map(reportMapper::toDto);
    }

    @Transactional
    public ReportDto updateReport(ReadyReportEvent readyReportEvent) {
        Report report = reportRepository.findByIdForUpdate(readyReportEvent.getOrderId())
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + readyReportEvent.getOrderId()));

        report.setStatus(Status.READY);
        report.setFilePath(readyReportEvent.getFilePath());
        report = reportRepository.saveAndFlush(report);

        ReportDto reportDto = reportMapper.toDto(report);
        redisService.saveReportToCache(reportDto, readyReportEvent);
        return reportDto;
    }
}


