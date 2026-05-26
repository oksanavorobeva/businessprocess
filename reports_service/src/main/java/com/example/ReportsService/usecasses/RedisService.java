package com.example.ReportsService.usecasses;


import by.javaguru.core.ReadyReportEvent;
import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.ReportKey;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.mapper.CacheEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<ReportKey, ReportCacheDto> redisTemplate;
    private final CacheEventMapper cacheEventMapper;

    private static final Duration REPORT_CACHE_DURATION = Duration.ofHours(24);

    public ReadyReportEvent processCachedReport(Report report, ReportCacheDto cachedReport) {
        ReportKey reportKey = getReportKey(report);
        getReportFromCache(reportKey);
        log.info("Report found in cache, returning cached report.");
        ReadyReportEvent readyReportEvent = cacheEventMapper.toreadyReportEvent(cachedReport);
        log.info("Report for template {} found in cache", report.getReportTemplate().getReportId());
        return readyReportEvent;
    }

    public ReportCacheDto checkReport(Report savedReport) {
        try {
            ReportKey reportKey = getReportKey(savedReport);
            log.info("ReportKey {}", reportKey);
            ReportCacheDto cachedReport = getReportFromCache(reportKey);
            log.info("Saved cachedReport {}", cachedReport);
            return cachedReport;
        } catch (Exception e) {
            log.error("Error getting cached report: ", e);
        }
        return null;
    }

    public ReportCacheDto getReportFromCache(ReportKey reportKey) {
        try {
            ReportCacheDto reportDto = redisTemplate.opsForValue().get(reportKey);
            return reportDto;
        } catch (Exception e) {
            log.error("Error retrieving report from Redis with key: {}", reportKey, e);
            return null;
        }
    }

    public void saveReportToCache(ReportDto reportDto, ReadyReportEvent readyReportEvent) {
        ReportKey key = generateKey(reportDto);
        ReportCacheDto reportCacheDto = cacheEventMapper.toReportCacheDReadyReportEvent(readyReportEvent);
        try {
            redisTemplate.opsForValue().set(key, reportCacheDto, REPORT_CACHE_DURATION);
            log.info("Report saved to Redis with key: {}", key);
        } catch (Exception e) {
            log.error("Error saving report to Redis with key: {}", key, e);
        }
    }

    public ReportKey generateKey(ReportDto report) {
        return new ReportKey(report.getReportId().getReportName(),
                report.getRangeStart(),
                report.getRangeEnd());
    }

    public ReportKey getReportKey(Report savedReport) {
        ReportKey reportKey = new ReportKey();
        reportKey.setReportName(savedReport.getReportTemplate().getReportName());
        reportKey.setRangeEnd(savedReport.getRangeEnd());
        reportKey.setRangeStart(savedReport.getRangeStart());
        return reportKey;
    }
}


