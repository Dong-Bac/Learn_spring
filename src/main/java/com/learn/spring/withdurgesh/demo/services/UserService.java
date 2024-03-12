package com.learn.spring.withdurgesh.demo.services;

import com.learn.spring.withdurgesh.demo.entities.User;
import com.learn.spring.withdurgesh.demo.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);

    List<User> getFromIdtoIdUser(Integer beginId, Integer endId);
    List<UserDto> getAllUser();
    boolean deleteUser(Integer userId);


}
