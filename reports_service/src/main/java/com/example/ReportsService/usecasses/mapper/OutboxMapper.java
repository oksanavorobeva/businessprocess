package com.example.ReportsService.usecasses.mapper;


import com.example.ReportsService.persistence.model.Outbox;
import com.example.ReportsService.usecasses.dto.OutboxDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface OutboxMapper {

    OutboxDTO fromEntityToDto(Outbox outbox);

    Outbox fromDtoToEntity(OutboxDTO outboxDTO);
}
