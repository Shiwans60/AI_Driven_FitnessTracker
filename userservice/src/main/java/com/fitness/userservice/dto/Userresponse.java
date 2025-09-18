package com.fitness.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Userresponse {
    private String id;
    private String email;
    private String password;
    private String username;
    private LocalDateTime createdate;
    private LocalDateTime updatedate;

}
