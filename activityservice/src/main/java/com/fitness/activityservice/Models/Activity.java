package com.fitness.activityservice.Models;

import com.fitness.activityservice.Models.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@Document(collection = "ACTIVITIES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity  {
    @Id
    private String id;
    private String userId;
    private ActivityType activity;
    private Integer duration;
    private Integer caloriesburnt;
    private LocalDateTime starttime;
    @Field("metrics")
    private Map<String,Object> adiitionalMetrics;
    @CreatedDate
    private LocalDateTime createdat ;
    @LastModifiedDate
    private LocalDateTime updatedat;
}
