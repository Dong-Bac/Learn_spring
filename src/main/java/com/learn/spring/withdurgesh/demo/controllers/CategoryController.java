package com.learn.spring.withdurgesh.demo.controllers;

import com.learn.spring.withdurgesh.demo.entities.Categories;
import com.learn.spring.withdurgesh.demo.payloads.ApiResponse;
import com.learn.spring.withdurgesh.demo.payloads.CategoryDto;
import com.learn.spring.withdurgesh.demo.services.CategoryService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto createCategory=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }


    //update
    @PutMapping(value = "/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer categoryId){
        CategoryDto updateCategory=this.categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(updateCategory);

    }

    //delete
    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<ApiResponse>  deleteCategory(@PathVariable("categoryId") Integer categoryId){
        boolean isCat=this.categoryService.deleteCategory(categoryId);
        if(isCat){
            return new ResponseEntity<>(new ApiResponse("Category deleted succesful",true), HttpStatus.OK);

        }else{
            return new ResponseEntity<>(new ApiResponse("Category didn't delete succesful",false), HttpStatus.OK);

        }
    }

    //get

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categories=this.categoryService.getAllCategories();
        if(categories!=null){
            return ResponseEntity.ok(categories);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<CategoryDto>  getCategory(@PathVariable("categoryId") Integer categoryId){
        CategoryDto category=this.categoryService.getCategory(categoryId);
        if(category!=null){
            return ResponseEntity.ok(category);

        }else{
            return ResponseEntity.notFound().build();

        }
    }

}
