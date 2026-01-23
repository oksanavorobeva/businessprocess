package reporting_service.usecasses;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reporting_service.persistence.model.ProcessedEventEntity;
import reporting_service.persistence.repository.ProcessedEventRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessedEventService {

    private final ProcessedEventRepository processedEventRepository;

    public ProcessedEventEntity findByMessageId(String messageId) {
        return processedEventRepository.findByMessageId(messageId);
    }

    public void save(ProcessedEventEntity processedEvent) {
        processedEventRepository.save(processedEvent);
    }
}
