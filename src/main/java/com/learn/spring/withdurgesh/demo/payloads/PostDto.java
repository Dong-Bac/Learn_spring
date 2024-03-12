package com.learn.spring.withdurgesh.demo.payloads;

import java.util.Date;
import java.util.Set;

import com.learn.spring.withdurgesh.demo.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {

    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> commentDtos;
}
