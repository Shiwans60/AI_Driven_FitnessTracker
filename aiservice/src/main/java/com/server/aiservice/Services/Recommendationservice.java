package com.server.aiservice.Services;

import com.server.aiservice.Controllers.Recommendationcontroller;
import com.server.aiservice.Model.Recommendation;
import com.server.aiservice.Repo.Recommendationrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Recommendationservice {
    private final Recommendationrepo recommendationrepo;

    public List<Recommendation> getuserrecommendationby(String userId) {
        return recommendationrepo.findByuserId(userId);
    }

    public Recommendation getuserrecommendationbyActivity(String activityId) {
        return recommendationrepo.findByactivityId(activityId)
                .orElseThrow(() -> new RuntimeException("no recommendation found for this activityId"));

    }
}
