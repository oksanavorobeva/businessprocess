package com.example.ReportsService.usecasses;


import com.example.ReportsService.persistence.model.ReportKey;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.example.ReportsService.usecasses.dto.ReportDto;
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

    private static final Duration REPORT_CACHE_DURATION = Duration.ofHours(24);

    public ReportCacheDto getReportFromCache(ReportKey reportKey) {
        try {
            ReportCacheDto reportDto = redisTemplate.opsForValue().get(reportKey);
            return reportDto;
        } catch (Exception e) {
            log.error("Error retrieving report from Redis with key: {}", reportKey, e);
            return null;
        }
    }

    public void saveReportToCache(ReportKey key, ReportCacheDto reportCacheDto) {
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
}


