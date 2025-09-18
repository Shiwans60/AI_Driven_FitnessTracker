package com.fitness.activityservice.Repo;

import com.fitness.activityservice.Models.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Activityrepo extends MongoRepository<Activity,String> {


    List<Activity> findByuserId(String userId);

}
