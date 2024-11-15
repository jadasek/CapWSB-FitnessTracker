package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;

import java.util.List;

@RestController
public class TrainingController {

    private final TrainingProvider trainingProvider;

    @Autowired
    public TrainingController(TrainingProvider trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

    @GetMapping("/v1/trainings")
    public List<TrainingDto> getAllTrainings() {
        return trainingProvider.getAllTrainings(); // Zwracamy wszystkie treningi
    }
}
