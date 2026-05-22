package reporting_service.kafka;


import by.javaguru.core.SalesReportEvent;
import by.javaguru.core.exception.NonRetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reporting_service.persistence.model.ProcessedEventEntity;
import reporting_service.usecasses.ProcessedEventService;
import reporting_service.usecasses.SalesReportEventHandler;


@Component
@KafkaListener(topics = "sales_reports-created-events-topic", containerFactory = "kafkaListenerContainerFactory",
        groupId = "reporting-events")
@Slf4j
@RequiredArgsConstructor
public class ReportsEventHandler {

    private final ProcessedEventService processedEventService;

    private final SalesReportEventHandler salesReportEventHandler;

    @Transactional
    @KafkaHandler
    public void handler(@Payload SalesReportEvent salesReportEvent,
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
        log.info("Processed event with ID: {}", salesReportEvent.getOrderId());
        log.info("Processed event with ID: {}", salesReportEvent.getReportDate());

        try {
            salesReportEventHandler.handleDtoAndGenerateReport(salesReportEvent);

            ProcessedEventEntity processedEvent = ProcessedEventEntity.builder()
                    .withMessageId(messageId)
                    .withReportEventId(salesReportEvent.getOrderId())
                    .withReportName(salesReportEvent.getReportName())
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


