package com.example.ReportsService;


import com.example.ReportsService.usecasses.dto.ReportRequestDto;

import java.time.LocalDate;

public class ReportTestData {


    public static ReportRequestDto createRequestDto() {
        Long reportId = 123L;
        String userEmail = "test@example.com";
        LocalDate rangeStart = LocalDate.of(2023, 1, 1);
        LocalDate rangeEnd = LocalDate.of(2023, 1, 31);
        return new ReportRequestDto(
               reportId,
                userEmail,
                rangeStart,
                rangeEnd
        );
    }

}