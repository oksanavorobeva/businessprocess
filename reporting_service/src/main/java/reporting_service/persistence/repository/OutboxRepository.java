package reporting_service.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reporting_service.persistence.model.Outbox;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    @Query(value = "SELECT * FROM outbox FOR UPDATE", nativeQuery = true)
    List<Outbox> selectForProcessingEntities();
}
