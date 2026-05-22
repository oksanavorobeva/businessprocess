package com.example.ReportsService.usecasses.mapper;


import by.javaguru.core.ReportCreatedEvent;
import com.example.ReportsService.usecasses.dto.ReportDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface OrderCreatedEventMapper {

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "reportName", source = "reportId.reportName")
    ReportCreatedEvent toOrderCreatedEvent(ReportDto orderDto);
}