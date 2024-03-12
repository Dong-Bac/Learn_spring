package com.learn.spring.withdurgesh.demo.services;

import com.learn.spring.withdurgesh.demo.entities.Comment;
import com.learn.spring.withdurgesh.demo.entities.Post;
import com.learn.spring.withdurgesh.demo.payloads.CommentDto;
import com.learn.spring.withdurgesh.demo.repositories.CommentRepo;
import com.learn.spring.withdurgesh.demo.repositories.PostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private PostRepo  postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Optional<Post> post=this.postRepo.findById(postId);
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post.get());
         Comment savedCommnet=this.commentRepo.save(comment);
         return this.modelMapper.map(savedCommnet, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Optional<Comment> com=this.commentRepo.findById(commentId);
        this.commentRepo.delete(com.get());
    }
}
