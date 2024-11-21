package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Retrieves a list of trainings associated with a specific user ID.
     *
     * @param userId the ID of the user whose trainings are to be retrieved
     * @return a list of Trainings related to the specified user ID
     */
    List<Training> findByUserId(Long userId);

    /**
     * Retrieves a list of trainings that have an end time after the specified date.
     *
     * @param afterTime the date to compare against the end time of the trainings
     * @return a list of Trainings that end after the specified time
     */
    List<Training> findByEndTimeAfter(Date afterTime);

    /**
     * Retrieves a list of trainings that are associated with a specific activity type.
     *
     * @param activityType the activity type to filter trainings by
     * @return a list of Trainings associated with the specified activity type
     */
    List<Training> findByActivityType(ActivityType activityType);
}
