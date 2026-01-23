package com.example.ReportsService.usecasses.mapper;


import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.usecasses.ReportTemplateService;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public abstract class ReportMapper {

    @Autowired
    ReportTemplateService reportTemplateService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reportId", expression = "java(reportTemplateService.getReportTemplateById(requestDto.getReportId()))")
    public abstract Report toEntity(ReportRequestDto requestDto);

    public abstract ReportDto toDto(Report report);
}

