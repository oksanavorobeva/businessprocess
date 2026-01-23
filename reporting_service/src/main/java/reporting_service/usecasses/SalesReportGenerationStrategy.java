package reporting_service.usecasses;

import by.javaguru.core.CalculatedSalesReportEvent;
import by.javaguru.core.SalesReportEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SalesReportGenerationStrategy implements ReportGenerationStrategy<CalculatedSalesReportEvent, SalesReportEvent> {

    @Override
    public void generateReport(String sourceFileName, String destFileName, SalesReportEvent salesReportEvent, List<CalculatedSalesReportEvent> data) throws JRException {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceFileName);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
            Map<String, Object> parameters = createReportParameters(salesReportEvent);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);

        } catch (JRException e) {
            throw new JRException("Ошибка при компиляции и заполнении отчета: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> createReportParameters(SalesReportEvent salesReportEvent) {
        Map<String, Object> parameters = new HashMap<>();
        if (salesReportEvent != null) {
            parameters.put("reportName", salesReportEvent.getReportName());
            parameters.put("rangeStart", salesReportEvent.getRangeStart());
            parameters.put("rangeEnd", salesReportEvent.getRangeEnd());
        }
        return parameters;
    }
}