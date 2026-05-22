package com.example.ReportsService.usecasses.mapper;


import by.javaguru.core.ReadyReportEvent;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface CacheEventMapper {

    ReportCacheDto toReportCacheDReadyReportEvent(ReadyReportEvent readyReportEvent);

    ReadyReportEvent toreadyReportEvent(ReportCacheDto reportCacheDto);

}