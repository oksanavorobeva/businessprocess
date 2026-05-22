package reporting_service.processor;


import reporting_service.usecasses.dto.OutboxDTO;

public interface OutboxRecordProcessor {
    void process(OutboxDTO outboxRecord);
}
