package com.fitness.activityservice.Controllers;

import com.fitness.activityservice.Services.Activityservice;
import com.fitness.activityservice.dto.Activityreq;
import com.fitness.activityservice.dto.Activityresponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class Activitycontroller {
    @Autowired
    private Activityservice activityservice;
    @PostMapping
    public ResponseEntity<Activityresponse> trackactivity(@RequestBody Activityreq activityreq){
        return ResponseEntity.ok(activityservice.track(activityreq));
    }
    @GetMapping
    public ResponseEntity<List<Activityresponse>> getAllActivities(@RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(activityservice.getactivities(userId));
    }


}
