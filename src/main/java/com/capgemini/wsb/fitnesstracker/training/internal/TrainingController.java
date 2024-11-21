package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    private final TrainingProvider trainingProvider;
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingProvider trainingProvider, TrainingService trainingService) {
        this.trainingProvider = trainingProvider;
        this.trainingService = trainingService;
    }

    /**
     * Endpoint to retrieve all available trainings.
     *
     * This method fetches a list of all trainings available in the system.
     *
     * @return A list of {@link TrainingDto} representing all trainings.
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingProvider.getAllTrainings();
    }

    /**
     * Endpoint to retrieve trainings by user ID.
     *
     * This method fetches all trainings associated with a specific user, identified by their user ID.
     *
     * @param userId The ID of the user whose trainings are being retrieved.
     * @return A list of {@link TrainingDto} representing the user's trainings.
     */
    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingProvider.getTrainingsByUserId(userId);
    }

    /**
     * Endpoint to retrieve finished trainings after a specific date.
     *
     * This method fetches all trainings that have been completed after the given date.
     *
     * @param afterTime The date after which finished trainings are to be retrieved.
     * @return A list of {@link TrainingDto} representing finished trainings after the specified date.
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainingsAfter(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date afterTime) {
        return trainingProvider.getFinishedTrainingsAfter(afterTime);
    }

    /**
     * Endpoint to retrieve trainings by activity type.
     *
     * This method fetches all trainings that match a specified activity type.
     *
     * @param activityType The type of activity to filter trainings by.
     * @return A list of {@link TrainingDto} representing trainings of the specified activity type.
     */
    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingProvider.getTrainingsByActivityType(activityType);
    }

    /**
     * Endpoint to create a new training.
     *
     * This method allows the creation of a new training session by providing its details in the request body.
     *
     * @param trainingDto The {@link TrainingDto} containing the details of the training to be created.
     * @return The created {@link TrainingDto} with HTTP 201 (Created) status.
     */
    @PostMapping("/trainings")
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingDto trainingDto) {
        TrainingDto createdTraining = trainingService.createTraining(trainingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTraining);
    }

    /**
     * Endpoint to update an existing training by its ID.
     *
     * This method updates the details of an existing training session, identified by its ID.
     *
     * @param trainingId  The ID of the training to be updated.
     * @param trainingDto The {@link TrainingDto} containing the updated details.
     * @return The updated {@link TrainingDto} object.
     */
    @PutMapping("/trainings/{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(
            @PathVariable Long trainingId, 
            @RequestBody TrainingDto trainingDto) {
        System.out.println("Received PUT request for training ID: " + trainingId);
        TrainingDto updatedTrainingDto = trainingService.updateTraining(trainingId, trainingDto);
        return ResponseEntity.ok(updatedTrainingDto);
    }
}
