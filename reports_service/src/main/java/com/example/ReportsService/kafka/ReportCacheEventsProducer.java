package com.example.ReportsService.kafka;


import by.javaguru.core.ReadyReportEvent;
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
public class ReportCacheEventsProducer {

    private final KafkaTemplate<String, ReadyReportEvent> kafkaTemplate;

    @Value("${spring.kafka.producer.cache-reports-events.name}")
    private String topicName;

    public SendResult<String, ReadyReportEvent> sendCacheEvent(ReadyReportEvent readyReportEvent)
            throws ExecutionException, InterruptedException {
        String eventId = String.valueOf(readyReportEvent.getOrderId());
        ProducerRecord<String, ReadyReportEvent> orderRecord =
                new ProducerRecord<>(topicName, eventId, readyReportEvent);
        orderRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        try {
            SendResult<String, ReadyReportEvent> result = kafkaTemplate.send(orderRecord).get();
            log.info("Topic: {}", result.getRecordMetadata().topic());
            log.info("Partition: {}", result.getRecordMetadata().partition());
            log.info("Offset: {}", result.getRecordMetadata().offset());
            return result;
        } catch (Exception e) {
            log.error("Failed to send message to Kafka topic: {}", topicName, e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
