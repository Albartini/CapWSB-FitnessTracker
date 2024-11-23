package com.capgemini.wsb.fitnesstracker.training.internal.dtos;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class TrainingDto {
    private final Date startTime;
    private final Date endTime;
    private final ActivityType activityType;
    private final double distance;
    private final double averageSpeed;
    private final User user;


    public TrainingDto(User user, Date startTime, Date endTime, ActivityType activityType, double distance, double averageSpeed) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.user = user;
    }
}

