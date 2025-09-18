package com.server.aiservice.Model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;


@Data
public class Activity {
    private String id;
    private String userId;
    private Integer duration;
    private String type;
    private Integer caloriesburnt;
    private LocalDateTime starttime;
    private Map<String,Object> adiitionalMetrics;
    private LocalDateTime createdat ;
    private LocalDateTime updatedat;


}
