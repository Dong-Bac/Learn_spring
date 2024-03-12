package com.learn.spring.withdurgesh.demo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.learn.spring.withdurgesh.demo.config.AppConstants;
import com.learn.spring.withdurgesh.demo.entities.Post;
import com.learn.spring.withdurgesh.demo.payloads.ApiResponse;
import com.learn.spring.withdurgesh.demo.payloads.CategoryDto;
import com.learn.spring.withdurgesh.demo.payloads.PostDto;
import com.learn.spring.withdurgesh.demo.payloads.PostResponse;
import com.learn.spring.withdurgesh.demo.services.FileService;
import com.learn.spring.withdurgesh.demo.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId

    ){
        PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }


    //get by user
    @GetMapping(value = "/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){

        List<PostDto> posts=this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    //get by category
    @GetMapping(value = "/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer CategoryId){

        List<PostDto> posts=this.postService.getPostsByUser(CategoryId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    //get all posts
    @GetMapping(value = "/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue =AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value="sortBy", defaultValue =AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue =AppConstants.SORT_DIC, required = false) String sortDir
    ){
        PostResponse postResponse=this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //get post details by id
    @GetMapping(value = "/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post í successfully deleted !!",true);
    }

    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto updatePost=this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    //search
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
            @PathVariable("keywords") String keywords
    ){
        List<PostDto> result = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {
        String fileName=this.fileService.uploadImage(path, image);
        PostDto postDto=this.postService.getPostById(postId);
        postDto.setImageName(fileName);
        PostDto updatePost=this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    //method to serve files
    @GetMapping(value = "post/image/{imageName}", produces= MediaType.IMAGE_GIF_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    ) throws IOException{
        InputStream resource=this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_GIF_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
