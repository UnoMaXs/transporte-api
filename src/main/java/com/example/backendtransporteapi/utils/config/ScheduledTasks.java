package com.example.backendtransporteapi.utils.config;

import com.example.backendtransporteapi.service.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final SummaryService summaryService;

    public ScheduledTasks(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void generateDailySummary() {
        LocalDate today = LocalDate.now();
        logger.info("Generating daily summary for {}", today);

        try {
            summaryService.generateDailySummary(today);
        } catch (Exception e) {
            logger.error("Error generating daily summary for {}", today, e);
        }
    }
}
