package com.fitness.userservice.Services;

import com.fitness.userservice.Models.User;
import com.fitness.userservice.Repo.Userrepo;
import com.fitness.userservice.dto.Registerreq;
import com.fitness.userservice.dto.Userresponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Userservice {
    @Autowired
    private Userrepo userrepo;

    public Userresponse getUserprofile(String userid) {
        User user = userrepo.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Userresponse userresponse = new Userresponse();
        userresponse.setId(user.getId());
        userresponse.setEmail(user.getEmail());
        userresponse.setUsername(user.getUsername());
        userresponse.setPassword(user.getPassword());
        userresponse.setCreatedate(user.getCreatedat());
        userresponse.setUpdatedate(user.getUpdatedat());
        return userresponse;


    }

    public Userresponse register(@Valid Registerreq registerreq) {
        if(userrepo.existsByEmail(registerreq.getEmail())) {
            throw new RuntimeException("User is already in use");
        }
        User user = new User();
        user.setEmail(registerreq.getEmail());
        user.setUsername(registerreq.getUsername());
        user.setPassword(registerreq.getPassword());
        User saved = userrepo.save(user);
        Userresponse userresponse = new Userresponse();
        userresponse.setId(saved.getId());
        userresponse.setEmail(saved.getEmail());
        userresponse.setUsername(saved.getUsername());
        userresponse.setPassword(saved.getPassword());
        userresponse.setCreatedate(saved.getCreatedat());
        userresponse.setUpdatedate(saved.getUpdatedat());
        return userresponse;



    }

    public Boolean existuserbyId(String userid) {
        return userrepo.existsById(userid);
    }
}
