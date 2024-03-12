package com.learn.spring.withdurgesh.demo.services;

import com.learn.spring.withdurgesh.demo.entities.User;
import com.learn.spring.withdurgesh.demo.payloads.UserDto;
import com.learn.spring.withdurgesh.demo.exceptions.*;
import com.learn.spring.withdurgesh.demo.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.userRepo.save(this.dtoToUser(userDto));
        if(user!=null){
            return this.userToDto(user) ;
        }else{
            return null;
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        // Kiểm tra xem có tồn tại người dùng với userId đã cho không
        Optional<User> findUserId=this.userRepo.findById(userId);

        if(findUserId.isPresent()){
            //Câp nhật thông tin của người dùng đã tồn tại
            User existingUser=findUserId.get();//user
            User user=this.dtoToUser(userDto);//userToDto
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setAbout(user.getAbout());

            User _user=this.userRepo.save(existingUser);
                return this.userToDto(_user);

        }else{
            return null;
        }
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Optional<User> findUserId=this.userRepo.findById(userId);

        if(findUserId.isPresent()) {
            return this.userToDto(findUserId.get());
        }else{
            return null;
        }
    }

    @Override
    public List<User> getFromIdtoIdUser(Integer beginId, Integer endId) {
        List<User> userList=userRepo.findByIdBetween(beginId,endId);
        if(userList!=null){
            return userList;
        }else {
            return null;
        }
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users=this.userRepo.findAll();
        List<UserDto> userDto=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        if(userDto!=null){
            return userDto;
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteUser(Integer userId) {
        Optional<User> findUserId=this.userRepo.findById(userId);

        if(findUserId.isPresent()){
            this.userRepo.delete(findUserId.get());
            return true;
        }else{
            return false;
        }
    }

    private User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto,User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;

    }

    private UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user,UserDto.class);

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
