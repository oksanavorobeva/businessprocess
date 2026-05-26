package com.example.ReportsService.usecasses.impl;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import com.example.ReportsService.persistence.model.Outbox;
import com.example.ReportsService.persistence.model.OutboxType;
import com.example.ReportsService.persistence.repository.OutboxRepository;
import com.example.ReportsService.usecasses.OutboxService;
import com.example.ReportsService.usecasses.dto.OutboxDTO;
import com.example.ReportsService.usecasses.mapper.OutboxMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.ReportsService.OutboxTestData.*;
import static com.example.ReportsService.ReadyReportEventData.*;
import static com.example.ReportsService.ReportCreatedEventData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OutboxServiceTest {

    @InjectMocks
    private OutboxService outboxService;

    @Mock
    private OutboxRepository outboxRepository;

    @Mock
    private OutboxMapperImpl outboxMapper;

    @Mock
    private ObjectMapper objectMapper;


    @Test
    void createReportReadyEvent_shouldSaveOutbox() throws Exception {
        ReadyReportEvent readyReportEvent = createdReadyReportEvent();
        OutboxDTO outboxDTO = createdOutboxDTO();
        Outbox outbox = createdOutbox();

        when(outboxMapper.fromDtoToEntity(any(OutboxDTO.class))).thenReturn(outbox);
        when(outboxRepository.save(any(Outbox.class))).thenReturn(outbox);

        outboxService.createReportReadyEvent(readyReportEvent);

        verify(objectMapper).writeValueAsString(readyReportEvent);
        ArgumentCaptor<Outbox> outboxCaptor = ArgumentCaptor.forClass(Outbox.class);
        verify(outboxRepository).save(outboxCaptor.capture());
        Outbox savedOutbox = outboxCaptor.getValue();
        assertNotNull(savedOutbox, "Сохраненный Outbox не должен быть null");
        assertEquals(outboxDTO.getPayload(), savedOutbox.getPayload(), "Payload должен соответствовать");
        assertEquals(OutboxType.CacheEvent, savedOutbox.getType(), "Тип должен быть CacheEvent");
        assertNotNull(savedOutbox.getCreated(), "Дата создания не должна быть null");
        verifyNoMoreInteractions(objectMapper, outboxMapper, outboxRepository);
    }

    @Test
    void createReportCreatedEvent_shouldSaveOutbox() throws Exception {
        ReportCreatedEvent reportCreatedEvent = createdReportCreatedEvent();
        OutboxDTO outboxDTO = createdOutboxDTO();
        Outbox outbox = createdOutbox();

        when(outboxMapper.fromDtoToEntity(any(OutboxDTO.class))).thenReturn(outbox);
        when(outboxRepository.save(any(Outbox.class))).thenReturn(outbox);

        outboxService.createReportCreatedEvent(reportCreatedEvent);

        verify(objectMapper).writeValueAsString(reportCreatedEvent);
        ArgumentCaptor<Outbox> outboxCaptor = ArgumentCaptor.forClass(Outbox.class);
        verify(outboxRepository).save(outboxCaptor.capture());
        Outbox savedOutbox = outboxCaptor.getValue();
        assertNotNull(savedOutbox, "Сохраненный Outbox не должен быть null");
        assertEquals(outboxDTO.getPayload(), savedOutbox.getPayload(), "Payload должен соответствовать");
        assertEquals(OutboxType.CacheEvent, savedOutbox.getType(), "Тип должен быть CacheEvent");
        assertNotNull(savedOutbox.getCreated(), "Дата создания не должна быть null");
        verifyNoMoreInteractions(objectMapper, outboxMapper, outboxRepository);
    }
}
