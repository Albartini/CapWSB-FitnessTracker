package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.dtos.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.dtos.UpsertTrainingDto;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapperMapper;


    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapperMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingByUser(@PathVariable long userId)
    {
        return trainingService.findAllTrainingsByUser(userId)
                .stream()
                .map(trainingMapperMapper::toDto)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTraining(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate afterTime)
    {
        return trainingService.findAllFinishedTraining(afterTime)
                .stream()
                .map(trainingMapperMapper::toDto)
                .toList();
    }


    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingByActivityType(@RequestParam("activityType") ActivityType activityType)
    {
        return trainingService.findTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapperMapper::toDto)
                .toList();
    }

    //activityType


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Training addTraining(@RequestBody UpsertTrainingDto trainingDto) throws InterruptedException {

        var training = trainingService.createTraining(trainingDto);

        return trainingService.getTraining(training.getId()).orElseThrow();
    }

    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable long trainingId, @RequestBody UpsertTrainingDto trainingDto) throws InterruptedException {
        return trainingService.updateTrainingById(trainingId, trainingDto);
    }
}
