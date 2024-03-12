package com.learn.spring.withdurgesh.demo.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;
}
