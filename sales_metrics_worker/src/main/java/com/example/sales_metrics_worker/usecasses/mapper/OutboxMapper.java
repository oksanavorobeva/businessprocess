package com.example.sales_metrics_worker.usecasses.mapper;


import com.example.sales_metrics_worker.persistence.model.Outbox;
import com.example.sales_metrics_worker.usecasses.dto.OutboxDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface OutboxMapper {

    OutboxDTO fromEntityToDto(Outbox outbox);

    Outbox fromDtoToEntity(OutboxDTO outboxDTO);
}
