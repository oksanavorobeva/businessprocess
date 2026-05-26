package com.example.ReportsService.usecasses.mapper;


import com.example.ReportsService.persistence.model.Report;
import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface ReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reportTemplate",source = "template")
    Report toEntity(ReportRequestDto requestDto, ReportTemplate template);

    ReportDto toDto(Report report);
}

