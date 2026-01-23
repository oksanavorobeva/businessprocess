package by.javaguru.core;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculatedSalesReportEvent {
    private String productName;
    private Integer totalQuantitySold;
    private BigDecimal totalPrice;
}
