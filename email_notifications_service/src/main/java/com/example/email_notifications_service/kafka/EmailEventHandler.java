package com.example.email_notifications_service.kafka;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.exception.NonRetryableException;
import com.example.email_notifications_service.persistence.model.ProcessedEventEntity;
import com.example.email_notifications_service.usecasses.EmailService;
import com.example.email_notifications_service.usecasses.ProcessedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@KafkaListener(topics = {"reports-email-events-topic",
        "cache-reports-events-topic"},
        containerFactory = "kafkaListenerContainerFactory",
        groupId = "email-events")
@Slf4j
@RequiredArgsConstructor
public class EmailEventHandler {

    private final ProcessedEventService processedEventService;
    private final EmailService emailService;


    @Transactional
    @KafkaHandler
    public void handler(@Payload ReadyReportEvent readyReportEvent,
                        @Header("messageId") String messageId,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        log.info("Received message with ID: {}, topic: {}, messageKey: {}", messageId, topic, messageKey);

        ProcessedEventEntity processedEventEntity = processedEventService.findByMessageId(messageId);

        if (processedEventEntity != null) {
            log.info("Duplicated message with ID: {}", messageId);
            return;
        }

        log.info("Processed message with ID: {}", messageId);
        log.info("Processed event with ID: {}", readyReportEvent.getReportName());

        try {
            emailService.sendEmailWithAttachment(readyReportEvent.getUserEmail(),
                    "Your Sales Report: " + readyReportEvent.getReportName(),
                    "Please find attached your sales report: " + readyReportEvent.getReportName() +
                    ". Generated for the period " + readyReportEvent.getRangeStart() + " to " +
                    readyReportEvent.getRangeEnd(),
                    readyReportEvent.getFilePath());

            ProcessedEventEntity processedEvent = ProcessedEventEntity.builder()
                    .withMessageId(messageId)
                    .withReportEventId(readyReportEvent.getOrderId())
                    .withReportName(readyReportEvent.getReportName())
                    .build();
            processedEventService.save(processedEvent);
            log.info("Processed message with ID: {}", messageId);
        } catch (Exception e) {
            log.error("Error saving processed event: {}", e.getMessage());
            log.error(e.getMessage());
            throw new NonRetryableException(e);
        }
    }
}


