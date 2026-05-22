package com.example.ReportsService.persistence.repository;

import com.example.ReportsService.persistence.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    @Query(value = "SELECT * FROM outbox FOR UPDATE", nativeQuery = true)
    List<Outbox> selectForProcessingEntities();
}
