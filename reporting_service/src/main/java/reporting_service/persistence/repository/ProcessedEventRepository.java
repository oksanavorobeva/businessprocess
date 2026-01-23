package reporting_service.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import reporting_service.persistence.model.ProcessedEventEntity;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, Long> {
    ProcessedEventEntity findByMessageId(String messageId);
}
