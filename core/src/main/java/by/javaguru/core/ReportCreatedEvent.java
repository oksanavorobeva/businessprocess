package by.javaguru.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportCreatedEvent {
    private Long orderId;
    private String reportName;
    private LocalDate rangeStart;
    private LocalDate rangeEnd;
    private String userEmail;
}