package com.server.aiservice.Controllers;

import com.server.aiservice.Model.Recommendation;
import com.server.aiservice.Services.Recommendationservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendations")
public class Recommendationcontroller {
    private final Recommendationservice recommendationservice;

    @GetMapping("/userrecommendation/{userId}")
    public ResponseEntity<List<Recommendation>> getuserrecommendation(@PathVariable String userId) {
        return ResponseEntity.ok(recommendationservice.getuserrecommendationby(userId));
    }
    @GetMapping("/activityrecommendation/{activityid}")
    public ResponseEntity<Recommendation> getactivityrecommendation(@PathVariable String activityId) {
        return ResponseEntity.ok(recommendationservice.getuserrecommendationbyActivity(activityId));
    }

}
