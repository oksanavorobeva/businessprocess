package com.example.sales_metrics_worker.processor;



import by.javaguru.core.SalesReportEvent;
import com.example.sales_metrics_worker.kafka.ReportCreatedEventsProducer;
import com.example.sales_metrics_worker.log.UtilLogging;
import com.example.sales_metrics_worker.usecasses.dto.OutboxDTO;
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
        SalesReportEvent salesReportEvent = objectMapper.readValue(payload, SalesReportEvent.class);
        reportCreatedEventsProducer.sendOrderEvent(salesReportEvent);
        utilLogging.sendSalesReportEvent(salesReportEvent);

        log.warn("Unknown outbox type: {}", outboxDTO.getType());
    }
}
