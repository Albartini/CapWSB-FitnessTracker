package com.capgemini.wsb.fitnesstracker.training.internal.dtos;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.Getter;

import java.util.Date;

@Getter
public class UpsertTrainingDto {
    private final Date startTime;
    private final Date endTime;
    private final ActivityType activityType;
    private final double distance;
    private final double averageSpeed;
    private final long userId;


    public UpsertTrainingDto(long userId, Date startTime, Date endTime, ActivityType activityType, double distance, double averageSpeed) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.userId = userId;
    }
}

