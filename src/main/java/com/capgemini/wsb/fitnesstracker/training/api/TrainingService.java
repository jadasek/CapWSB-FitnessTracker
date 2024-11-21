package com.capgemini.wsb.fitnesstracker.training.api;


/**
 * Interface for managing training data.
 *
 * Provides methods for creating and updating training information.
 */
public interface TrainingService {

    /**
     * Create a new training.
     *
     * Allows the creation of a new training session with the details provided in the DTO.
     *
     * @param trainingDto The {@link TrainingDto} containing the training details.
     * @return The created {@link TrainingDto} with assigned properties such as ID.
     */
    TrainingDto createTraining(TrainingDto trainingDto);

    /**
     * Update an existing training.
     *
     * Updates the details of an existing training identified by its ID.
     *
     * @param id          The ID of the training to be updated.
     * @param trainingDto The {@link TrainingDto} containing the updated details.
     * @return The updated {@link TrainingDto}.
     */
    TrainingDto updateTraining(Long id, TrainingDto trainingDto);
}
