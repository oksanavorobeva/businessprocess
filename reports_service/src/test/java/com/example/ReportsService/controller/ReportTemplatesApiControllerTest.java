package com.example.ReportsService.controller;


import com.example.ReportsService.api.ReportTemplatesController;
import com.example.ReportsService.usecasses.ReportTemplateService;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;


import static com.example.ReportsService.ReportTemplateTestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ReportTemplatesController.class)
public class ReportTemplatesApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportTemplateService reportTemplateService;

    @Test
    void shouldReturnPageOfReportsTemplate() throws Exception {
        ReportTemplateDto report1 = createReportTemplateDto();
        ReportTemplateDto report2 = createReportTemplateDto();

        List<ReportTemplateDto> reportList = Arrays.asList(report1, report2);
        Page<ReportTemplateDto> reportPage =
                new PageImpl<>(reportList, PageRequest.of(0, 10), reportList.size());

        when(reportTemplateService.getAllReportTemplates(any(Pageable.class))).thenReturn(reportPage);

        mockMvc.perform(get("/api/v1/cvs/reportTemplates")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "reportId,desc")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].reportId").value(report1.getReportId()))
                .andExpect(jsonPath("$.content[1].reportId").value(report2.getReportId()))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0));
    }
}
