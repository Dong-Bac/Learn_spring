package com.learn.spring.withdurgesh.demo.services;

import com.learn.spring.withdurgesh.demo.entities.Post;
import com.learn.spring.withdurgesh.demo.payloads.PostDto;
import com.learn.spring.withdurgesh.demo.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
        PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
        PostDto updatePost(PostDto postDto, Integer postId);

    //delete
        boolean deletePost(Integer postId);

    //get all posts
        PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single post
        PostDto getPostById(Integer postId);

    //get all posts by category
        List<PostDto> getPostsByCategory(Integer categoryId);

    //get all posts by user
        List<PostDto> getPostsByUser(Integer userId);
    //search posts
        List<PostDto> searchPosts(String keyword);

}
