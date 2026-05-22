package com.example.ReportsService.api;


import com.example.ReportsService.usecasses.ReportService;
import com.example.ReportsService.usecasses.dto.ReportDto;
import com.example.ReportsService.usecasses.dto.ReportRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/cvs/report")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String save(
            @Valid @RequestBody ReportRequestDto request
    ) {
        log.info("Saving report {}", request);
        reportService.createReport(request);
        return "";
    }

    @GetMapping
    public ResponseEntity<Page<ReportDto>> getAllReports(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Request to get a page of reports: page={}, size={}",
                pageable.getPageNumber(), pageable.getPageSize());
        Page<ReportDto> page = reportService.getAllReports(pageable);
        log.info("Fetched {} reports out of total {}",
                page.getNumberOfElements(), page.getTotalElements());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(page);
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
