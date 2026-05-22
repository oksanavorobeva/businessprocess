package com.example.sales_metrics_worker.kafka;


import by.javaguru.core.ReportCreatedEvent;
import by.javaguru.core.exception.NonRetryableException;
import com.example.sales_metrics_worker.api.exception.NoActiveReportException;
import com.example.sales_metrics_worker.persistence.model.ProcessedEventEntity;
import com.example.sales_metrics_worker.persistence.model.ReportConfigurationEntity;
import com.example.sales_metrics_worker.usecasses.ProcessedEventService;
import com.example.sales_metrics_worker.usecasses.ReportConfigurationService;
import com.example.sales_metrics_worker.usecasses.ReportStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;

//TODO:Название топиков и прочих свойств кафки лучше вынести в  yaml
@Component
@KafkaListener(topics = "sales_reports-events-topic", containerFactory = "kafkaListenerContainerFactory",
        groupId = "sales_report-created-events")
@Slf4j
@RequiredArgsConstructor
public class SalesReportsEventHandler {

    private final ProcessedEventService processedEventService;
    private final ReportConfigurationService reportConfigurationService;
    private final Map<String, ReportStrategy> reportStrategies;

    @Transactional(value = "transactionManager")
    @KafkaHandler
    public void handler(@Payload ReportCreatedEvent reportCreatedEvent,
                        @Header("messageId") String messageId,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        log.info("Received message with ID: {}, topic: {}, messageKey: {}", messageId, topic, messageKey);

        ProcessedEventEntity processedEventEntity = processedEventService.findByMessageId(messageId);

        if (processedEventEntity != null) {
            log.info("Duplicated message with ID: {}", messageId);
            return;
        }

        try {
            ReportConfigurationEntity reportConfiguration =
                    reportConfigurationService.getReportName(reportCreatedEvent.getReportName());

            if (reportConfiguration == null || !reportConfiguration.getIsActive()) {
                throw new NoActiveReportException
                        ("No active report configuration found for report name: " + reportCreatedEvent.getReportName());
            }

            String strategyBeanName = reportConfiguration.getStrategyBeanName();
            ReportStrategy strategy = reportStrategies.get(strategyBeanName);
            strategy.generateReport(reportCreatedEvent);

            ProcessedEventEntity processedEvent = ProcessedEventEntity.builder()
                    .withMessageId(messageId)
                    .withReportEventId(reportCreatedEvent.getOrderId())
                    .withReportName(reportCreatedEvent.getReportName())
                    .build();
            processedEventService.save(processedEvent);
            log.info("Processed message with ID: {}", messageId);

        } catch (NoActiveReportException e) {
            log.warn("No active report - skipping retry: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error saving processed event: {}", e.getMessage());
            throw new NonRetryableException(e);
        }
    }
}

