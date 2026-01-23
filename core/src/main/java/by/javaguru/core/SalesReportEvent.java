package by.javaguru.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportEvent {
    private Long orderId;
    private String reportName;
    private LocalDate rangeStart;
    private LocalDate rangeEnd;
    private String userEmail;
    private List<CalculatedSalesReportEvent> salesReportEvents;
    private LocalDateTime reportDate;
}