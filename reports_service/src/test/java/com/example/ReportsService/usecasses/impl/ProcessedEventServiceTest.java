package com.example.ReportsService.usecasses.impl;


import com.example.ReportsService.persistence.model.ProcessedEventEntity;
import com.example.ReportsService.persistence.repository.ProcessedEventRepository;
import com.example.ReportsService.usecasses.ProcessedEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.example.ReportsService.ProcessedEventTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessedEventServiceTest {

    @InjectMocks
    private ProcessedEventService processedEventService;

    @Mock
    private ProcessedEventRepository processedEventRepository;


    @Test
    void findByMessageById_whenReportExists_shouldReturnProcessedEvent() {
       ProcessedEventEntity processedEvent = createProcessedEventEntity();

        when(processedEventRepository.findByMessageId(messageId)).thenReturn(processedEvent);

        ProcessedEventEntity result = processedEventService.findByMessageId(messageId);

        assertNotNull(result);
        assertEquals(processedEvent, result);

        verify(processedEventRepository, times(1)).findByMessageId(messageId);
        verifyNoMoreInteractions(processedEventRepository);
    }

    @Test
    void whenSave_thenCallProcessedEventRepositoryRepository() {
        ProcessedEventEntity processedEvent = createProcessedEventEntity();

        when(processedEventRepository.save(any())).thenReturn(processedEvent);

        processedEventService.save(processedEvent);

        verify(processedEventRepository, times(1)).save(any());
    }
}
