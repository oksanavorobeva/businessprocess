package reporting_service.usecasses;


import by.javaguru.core.ReadyReportEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reporting_service.persistence.model.Outbox;
import reporting_service.persistence.model.OutboxType;
import reporting_service.persistence.repository.OutboxRepository;
import reporting_service.processor.OutboxRecordProcessor;
import reporting_service.usecasses.dto.OutboxDTO;
import reporting_service.usecasses.mapper.OutboxMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final OutboxRecordProcessor recordProcessor;
    private final OutboxMapper outboxMapper;
    private final ObjectMapper objectMapper;
    private OutboxType outboxType;

    @Transactional
    public void processAll() {
        List<Outbox> entities = outboxRepository.selectForProcessingEntities();
        log.info("Found {} outbox records to process", entities.size());
        entities.stream()
                .map(outboxMapper::fromEntityToDto)
                .forEach(this::process);
        log.info("Processed all outboxes");
    }

    @SneakyThrows
    private void process(OutboxDTO outboxRecord) {
        recordProcessor.process(outboxRecord);
        log.info("Processed outbox {}", outboxRecord);
        Outbox outbox = outboxMapper.fromDtoToEntity(outboxRecord);
        Optional<Outbox> existingOutbox = outboxRepository.findById(outbox.getId());
        outboxRepository.delete(outbox);
    }

    @SneakyThrows
    public void readyReportCreatedEvent(ReadyReportEvent readySalesReportEvent) {
        String payload = objectMapper.writeValueAsString(readySalesReportEvent);
        OutboxDTO outboxDTO = new OutboxDTO();
        outboxDTO.setPayload(payload);
        outboxDTO.setType(outboxType.OrderEvent);
        outboxDTO.setCreated(LocalDateTime.now());
        Outbox outbox = outboxMapper.fromDtoToEntity(outboxDTO);
        outboxRepository.save(outbox);
        log.info("Created outbox: {}", outbox.toString());
    }
}
