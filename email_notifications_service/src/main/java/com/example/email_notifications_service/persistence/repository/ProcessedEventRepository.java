package com.example.email_notifications_service.persistence.repository;


import com.example.email_notifications_service.persistence.model.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, Long> {
    ProcessedEventEntity findByMessageId(String messageId);
}
