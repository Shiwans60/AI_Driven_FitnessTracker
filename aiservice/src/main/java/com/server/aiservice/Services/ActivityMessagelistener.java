package com.server.aiservice.Services;

import com.server.aiservice.Model.Activity;
import com.server.aiservice.Model.Recommendation;
import com.server.aiservice.Repo.Recommendationrepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessagelistener  {
    private final ActivityAiService activityAiService;
    private final Recommendationrepo recommendationrepo;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {
        log.info("Received Activity for processing : {}", activity.getId());
        //log.info("Generated Recommendations : {}", activityAiService.generateRecommendations(activity));
        Recommendation recommendation = activityAiService.generateRecommendations(activity);
        recommendationrepo.save(recommendation);


    }
}
