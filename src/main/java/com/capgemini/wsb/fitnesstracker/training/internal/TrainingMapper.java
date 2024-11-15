package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    private final UserMapper userMapper;

    @Autowired
    public TrainingMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                userMapper.toDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingDto dto) {
        User user = userMapper.toEntity(dto.getUser());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return new Training(
                user,
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );
    }
}
