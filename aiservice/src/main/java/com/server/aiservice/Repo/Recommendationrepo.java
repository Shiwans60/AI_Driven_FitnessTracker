package com.server.aiservice.Repo;

import com.server.aiservice.Model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Recommendationrepo extends MongoRepository<Recommendation, String> {

    List<Recommendation> findByuserId(String userId);

    Optional<Recommendation> findByactivityId(String activityId);
}
