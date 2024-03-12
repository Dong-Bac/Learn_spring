package com.learn.spring.withdurgesh.demo.repositories;

import com.learn.spring.withdurgesh.demo.entities.Categories;
import com.learn.spring.withdurgesh.demo.entities.Post;
import com.learn.spring.withdurgesh.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Categories category);

    @Query("select p from Post p where p.title like :key")//%keywork%
    List<Post> searchPostByTitle(@Param("key") String title);

}
