package com.example.ReportsService.kafka;


import by.javaguru.core.ReportCreatedEvent;
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

    private final KafkaTemplate<String, ReportCreatedEvent> kafkaTemplate;

    @Value("${spring.kafka.producer.cache-reports-events.name}")
    private String topicName;

    public SendResult<String, ReportCreatedEvent> sendOrderEvent(ReportCreatedEvent reportCreatedEvent, String topicNames)
            throws ExecutionException, InterruptedException {
        String eventId = String.valueOf(reportCreatedEvent.getOrderId());
        ProducerRecord<String, ReportCreatedEvent> orderRecord =
                new ProducerRecord<>(topicNames, eventId, reportCreatedEvent);
        orderRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        try {
            SendResult<String, ReportCreatedEvent> result = kafkaTemplate.send(orderRecord).get();
            log.info("Topic: {}", result.getRecordMetadata().topic());
            log.info("Partition: {}", result.getRecordMetadata().partition());
            log.info("Offset: {}", result.getRecordMetadata().offset());
            return result;
        } catch (Exception e) {
            log.error("Failed to send message to Kafka topic: {}", topicNames, e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
