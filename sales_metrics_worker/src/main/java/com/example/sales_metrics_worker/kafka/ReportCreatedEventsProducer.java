package com.example.sales_metrics_worker.kafka;


import by.javaguru.core.SalesReportEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;


import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportCreatedEventsProducer {

    @Value("${spring.kafka.producer.sales_reports-created-events.name}")
    private String topicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SendResult<String, Object> sendOrderEvent(SalesReportEvent salesReportEvent)
            throws ExecutionException, InterruptedException {

        String eventId = String.valueOf(salesReportEvent.getOrderId());

        ProducerRecord<String, Object> orderRecord =
                new ProducerRecord<>(topicName, eventId, salesReportEvent);
        orderRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        SendResult<String, Object> result = kafkaTemplate.send
                (orderRecord).get();

        log.info("Topic: {}", result.getRecordMetadata().topic());
        log.info("Partition: {}", result.getRecordMetadata().partition());
        log.info("Offset: {}", result.getRecordMetadata().offset());

        return result;
    }
}
