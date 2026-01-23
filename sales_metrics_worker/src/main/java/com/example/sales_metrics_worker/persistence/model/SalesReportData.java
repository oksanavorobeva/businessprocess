package com.example.sales_metrics_worker.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalesReportData {
    @Id
    @GeneratedValue
    private Long productId;
    private String productName;
    private LocalDate saleDate;
    private Integer quantitySold;
    private BigDecimal pricePerUnit;
}
