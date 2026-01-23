package com.example.ReportsService.usecasses.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {

    @NotNull(message = "Report Id cannot be null")
    @Positive(message = "Report Id must be a positive number")
    private Long reportId;

    @Email
    @NotNull
    private String userEmail;

    private LocalDate rangeStart;

    private LocalDate rangeEnd;
}
