package reporting_service.log;


import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.SalesReportEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UtilLogging {

    public void sendSalesReportEvent
            (ReadyReportEvent readySalesReportEvent) {
        log.info("Send simple sales report for: {}",
                readySalesReportEvent.getOrderId()
                + " " + readySalesReportEvent.getReportName()
                + " " + readySalesReportEvent.getRangeStart()
                + " " + readySalesReportEvent.getRangeEnd()
                + " " + readySalesReportEvent.getUserEmail()
                + " " + readySalesReportEvent.getFilePath());
    }
}
