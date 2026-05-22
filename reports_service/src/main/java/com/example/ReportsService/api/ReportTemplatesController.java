package com.example.ReportsService.api;


import com.example.ReportsService.usecasses.ReportTemplateService;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/cvs/reportTemplates")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ReportTemplatesController {

    private final ReportTemplateService reportTemplateService;

    @GetMapping
    public ResponseEntity<Page<ReportTemplateDto>> getAllReportTemplates(
            @PageableDefault(size = 20, sort = "reportId") Pageable pageable) {
        log.info("Request for report templates page: {}", pageable.getPageNumber());
        Page<ReportTemplateDto> templates = reportTemplateService.getAllReportTemplates(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(templates);
    }
}


