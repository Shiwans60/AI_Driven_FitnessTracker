package com.fitness.activityservice.dto;

import com.fitness.activityservice.Models.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Data
public class Activityreq {
    private String userid;
    private ActivityType activity;
    private Integer duration;
    private Integer caloriesburnt;
    private LocalDateTime starttime;
    private Map<String,Object> adiitionalMetrics;
}
