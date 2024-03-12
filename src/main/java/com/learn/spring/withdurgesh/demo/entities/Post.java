package com.learn.spring.withdurgesh.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private String imageName;

    @Column(name = "post_title", length = 100,nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

    private Date addDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, fetch =FetchType.LAZY )
    private Set<Comment> comments=new HashSet<>();


}
