package com.example.ReportsService.processor;


import com.example.ReportsService.usecasses.dto.OutboxDTO;

public interface OutboxRecordProcessor {
    void process(OutboxDTO outboxRecord);
}
