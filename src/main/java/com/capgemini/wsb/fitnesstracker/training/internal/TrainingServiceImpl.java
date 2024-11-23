package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.internal.dtos.UpsertTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }


    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findAllTrainingsByUser(Long userId) {
        return trainingRepository.findAllByUser(userId);
    }

    @Override
    public List<Training> findAllFinishedTraining(LocalDate afterTime) {
        return trainingRepository.findAllFinishedTraining(afterTime);
    }

    @Override
    public List<Training> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findTrainingsByActivityType(activityType);
    }

    @Override
    public Training createTraining(UpsertTrainingDto trainingDto) {
        var user = userProvider.getUser(trainingDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found, create training is not permitted!"));


        var training = new Training(
                user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed());

        log.info("Creating Training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTrainingById(Long trainingId, UpsertTrainingDto trainingUpdateDto) {
        log.info("Updating Training with Id {} for user {}", trainingId, trainingUpdateDto.getUserId());

        var training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found with id: " + trainingId));

        assert training.getUser().getId() != null;
        if (!training.getUser().getId().equals(trainingUpdateDto.getUserId())) {
            throw new IllegalArgumentException("User doesn't have access to modify this training");
        }

        training.setDistance(trainingUpdateDto.getDistance());
        training.setActivityType(trainingUpdateDto.getActivityType());
        training.setAverageSpeed(trainingUpdateDto.getAverageSpeed());
        training.setEndTime(trainingUpdateDto.getEndTime());
        training.setStartTime(trainingUpdateDto.getStartTime());

        return trainingRepository.save(training);
    }
}
