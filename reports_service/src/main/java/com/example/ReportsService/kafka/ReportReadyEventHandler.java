package com.example.ReportsService.kafka;


import by.javaguru.core.ReadyReportEvent;
import com.example.ReportsService.persistence.model.ProcessedEventEntity;
import com.example.ReportsService.usecasses.ProcessedEventService;
import com.example.ReportsService.usecasses.ReportService;
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
@KafkaListener(topics = "reports-ready-events-topic", containerFactory = "kafkaListenerContainerFactory",
        groupId = "report-events")
@Slf4j
@RequiredArgsConstructor
public class ReportReadyEventHandler {

    private final ProcessedEventService processedEventService;
    private final ReportService reportService;

    @Transactional(value = "transactionManager")
    @KafkaHandler
    public void handler(@Payload ReadyReportEvent readySalesReportEvent,
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
        log.info("Processed event with ID: {}", readySalesReportEvent.getOrderId());
        log.info("Processed event with ID: {}", readySalesReportEvent.getReportName());

        reportService.updateReport(readySalesReportEvent);

        ProcessedEventEntity processedEvent = ProcessedEventEntity.builder()
                .withMessageId(messageId)
                .withReportEventId(readySalesReportEvent.getOrderId())
                .withReportName(readySalesReportEvent.getReportName())
                .build();
        processedEventService.save(processedEvent);
        log.info("Processed message with ID: {}", messageId);
    }
}


