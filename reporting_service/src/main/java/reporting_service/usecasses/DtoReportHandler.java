package reporting_service.usecasses;

public interface DtoReportHandler<T> {
    void handleDtoAndGenerateReport(T dto);
}