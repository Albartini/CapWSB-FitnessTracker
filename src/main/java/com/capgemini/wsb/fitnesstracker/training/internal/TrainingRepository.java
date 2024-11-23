package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

interface TrainingRepository extends JpaRepository<Training, Long> {

    default List<Training> findAllByUser(Long userId) {
        return findAll().stream()
                .filter(training -> Objects.equals(training.getUser().getId(), userId)).toList();
    }

    default List<Training> findAllFinishedTraining(LocalDate afterTime) {
        return findAll().stream()
                .filter(training -> training.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(afterTime))
                .toList();
    }

    default List<Training> findTrainingsByActivityType(ActivityType activityType) {
        return findAll().stream()
                .filter(training -> Objects.equals(training.getActivityType(), activityType))
                .toList();
    }
}
