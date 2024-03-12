package com.learn.spring.withdurgesh.demo.controllers;

import com.learn.spring.withdurgesh.demo.entities.Comment;
import com.learn.spring.withdurgesh.demo.payloads.ApiResponse;
import com.learn.spring.withdurgesh.demo.payloads.CommentDto;
import com.learn.spring.withdurgesh.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId){
        CommentDto createComment=this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully !!",true), HttpStatus.OK);
    }
}
