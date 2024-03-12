package com.learn.spring.withdurgesh.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/request")
public class test {

    @GetMapping()
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello everyone !!");
    }

    @GetMapping("/goodbye")
    public ResponseEntity<String> goodbye(){
        return ResponseEntity.ok("Goodbye everyone !!");
    }
}
