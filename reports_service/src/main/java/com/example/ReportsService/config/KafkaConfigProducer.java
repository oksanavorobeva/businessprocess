package com.example.ReportsService.config;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConfigProducer {

    //Вопрос
    //Пришлось создать несколько бинов продюсера и темплейта.
    //Так как разные Дто.
    //По другому не получилось.
    //На сколько правильно?

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.properties.delivery-timeout-ms}")
    private int deliveryTimeoutMs;

    @Value("${spring.kafka.producer.properties.linger-ms}")
    private int lingerMs;

    @Value("${spring.kafka.producer.properties.request-timeout-ms}")
    private int requestTimeoutMs;

    @Value("${spring.kafka.producer.properties.enable-idempotence}")
    private boolean enableIdempotence;

    @Value("${spring.kafka.producer.properties.max-in-flight-requests-per-connection}")
    private int maxInFlightRequestsPerConnection;

    @Value("${spring.kafka.producer.transaction-id-prefix}")
    private String transactionIdPrefix;

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs);
        config.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
        config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionIdPrefix);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    ProducerFactory<String, ReadyReportEvent> producerFactoryReadyReportEvent() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs);
        config.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
        config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionIdPrefix);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    ProducerFactory<String, ReportCreatedEvent> producerFactoryReportCreatedEvent() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs);
        config.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
        config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionIdPrefix);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<String, Object>(producerFactory);
    }

    @Bean
    KafkaTemplate<String, ReportCreatedEvent> reportCreatedEventKafkaTemplate(ProducerFactory<String, ReportCreatedEvent> reportCreatedEventProducerFactory) {
        return new KafkaTemplate<String, ReportCreatedEvent>(reportCreatedEventProducerFactory);
    }

    @Bean
    KafkaTemplate<String, ReadyReportEvent> readyReportEventKafkaTemplate(ProducerFactory<String, ReadyReportEvent> readyReportEventProducerFactory) {
        return new KafkaTemplate<String, ReadyReportEvent>(readyReportEventProducerFactory);
    }

    @Bean
    KafkaTransactionManager<String, Object> kafkaTransactionManager(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTransactionManager<String, Object>(producerFactory);
    }

    @Bean("transactionManager")
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    NewTopic salesReportsEventsTopic(
            @Value("${spring.kafka.producer.sales_reports-events.name}") String topicName,
            @Value("${spring.kafka.producer.sales_reports-events.partitions}") int partitions,
            @Value("${spring.kafka.producer.sales_reports-events.replication-factor}") int replicationFactor,
            @Value("${spring.kafka.producer.sales_reports-events.min-insync-replicas}") int minInsyncReplicas) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicationFactor)
                .configs(Map.of("min.insync.replicas", String.valueOf(minInsyncReplicas)))
                .build();
    }

    @Bean
    NewTopic customerReportsEventsTopic(
            @Value("${spring.kafka.producer.customer_reports-events.name}") String topicName,
            @Value("${spring.kafka.producer.customer_reports-events.partitions}") int partitions,
            @Value("${spring.kafka.producer.customer_reports-events.min-insync-replicas}") int replicationFactor,
            @Value("${spring.kafka.producer.customer_reports-events.min-insync-replicas}") int minInsyncReplicas) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicationFactor)
                .configs(Map.of("min.insync.replicas", String.valueOf(minInsyncReplicas)))
                .build();
    }

    @Bean
    NewTopic returnsReportsEventsTopic(
            @Value("${spring.kafka.producer.returns_reports-events.name}") String topicName,
            @Value("${spring.kafka.producer.returns_reports-events.partitions}") int partitions,
            @Value("${spring.kafka.producer.returns_reports-events.replication-factor}") int replicationFactor,
            @Value("${spring.kafka.producer.customer_reports-events.min-insync-replicas}") int minInsyncReplicas) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicationFactor)
                .configs(Map.of("min.insync.replicas", String.valueOf(minInsyncReplicas)))
                .build();
    }

    @Bean
    NewTopic cacheReportsEventsTopic(
            @Value("${spring.kafka.producer.cache-reports-events.name}") String topicName,
            @Value("${spring.kafka.producer.cache-reports-events.partitions}") int partitions,
            @Value("${spring.kafka.producer.cache-reports-events.replication-factor}") int replicationFactor,
            @Value("${spring.kafka.producer.cache-reports-events.min-insync-replicas}") int minInsyncReplicas) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicationFactor)
                .configs(Map.of("min.insync.replicas", String.valueOf(minInsyncReplicas)))
                .build();
    }
}
