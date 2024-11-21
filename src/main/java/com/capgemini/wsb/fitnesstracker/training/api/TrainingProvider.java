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
}

