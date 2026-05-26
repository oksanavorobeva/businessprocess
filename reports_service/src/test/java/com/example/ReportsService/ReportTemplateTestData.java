package com.example.ReportsService;


import com.example.ReportsService.persistence.model.ReportTemplate;
import com.example.ReportsService.persistence.model.Topic;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;


public class ReportTemplateTestData {

    public static final String reportName = "testReport";

    public static ReportTemplate createReportTemplate() {
        Long reportId = 123L;
        String reportName = "testReport";
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setTopicName("testTopicName");
        return new ReportTemplate(
                reportId,
                reportName,
                topic
        );
    }

    public static ReportTemplateDto createReportTemplateDto() {
        Long reportId = 123L;
        String reportName = "testReport";
        Topic topic = new Topic();
        topic.setId(1L);
        return new ReportTemplateDto(
                reportId,
                reportName,
                topic
        );
    }
}
