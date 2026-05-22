package com.example.sales_metrics_worker.scheduler;



import com.example.sales_metrics_worker.usecasses.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OutboxScheduler {
    private final OutboxService outboxService;

    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(name = "processOutboxMessages", lockAtLeastFor = "2s", lockAtMostFor = "10s")
    @Transactional
    public void run() {
        log.info("Running outbox service");
        outboxService.processAll();
        log.info("Finished running outbox service");
    }
}

