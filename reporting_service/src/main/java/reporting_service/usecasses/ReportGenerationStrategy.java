package reporting_service.usecasses;

import net.sf.jasperreports.engine.JRException;

import java.util.List;


public interface ReportGenerationStrategy<T, U> {
    void generateReport(String sourceFileName, String destFileName, U reportParams, List<T> data) throws JRException;
}
