package com.example.ReportsService.api;



import com.example.ReportsService.usecasses.ReportTemplateService;
import com.example.ReportsService.usecasses.dto.ReportTemplateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/cvs/reportTemplates")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ReportTemplatesController {

    private final ReportTemplateService reportTemplateService;

    @GetMapping
    public ResponseEntity<List<ReportTemplateDto>> getAllReportTemplate() {
        List<ReportTemplateDto> orders = reportTemplateService.getAllReportTemplates();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orders);
    }
}
