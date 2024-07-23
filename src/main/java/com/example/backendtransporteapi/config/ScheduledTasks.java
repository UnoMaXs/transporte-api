package com.example.backendtransporteapi.config;

import com.example.backendtransporteapi.service.SummaryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduledTasks {

    private final SummaryService summaryService;

    public ScheduledTasks(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void generateDailySummary() {
        LocalDate today = LocalDate.now().plusDays(1);
        System.out.println("Generating daily summary for " + today);
        summaryService.generateDailySummary(today);
    }
}

