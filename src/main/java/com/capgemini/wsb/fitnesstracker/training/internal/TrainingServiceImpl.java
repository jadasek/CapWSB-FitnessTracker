package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Provide Impl
@Service
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserRepository userRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingMapper trainingMapper, UserRepository userRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<TrainingDto> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll(); 

        // Mapujemy je na TrainingDto
        return trainings.stream()
                .map(trainingMapper::toDto) 
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        List<Training> trainings = trainingRepository.findByUserId(userId); 
        return trainings.stream()
                .map(trainingMapper::toDto) 
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getFinishedTrainingsAfter(Date afterTime) {
        List<Training> trainings = trainingRepository.findByEndTimeAfter(afterTime);
        return trainings.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        User user = userRepository.findById(trainingDto.getUser().getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Training training = new Training(
            user, 
            trainingDto.getStartTime(),
            trainingDto.getEndTime(),
            trainingDto.getActivityType(),
            trainingDto.getDistance(),
            trainingDto.getAverageSpeed()
        );

        trainingRepository.save(training);
        return trainingMapper.toDto(training);
    }
    
    @Override
    public TrainingDto updateTraining(Long id, TrainingDto trainingDto) {
        return trainingRepository.findById(id)
                .map(existingTraining -> {
                    if (trainingDto.getUser() != null) {
                        User user = userRepository.findById(trainingDto.getUser().getId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        existingTraining.setUser(user);
                    }
                    if (trainingDto.getStartTime() != null) {
                        existingTraining.setStartTime(trainingDto.getStartTime());
                    }
                    if (trainingDto.getEndTime() != null) {
                        existingTraining.setEndTime(trainingDto.getEndTime());
                    }
                    if (trainingDto.getActivityType() != null) {
                        existingTraining.setActivityType(trainingDto.getActivityType());
                    }
                    if (trainingDto.getDistance() != 0) {
                        existingTraining.setDistance(trainingDto.getDistance());
                    }
                    if (trainingDto.getAverageSpeed() != 0) {
                        existingTraining.setAverageSpeed(trainingDto.getAverageSpeed());
                    }
                    return trainingRepository.save(existingTraining);
                })
                .map(trainingMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Training not found"));
    }
    
}
