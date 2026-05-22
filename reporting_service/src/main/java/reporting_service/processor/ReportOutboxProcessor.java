package reporting_service.processor;



import by.javaguru.core.ReadyReportEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reporting_service.kafka.ReportEmailEventsProducer;
import reporting_service.kafka.ReportReadylEventsProducer;
import reporting_service.log.UtilLogging;
import reporting_service.usecasses.dto.OutboxDTO;


@Slf4j
@RequiredArgsConstructor
@Component
public class ReportOutboxProcessor implements OutboxRecordProcessor {

    private final ReportReadylEventsProducer reportReadylEventsProducer;
    private final ReportEmailEventsProducer reportEmailEventsProducer;
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
        ReadyReportEvent readyReportEvent = objectMapper.readValue(payload, ReadyReportEvent.class);
        reportReadylEventsProducer.sendOrderEvent(readyReportEvent);
        reportEmailEventsProducer.sendOrderEvent(readyReportEvent);
        utilLogging.sendSalesReportEvent(readyReportEvent);

        log.warn("Unknown outbox type: {}", outboxDTO.getType());
    }
}
