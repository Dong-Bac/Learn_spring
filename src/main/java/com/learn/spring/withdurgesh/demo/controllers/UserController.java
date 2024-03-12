package com.learn.spring.withdurgesh.demo.controllers;

import com.learn.spring.withdurgesh.demo.entities.User;
import com.learn.spring.withdurgesh.demo.payloads.ApiResponse;
import com.learn.spring.withdurgesh.demo.payloads.UserDto;
import com.learn.spring.withdurgesh.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    //POST-create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        // Your implementation to create a user
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    //PUT-update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> UpdateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
        UserDto updateUser=this.userService.updateUser(userDto, userId);
        if(updateUser!=null){
            return ResponseEntity.ok(updateUser);
        }else{
            // Trả về http 404 not found nếu không tìm thấy nguồi dùng cần cập nhật
            return ResponseEntity.notFound().build();
        }


    }
    //DELETE-delete user
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
        boolean deleted=this.userService.deleteUser(userId);
        if(deleted){
            return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Succesfully", true),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<ApiResponse>(new ApiResponse("User don't deleted Succesfully", false),HttpStatus.CREATED);
        }

    }

    //GET- user get
    @GetMapping("/")
    //lấy tất cả
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users=this.userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    //lấy theo id
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getIdUser(@PathVariable("userId") Integer userId){
        UserDto user=this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    //lấy nhiều ngươi theo id
    @GetMapping(value = "/{userId1}/{userId2}")
    public ResponseEntity<List<User>> getFromIdToId(@PathVariable("userId1") Integer userId1, @PathVariable("userId2") Integer userId2){
        List<User> users=this.userService.getFromIdtoIdUser(userId1,userId2);
        return ResponseEntity.ok(users);
    }

}
