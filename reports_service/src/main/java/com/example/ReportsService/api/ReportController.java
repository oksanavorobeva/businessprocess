package com.example.ReportsService.api;


import com.example.ReportsService.usecasses.ReportService;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/cvs/report")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDto> save(
            @Valid @RequestBody ReportRequestDto request
    ) {
        log.info("Saving report {}", request);
        ReportDto responseDto = reportService.createReport(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ReportDto>> getAllReport() {
        List<ReportDto> orders = reportService.getAllReports();
        log.info("Get all report {}", orders);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getById(
            @PathVariable(name = "id") Long id
    ) {
        ReportDto dto = reportService.getReportById(id);
        log.info("Get report {}", dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto);
    }
}
