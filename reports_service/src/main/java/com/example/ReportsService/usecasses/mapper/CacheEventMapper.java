package com.example.ReportsService.usecasses.mapper;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.example.ReportsService.usecasses.dto.ReportDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface CacheEventMapper {

    ReportCacheDto toReportCacheDReadyReportEvent(ReadyReportEvent readyReportEvent);

    ReadyReportEvent toreadyReportEvent(ReportCacheDto reportCacheDto);
   /* private static ReadyReportEvent getReadyReportEvent(ReportCacheDto cachedReport) {
        ReadyReportEvent readyReportEvent = new ReadyReportEvent();
        readyReportEvent.setOrderId(cachedReport.getReportId());
        readyReportEvent.setReportName(cachedReport.getReportName());
        readyReportEvent.setRangeStart(cachedReport.getRangeStart());
        readyReportEvent.setRangeEnd(cachedReport.getRangeEnd());
        readyReportEvent.setFilePath(cachedReport.getFilePath());
        readyReportEvent.setUserEmail(cachedReport.getUserEmail());
        return readyReportEvent;
    }*/
    /*private static ReportCacheDto getReportCacheDto(ReadyReportEvent readyReportEvent) {
        ReportCacheDto reportCacheDto = new ReportCacheDto();
        reportCacheDto.setReportId(readyReportEvent.getOrderId());
        reportCacheDto.setReportName(readyReportEvent.getReportName());
        reportCacheDto.setRangeStart(readyReportEvent.getRangeStart());
        reportCacheDto.setRangeEnd(readyReportEvent.getRangeEnd());
        reportCacheDto.setFilePath(readyReportEvent.getFilePath());
        reportCacheDto.setUserEmail(readyReportEvent.getUserEmail());
        return reportCacheDto;
    }*/
}