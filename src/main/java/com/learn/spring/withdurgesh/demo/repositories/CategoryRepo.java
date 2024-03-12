package com.learn.spring.withdurgesh.demo.repositories;

import com.learn.spring.withdurgesh.demo.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Categories,Integer> {


}
