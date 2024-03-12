package com.learn.spring.withdurgesh.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.learn.spring.withdurgesh.demo.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.id BETWEEN :beginId AND :endId")
    List<User> findByIdBetween(@Param("beginId") Integer beginId,@Param("endId") Integer endId);

    Optional<User> findByEmail(String email);

}
