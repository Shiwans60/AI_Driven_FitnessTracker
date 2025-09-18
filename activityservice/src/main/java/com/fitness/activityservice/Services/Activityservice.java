package com.fitness.activityservice.Services;

import com.fitness.activityservice.Repo.Activityrepo;
import com.fitness.activityservice.dto.Activityreq;
import com.fitness.activityservice.dto.Activityresponse;
import com.fitness.activityservice.Models.Activity;
import com.fitness.activityservice.Repo.Activityrepo;
import com.fitness.activityservice.dto.Activityreq;
import com.fitness.activityservice.dto.Activityresponse;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class Activityservice {

    private final Activityrepo activityrepo;

    private final Uservalidationservice uservalidationservice;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;



    public Activityresponse track(Activityreq activityreq) {
        Boolean isValidUser = uservalidationservice.validateuser(activityreq.getUserid());
        if(!isValidUser){
            throw  new RuntimeException("Invalid user id" + activityreq.getUserid());
        }

        Activity activity = Activity.builder()
                .userId(activityreq.getUserid())
                .activity(activityreq.getActivity())
                .duration(activityreq.getDuration())
                .caloriesburnt(activityreq.getCaloriesburnt())
                .starttime(activityreq.getStarttime())
                .adiitionalMetrics(activityreq.getAdiitionalMetrics())
                .build();
        Activity saved = activityrepo.save(activity);
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, saved);


        } catch (Exception e) {
            log.error("Failed to published at rabbit mq" + e);
        }
        return maptorespnse(saved);

    }
    private Activityresponse maptorespnse(Activity activity) {
        Activityresponse response = new Activityresponse();
        response.setId(activity.getId());
        response.setUserid(activity.getUserId());
        response.setActivitype(activity.getActivity());
        response.setDuration(activity.getDuration());
        response.setCaloriesburnt(activity.getCaloriesburnt());
        response.setStarttime(activity.getStarttime());
        response.setAdiitionalMetrics(activity.getAdiitionalMetrics());
        response.setCreatedat(activity.getCreatedat());
        response.setUpdatedat(activity.getUpdatedat());
        return response;

    }

    public List<Activityresponse> getactivities(String userId) {

        List<Activity> activities = activityrepo.findByuserId(userId);
        return activities.stream()
                .map(this::maptorespnse)
                .collect(Collectors.toList());
        
    }
}
