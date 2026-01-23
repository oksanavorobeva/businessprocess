package com.example.ReportsService.usecasses.mapper;

import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface ReportTemplateMapper {

    ReportTemplateDto toDto(ReportTemplate order);
}
