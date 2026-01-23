package com.example.sales_metrics_worker.persistence.repository;


import com.example.sales_metrics_worker.persistence.model.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, Long> {
    ProcessedEventEntity findByMessageId(String messageId);
}
