package com.example.email_notifications_service.usecasses;


import com.example.email_notifications_service.persistence.model.ProcessedEventEntity;
import com.example.email_notifications_service.persistence.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessedEventService {

    private final ProcessedEventRepository processedEventRepository;

    public ProcessedEventEntity findByMessageId(String messageId) {
        return processedEventRepository.findByMessageId(messageId);
    }

    public void save(ProcessedEventEntity processedEvent) {
        processedEventRepository.save(processedEvent);
    }
}
