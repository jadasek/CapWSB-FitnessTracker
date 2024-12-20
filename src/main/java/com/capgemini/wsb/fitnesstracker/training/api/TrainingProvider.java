package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Date;
import java.util.List;

/**
 * Interface for retrieving training data.
 *
 * Provides methods for fetching training-related information from the data source.
 */
public interface TrainingProvider {

    /**
     * Retrieve all available trainings.
     *
     * @return A list of {@link TrainingDto} representing all trainings.
     */
    List<TrainingDto> getAllTrainings();

    /**
     * Retrieve trainings by user ID.
     *
     * Fetches all trainings associated with a specific user.
     *
     * @param userId The ID of the user whose trainings are to be retrieved.
     * @return A list of {@link TrainingDto} representing the user's trainings.
     */
    List<TrainingDto> getTrainingsByUserId(Long userId);

    /**
     * Retrieve finished trainings after a specified date.
     *
     * Fetches all trainings that were completed after the given date.
     *
     * @param afterTime The date after which finished trainings are to be retrieved.
     * @return A list of {@link TrainingDto} representing the finished trainings.
     */
    List<TrainingDto> getFinishedTrainingsAfter(Date afterTime);

    /**
     * Retrieve trainings by activity type.
     *
     * Fetches all trainings that match the specified activity type.
     *
     * @param activityType The type of activity to filter trainings by.
     * @return A list of {@link TrainingDto} representing trainings of the specified activity type.
     */
    List<TrainingDto> getTrainingsByActivityType(ActivityType activityType);


    /**
     * Get the count of trainings for a specific user within a given date range.
     *
     * This method calculates the number of training sessions a specific user has completed
     * within the specified date range (from startDate to endDate). It helps in tracking the
     * user's training activity over a certain period.
     *
     * @param userId The ID of the user whose training count is to be retrieved.
     * @param startDate The start date of the period to check for completed trainings.
     * @param endDate The end date of the period to check for completed trainings.
     * @return An integer representing the number of training sessions completed by the user
     *         within the specified date range.
     */
    int getMonthlyTrainingCount(Long userId, Date startDate, Date endDate);
}

