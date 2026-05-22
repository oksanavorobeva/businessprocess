package reporting_service.usecasses.mapper;



import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import reporting_service.persistence.model.Outbox;
import reporting_service.usecasses.dto.OutboxDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface OutboxMapper {

    OutboxDTO fromEntityToDto(Outbox outbox);

    Outbox fromDtoToEntity(OutboxDTO outboxDTO);
}
