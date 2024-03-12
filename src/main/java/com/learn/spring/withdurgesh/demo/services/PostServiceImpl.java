package com.learn.spring.withdurgesh.demo.services;

import com.learn.spring.withdurgesh.demo.entities.Categories;
import com.learn.spring.withdurgesh.demo.entities.Post;
import com.learn.spring.withdurgesh.demo.entities.User;
import com.learn.spring.withdurgesh.demo.payloads.PostDto;
import com.learn.spring.withdurgesh.demo.payloads.PostResponse;
import com.learn.spring.withdurgesh.demo.repositories.CategoryRepo;
import com.learn.spring.withdurgesh.demo.repositories.PostRepo;
import com.learn.spring.withdurgesh.demo.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        Optional<User> user=this.userRepo.findById(userId);
        Optional<Categories> category=this.categoryRepo.findById(categoryId);

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user.get());
        post.setCategory(category.get());

        Post newPost=this.postRepo.save(post);
        if(post!=null){
            return this.modelMapper.map(newPost,PostDto.class);
        }else {
            return null;
        }
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Optional<Post> post=this.postRepo.findById(postId);
        if(post.isPresent()){

            Post oldPost=post.get();
            Post newPost=this.modelMapper.map(postDto,Post.class);
            oldPost.setTitle(newPost.getTitle());
            oldPost.setContent(newPost.getContent());
            oldPost.setImageName(newPost.getImageName());

            this.postRepo.save(oldPost);

            return this.modelMapper.map(oldPost,PostDto.class);
        }else{
            return null;
        }

    }

    @Override
    public boolean deletePost(Integer postId) {
        Optional<Post> post=this.postRepo.findById(postId);
        if(post.isPresent()){
            this.postRepo.delete(post.get());
            return true;

        }else{
            return false;
        }


    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        //Pageable p= PageRequest.of(pageNumber,pageSize);
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else{
            sort=Sort.by(sortBy).descending();
        }

        Pageable p =PageRequest.of(pageNumber, pageSize, sort);


        Page<Post> pagePost =this.postRepo.findAll(p);
        List<Post> allPosts=pagePost.getContent();
        System.out.print(pagePost.getContent());
        List<PostDto> postDtos=new ArrayList<>();

        allPosts.forEach((post)->postDtos.add(this.modelMapper.map(post,PostDto.class)));

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return  postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Optional<Post> post=this.postRepo.findById(postId);
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Optional<Categories> category=this.categoryRepo.findById(categoryId);
        List<Post> posts=this.postRepo.findByCategory(category.get());
        List<PostDto> postsDto=new ArrayList<>();

        posts.forEach((post)->{
            postsDto.add(this.modelMapper.map(post,PostDto.class));
        });
        return postsDto;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        Optional<User> user=this.userRepo.findById(userId);
        List<Post> posts=this.postRepo.findByUser(user.get());
        List<PostDto> postsDto=new ArrayList<>();

        posts.forEach((post)->{
            postsDto.add(this.modelMapper.map(post,PostDto.class));
        });
        return postsDto;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=this.postRepo.searchPostByTitle("%"+keyword+"%");
        List<PostDto> postDtos=new ArrayList<>();

        posts.forEach(post -> {
            postDtos.add(this.modelMapper.map(post,PostDto.class));
        });

        return postDtos;
    }


}
