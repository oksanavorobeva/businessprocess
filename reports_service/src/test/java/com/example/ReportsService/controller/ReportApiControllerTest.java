package com.example.ReportsService.controller;


import com.example.ReportsService.api.ReportController;
import com.example.ReportsService.usecasses.ReportService;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.example.ReportsService.ReportTestData.*;
import static com.example.ReportsService.ReportTestData.createReportDto;
import static com.example.ReportsService.ReportTestData.createRequestDto;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ReportController.class)
public class ReportApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReportService reportService;

    @Test
    void shouldReturn200WhenReportSaved() throws Exception {
        ReportRequestDto requestDto = createRequestDto();

        mockMvc.perform(post("/api/v1/cvs/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andReturn();

        verify(reportService, times(1)).createReport(
                argThat(dto ->
                        dto.getReportId().equals(requestDto.getReportId()) &&
                        dto.getRangeEnd().equals(requestDto.getRangeEnd()) &&
                        dto.getRangeStart().equals(requestDto.getRangeStart()) &&
                        dto.getUserEmail().equals(requestDto.getUserEmail())
                )
        );
    }

    @Test
    void shouldReturnPageOfReports() throws Exception {
        ReportDto dto1 = createReportDto();
        ReportDto dto2 = createReportDto();

        List<ReportDto> reportList = Arrays.asList(dto1, dto2);
        Page<ReportDto> reportPage =
                new PageImpl<>(reportList, PageRequest.of(0, 10), reportList.size());

        when(reportService.getAllReports(any(Pageable.class))).thenReturn(reportPage);

        mockMvc.perform(get("/api/v1/cvs/report")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(dto1.getId()))
                .andExpect(jsonPath("$.content[1].id").value(dto2.getId()))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0));
    }

    @Test
    void shouldReturnReportById() throws Exception {
        ReportDto dto = createReportDto();
        when(reportService.getReportById(id)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/cvs/report/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(dto.getId()));

        verify(reportService, times(1))
                .getReportById(id);
    }
}
