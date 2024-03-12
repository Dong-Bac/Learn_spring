package com.learn.spring.withdurgesh.demo.services;

import com.learn.spring.withdurgesh.demo.entities.Categories;
import com.learn.spring.withdurgesh.demo.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    boolean deleteCategory(Integer categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto getCategory(Integer categoryId);



}



