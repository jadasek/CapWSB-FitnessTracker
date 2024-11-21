package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingReportService;

@Component
public class MonthlyTrainingReportTask {

    private final TrainingReportService trainingReportService;

    public MonthlyTrainingReportTask(TrainingReportService trainingReportService) {
        this.trainingReportService = trainingReportService;
    }

    /**
     * Task to generate and send training reports monthly.
     */
    @Scheduled(cron = "0 0 0 1 * ?") 
    public void generateAndSendReports() {
        trainingReportService.generateAndSendMonthlyReports();
    }
}
