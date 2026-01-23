package com.example.ReportsService.usecasses;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import by.javaguru.core.exception.OrderDataMissingException;
import com.example.ReportsService.api.exception.ReportNotFoundException;
import com.example.ReportsService.kafka.ReportCacheEventsProducer;
import com.example.ReportsService.kafka.ReportCreatedEventsProducer;
import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.ReportKey;
import com.example.ReportsService.persistence.model.Status;
import com.example.ReportsService.persistence.repository.ReportRepository;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import com.example.ReportsService.usecasses.mapper.CacheEventMapper;
import com.example.ReportsService.usecasses.mapper.OrderCreatedEventMapper;
import com.example.ReportsService.usecasses.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportMapper reportMapper;
    private final ReportRepository reportRepository;
    private final OrderCreatedEventMapper orderCreatedEventMapper;
    private final CacheEventMapper cacheEventMapper;
    private final ReportCreatedEventsProducer reportCreatedEventsProducer;
    private final ReportCacheEventsProducer reportCacheEventsProducer;
    private final RedisService redisService;

    @Transactional(value = "transactionManager")
    public ReportDto createReport(ReportRequestDto requestDto) {
        Report savedReport = reportMapper.toEntity(requestDto);

        ReportCacheDto cachedReport = null;
        try {
            //Проверяем если отчет в кэш
            ReportKey reportKey = getReportKey(savedReport);
            log.info("ReportKey {}", reportKey);
            cachedReport = redisService.getReportFromCache(reportKey);
            log.info("Saved cachedReport {}", cachedReport);
        } catch (Exception e) {
            log.error("Error getting cached report: ", e);
        }
        if (cachedReport != null) {
            log.info("Report found in cache, returning cached report.");
            try {
                ReadyReportEvent readyReportEvent = cacheEventMapper.toreadyReportEvent(cachedReport);
                //если есть отправляем в емэйл сервис для отправки
                reportCacheEventsProducer.sendCacheEvent(readyReportEvent);
                log.info("send cache event" + " " + readyReportEvent.getOrderId()
                         + " " + readyReportEvent.getReportName()
                         + " " + readyReportEvent.getRangeStart()
                         + " " + readyReportEvent.getRangeEnd()
                         + " " + readyReportEvent.getUserEmail()
                         + " " + readyReportEvent.getFilePath());
                return reportMapper.toDto(savedReport); // как вернуть дто
            } catch (Exception e) {
                throw new OrderDataMissingException(e.getMessage());
            }
        } else {
            //если нет в сервис worker
            savedReport.setDate(LocalDateTime.now());
            savedReport.setStatus(Status.PROCESSING);
            reportRepository.save(savedReport);
            ReportDto savedReportDto = reportMapper.toDto(savedReport);
            try {
                ReportCreatedEvent reportCreatedEvent = orderCreatedEventMapper.toOrderCreatedEvent(savedReportDto);
                log.info(reportCreatedEvent.getUserEmail());
                reportCreatedEvent.setOrderId(savedReportDto.getId());
                reportCreatedEvent.setReportName(savedReportDto.getReportId().getReportName());
                String topicName = savedReportDto.getReportId().getTopic();
                reportCreatedEventsProducer.sendOrderEvent(reportCreatedEvent, topicName);
                log.info("send order event" + " " + reportCreatedEvent.getOrderId()
                         + " " + reportCreatedEvent.getReportName()
                         + " " + reportCreatedEvent.getRangeStart()
                         + " " + reportCreatedEvent.getRangeEnd()
                         + " " + reportCreatedEvent.getUserEmail());
            } catch (Exception e) {
                throw new OrderDataMissingException(e.getMessage());
            }
            return savedReportDto;
        }
    }

    @Transactional(value = "transactionManager")
    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toDto)
                .toList();
    }

    @Transactional(value = "transactionManager")
    public ReportDto getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("No report found"));
        return reportMapper.toDto(report);
    }

    @Transactional(value = "transactionManager")
    public ReportDto updateReport(ReadyReportEvent readyReportEvent) {
        //Сохраныем ссылку и меняем статус
        Report report = reportRepository.findReportById(readyReportEvent.getOrderId());
        report.setStatus(Status.READY);
        report.setFilePath(readyReportEvent.getFilePath());
        ReportDto reportDto = reportMapper.toDto(report);
        reportRepository.save(report);

        //Сохраняеи с кэш
        //не сохраняется getFilePath()
        ReportKey reportKey = redisService.generateKey(reportDto);
        ReportCacheDto reportCacheDto = cacheEventMapper.toReportCacheDReadyReportEvent(readyReportEvent);
        redisService.saveReportToCache(reportKey, reportCacheDto);
        return reportDto;
    }

    private ReportKey getReportKey(Report savedReport) {
        ReportKey reportKey = new ReportKey();
        reportKey.setReportName(savedReport.getReportId().getReportName());
        reportKey.setRangeEnd(savedReport.getRangeEnd());
        reportKey.setRangeStart(savedReport.getRangeStart());
        return reportKey;
    }
}


