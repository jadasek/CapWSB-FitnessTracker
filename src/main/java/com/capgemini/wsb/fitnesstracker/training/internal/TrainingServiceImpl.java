package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Provide Impl
@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }


    @Override
    public Optional<User> getTraining(final Long trainingId) {
        // Zmieniamy Optional<User> na Optional<Training>, ponieważ metoda ma zwracać trening, nie użytkownika
        Optional<Training> training = trainingRepository.findById(trainingId);

        // Sprawdzamy, czy trening istnieje, a jeśli tak, zwracamy użytkownika związany z tym treningiem
        return training.map(Training::getUser);
    }


    @Override
    public List<TrainingDto> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll(); // Pobieramy wszystkie treningi z repozytorium

        // Mapujemy je na TrainingDto
        return trainings.stream()
                .map(trainingMapper::toDto) // Mapujemy treningi na TrainingDto za pomocą mappera
                .collect(Collectors.toList());
    }




}
