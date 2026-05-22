package com.example.sales_metrics_worker.persistence.model;


import jakarta.persistence.*;
import lombok.*;


@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "processed_events_sales_metrics_worker")
public class ProcessedEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "message_id")
    private String messageId;

    @Column(nullable = false, name = "report_event_id")
    private Long reportEventId;

    @Column(nullable = false, name = "report_name")
    private String reportName;
}
