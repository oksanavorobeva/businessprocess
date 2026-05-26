package com.example.ReportsService.usecasses.mapper;


import com.example.ReportsService.persistence.model.Outbox;
import com.example.ReportsService.usecasses.dto.OutboxDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.ReportsService.OutboxTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class OutboxMapperTest {

    @InjectMocks
    private OutboxMapperImpl outboxMapper;

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeToDto() {
        Outbox outbox = createdOutbox();
        OutboxDTO outboxDTO = outboxMapper.fromEntityToDto(outbox);
        assertEquals(outbox.getId(), outboxDTO.getId());
        assertEquals(outbox.getPayload(), outboxDTO.getPayload());
        assertEquals(outbox.getType(), outboxDTO.getType());
        assertEquals(outbox.getCreated(), outboxDTO.getCreated());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeToEntity() {
        OutboxDTO outboxDTO = createdOutboxDTO();
        Outbox outbox = outboxMapper.fromDtoToEntity(outboxDTO);
        assertEquals(outboxDTO.getId(), outbox.getId());
        assertEquals(outboxDTO.getPayload(), outbox.getPayload());
        assertEquals(outboxDTO.getType(), outbox.getType());
        assertEquals(outboxDTO.getCreated(), outbox.getCreated());
    }
}