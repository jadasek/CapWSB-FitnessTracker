package com.capgemini.wsb.fitnesstracker.training.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


// Zadanie 3.2 czegoś brakuje...
@Service
public class TrainingReportService {

    private final TrainingProvider trainingProvider;
    private final UserProvider userProvider;
    private final JavaMailSender mailSender;

    public TrainingReportService(@Lazy TrainingProvider trainingProvider, UserProvider userProvider, JavaMailSender mailSender) {
        this.trainingProvider = trainingProvider;
        this.userProvider = userProvider;
        this.mailSender = mailSender;
    }
    /**
     * Generate and send monthly training reports for all users.
     */
    public void generateAndSendMonthlyReports() {
        // Get all users
        List<User> users = userProvider.findAllUsers();

        // Generate and send report for each user
        users.forEach(user -> {
            // Get training count for the current month
            // Get the current date

            Date currentDate = new Date();

            // Calculate the start of the month
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.set(Calendar.DAY_OF_MONTH, 1);  // Set to the first day of the month
            Date startDate = calendar.getTime();

            // Calculate the end of the month
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 0);  // Set to the last day of the previous month
            Date endDate = calendar.getTime();

            // Now call the method with the correct parameters
            int trainingCount = trainingProvider.getMonthlyTrainingCount(user.getId(), startDate, endDate);

            // Generate the email content
            String report = generateReport(user.getFirstName(), trainingCount);
            // Send the email
            sendEmail(user.getEmail(), report);
        });
    }

    /**
     * Generate the email content for the training report.
     *
     * @param userName      The name of the user.
     * @param trainingCount The number of trainings the user completed.
     * @return The email content.
     */
    private String generateReport(String userName, int trainingCount) {
        return String.format("Hello %s,\n\nYou completed %d trainings this month.\n\nBest regards,\nFitness Tracker Team",
                userName, trainingCount);
    }

    /**
     * Send the report via email.
     *
     * @param toEmail The recipient's email address.
     * @param content The content of the email.
     */
    private void sendEmail(String toEmail, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Monthly Training Report");
            helper.setText(content, false);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
