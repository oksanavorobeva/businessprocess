package com.example.sales_metrics_worker.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "outboxId")
@Table(name = "outbox")
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb")
    private String payload;

    @Column(name="created")
    private LocalDateTime created;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OutboxType type;
}
