package com.example.ReportsService;


import com.example.ReportsService.persistence.model.Outbox;
import com.example.ReportsService.persistence.model.OutboxType;
import com.example.ReportsService.usecasses.dto.OutboxDTO;

import java.time.LocalDateTime;

public class OutboxTestData {

    public static String payload = "{\"test\": \"data\"}";

    public static OutboxDTO createdOutboxDTO() {
        Long id = 123L;
        String payload = "{\"test\": \"data\"}";
        LocalDateTime created = LocalDateTime.now();
        String name = "CacheEvent";
        OutboxType type = OutboxType.valueOf(name);
        return new OutboxDTO(
                id,
                payload,
                created,
                type);
    }

    public static Outbox createdOutbox() {
        Long id = 123L;
        String payload = "{\"test\": \"data\"}";
        LocalDateTime created = LocalDateTime.now();
        String name = "CacheEvent";
        OutboxType type = OutboxType.valueOf(name);
        return new Outbox(
                id,
                payload,
                created,
                type);
    }
}
