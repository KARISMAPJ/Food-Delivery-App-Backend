package com.example.food_demo.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.food_demo.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Integer> {
	//save()
	//findById()
	//findAll()
	//delete()
	
	List<Food> findByNameContainingIgnoreCase(String name);

    // 2. Filter by category
    List<Food> findByCategoryIgnoreCase(String category);

}
