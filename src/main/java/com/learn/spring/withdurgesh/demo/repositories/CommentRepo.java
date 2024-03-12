package com.learn.spring.withdurgesh.demo.repositories;

import com.learn.spring.withdurgesh.demo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
