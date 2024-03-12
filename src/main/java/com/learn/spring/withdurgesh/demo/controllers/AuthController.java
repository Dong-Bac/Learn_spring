package com.learn.spring.withdurgesh.demo.controllers;

import com.learn.spring.withdurgesh.demo.blog.security.JwtTokenHelper;
import com.learn.spring.withdurgesh.demo.payloads.JwtAuthRequest;
import com.learn.spring.withdurgesh.demo.payloads.JwtAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
            ){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=this.userDetailsService.loadUserByUsername(request.getUsername());

         String token=this.jwtTokenHelper.generateToken(user);
        System.out.println("token controller: "+token);

         JwtAuthResponse response=new JwtAuthResponse();
         response.setToken(token);
         return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
