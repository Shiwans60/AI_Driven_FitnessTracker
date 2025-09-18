package com.fitness.activityservice.dto;

import com.fitness.activityservice.Models.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activityresponse {
    private String id;
    private String userid;
    private ActivityType activitype;
    private Integer duration;
    private Integer caloriesburnt;
    private LocalDateTime starttime;
    private Map<String,Object> adiitionalMetrics;
    private LocalDateTime createdat ;
    private LocalDateTime updatedat;

}
