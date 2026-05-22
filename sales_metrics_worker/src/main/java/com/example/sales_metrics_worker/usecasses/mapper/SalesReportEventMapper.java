package com.example.sales_metrics_worker.usecasses.mapper;



import by.javaguru.core.CalculatedSalesReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import by.javaguru.core.SalesReportEvent;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        imports = { java.time.LocalDateTime.class } )
public interface SalesReportEventMapper {

    @Mapping(target = "salesReportEvents", source = "calculatedReports")
    @Mapping(target = "reportDate", expression = "java(LocalDateTime.now())")
    SalesReportEvent toSalesReportEvent(
            ReportCreatedEvent reportCreatedEvent,
            List<CalculatedSalesReportEvent> calculatedReports
    );
}