package com.learn.spring.withdurgesh.demo.services;

import com.learn.spring.withdurgesh.demo.entities.Categories;
import com.learn.spring.withdurgesh.demo.payloads.CategoryDto;
import com.learn.spring.withdurgesh.demo.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServeceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Categories category=this.categoryRepo.save(this.modelMapper.map(categoryDto,Categories.class));
        if(category!=null){
            return this.modelMapper.map(category,CategoryDto.class);
        }else{
            return null;
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Optional<Categories> findCategoryId=this.categoryRepo.findById(categoryId);

        if(findCategoryId.isPresent()){
            Categories category=findCategoryId.get();
            Categories _category=this.modelMapper.map(categoryDto,Categories.class);
            category.setCategoryDescription(_category.getCategoryDescription());
            category.setCategoryTitle(_category.getCategoryTitle());
            this.categoryRepo.save(category);
            return this.modelMapper.map(category, CategoryDto.class);
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteCategory(Integer categoryId) {
        Optional<Categories> findCategoryId=this.categoryRepo.findById(categoryId);
        if(findCategoryId.isPresent()){
            this.categoryRepo.delete(findCategoryId.get());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Optional<Categories> findCategoryId=this.categoryRepo.findById(categoryId);
        if(findCategoryId.isPresent()){
            return this.modelMapper.map(findCategoryId.get(),CategoryDto.class);
        }else{
            return null;
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Categories> categories=this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos=new ArrayList<>();

        if(categories!=null){
            categories.forEach((category)->{
                categoryDtos.add(this.modelMapper.map(category,CategoryDto.class));
            });
            return categoryDtos;
        }else{
            return null;
        }
    }


}
