package com.example.sales_metrics_worker.processor;


import com.example.sales_metrics_worker.usecasses.dto.OutboxDTO;

public interface OutboxRecordProcessor {
    void process(OutboxDTO outboxRecord);
}
