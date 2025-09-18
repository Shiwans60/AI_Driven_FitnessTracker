package com.fitness.userservice.Controllers;

import com.fitness.userservice.Services.Userservice;
import com.fitness.userservice.dto.Registerreq;
import com.fitness.userservice.dto.Userresponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Users")
public class Usercontroller {
    @Autowired
    private Userservice userservice;


    @GetMapping("/getuser/{userid}")
    public ResponseEntity<Userresponse> getUser(@PathVariable String userid) {
        return ResponseEntity.ok(userservice.getUserprofile(userid));
    }


    @PostMapping("/register")
    public ResponseEntity<Userresponse> register(@Valid @RequestBody Registerreq registerreq){
         return ResponseEntity.ok(userservice.register(registerreq));
    }

    @GetMapping("/getuser/{userid}/validate")
    public ResponseEntity<Boolean> validateuser(@PathVariable String userid) {
        return ResponseEntity.ok(userservice.existuserbyId(userid));
    }

}
