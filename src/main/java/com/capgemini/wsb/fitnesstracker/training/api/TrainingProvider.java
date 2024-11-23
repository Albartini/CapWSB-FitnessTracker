package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

    /**
     * Retrieves all trainings associated with a specific user.
     * This method fetches a list of all training sessions recorded for the given user.
     *
     * @param userId The unique identifier of the user whose trainings are to be retrieved.
     * @return A List of Training objects associated with the specified user.
     *         Returns an empty list if no trainings are found for the user.
     */
    List<Training> findAllTrainingsByUser(Long userId);

    /**
     * Retrieves all trainings have been completed after a given time.
     * This method fetches a list of all training sessions have been completed after the specified time.
     *
     * @param afterTime The date and time after which the trainings should have been completed.
     * @return A List of Training objects associated with the specified user and completed after the given time.
     *         Returns an empty list if no trainings are found for the after time that meet the criteria.
     */
    List<Training> findAllFinishedTraining(LocalDate afterTime);

    /**
     * Retrieves all trainings associated with a specific activity type.
     * This method fetches a list of all training sessions recorded for the given of the specified activity type.
     *
     * @param activityType The type of activity for which the trainings should have been completed.
     * @return A List of Training objects associated with the specified user and  the given activity type.
     *         Returns an empty list if no trainings are found for the activity type that meet the criteria.
     */
    List<Training> findTrainingsByActivityType(ActivityType activityType);

    /**
     * Retrieves all trainings.
     *
     * @return An {@link Optional} containing the all trainings,
     */
    List<Training> findAllTrainings();

}
