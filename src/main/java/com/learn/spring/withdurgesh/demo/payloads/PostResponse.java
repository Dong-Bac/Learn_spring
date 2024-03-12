package com.learn.spring.withdurgesh.demo.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private  boolean lastPage;

}
