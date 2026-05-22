package com.example.ReportsService.processor;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import com.example.ReportsService.kafka.ReportCacheEventsProducer;
import com.example.ReportsService.kafka.ReportCreatedEventsProducer;
import com.example.ReportsService.log.UtilLogging;
import com.example.ReportsService.usecasses.dto.OutboxDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class ReportOutboxProcessor implements OutboxRecordProcessor {

    private final ReportCreatedEventsProducer reportCreatedEventsProducer;
    private final ReportCacheEventsProducer reportCacheEventsProducer;
    private final ObjectMapper objectMapper;
    private final UtilLogging utilLogging;

    @SneakyThrows
    @Override
    public void process(OutboxDTO outboxDTO) {

        String payload = outboxDTO.getPayload();
        if (payload == null || payload.isBlank()) {
            log.error("Payload is empty for outbox record: {}", outboxDTO.getId());
            return;
        }

        switch (outboxDTO.getType()) {
            case OrderEvent: // Для ReportCreatedEvent
                ReportCreatedEvent createdEvent = objectMapper.readValue(payload, ReportCreatedEvent.class);
                reportCreatedEventsProducer.sendOrderEvent(createdEvent);
                utilLogging.sendOrderEvent(createdEvent);
                break;

            case CacheEvent: // Для ReadyReportEvent
                ReadyReportEvent readyEvent = objectMapper.readValue(payload, ReadyReportEvent.class);
                reportCacheEventsProducer.sendCacheEvent(readyEvent);
                utilLogging.sendCacheEvent(readyEvent);
                break;

            default:
                log.warn("Unknown outbox type: {}", outboxDTO.getType());
        }
    }
}