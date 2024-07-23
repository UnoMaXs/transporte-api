package com.example.backendtransporteapi.controller;

import com.example.backendtransporteapi.model.DailySummary;
import com.example.backendtransporteapi.service.SummaryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/summaries")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Void> generateSummary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        summaryService.generateDailySummary(date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DailySummary>> getAllSummaries() {
        List<DailySummary> summaries = summaryService.getAllSummaries();
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }
}
