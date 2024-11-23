package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.dtos.TrainingDto;

import org.springframework.stereotype.Component;

@Component
class TrainingMapper {
    TrainingDto toDto(Training training) {
        return new TrainingDto(training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }
}
