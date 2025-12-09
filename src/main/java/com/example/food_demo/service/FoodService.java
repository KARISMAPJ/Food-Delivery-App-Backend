package com.example.food_demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food_demo.entity.Food;
import com.example.food_demo.entity.User;
import com.example.food_demo.exception.ResourceNotFoundException;
import com.example.food_demo.exception.UnauthorizedException;
import com.example.food_demo.repositroy.FoodRepository;
import com.example.food_demo.repositroy.UserRepository;
@Service
public class FoodService {
	
	@Autowired
	private FoodRepository repo;
	@Autowired
	private UserRepository repository;
	
//	public Food addFood(Food food) {
//		return repo.save(food);
//	}
	
	public Food addFood(Food food, String email) {
		User user = repository.findByEmail(email).orElseThrow();
		food.setUser(user);
		return repo.save(food);
	}
	
	public List<Food>getAllFood(){
		return repo.findAll();
	}
	
	public Food getFoodById(int id) {
		return repo.findById(id).orElse(null);
	}
	
	public boolean deleteFood(int id) {
		
		Optional<Food>optionalFood = repo.findById(id);
		
		if(optionalFood.isEmpty()) {
			return false;
		}
		
		repo.delete(optionalFood.get());
//		Food food = optionalFood.get();
//		
//		if(!food.getUser().getEmail().equals(email)) {
//			return false;
//		}
//		repo.delete(food);
		return true;
		
		
	}
	
	public Food updateFood(int id, Food newData) {
		
		Optional<Food>optionalFood = repo.findById(id);
		
		if(optionalFood.isEmpty()) {
			return null;
		}
		
		Food existingFood = optionalFood.get();
		
//		if(!existingFood.getUser().getEmail().equals(email)) {
//			return null;
//			
//		}
		
		existingFood.setName(newData.getName());
		existingFood.setPrice(newData.getPrice());
		existingFood.setDescription(newData.getDescription());
		
		return repo.save(existingFood);
	}
	
	 // ------------------ SEARCH ------------------
    public List<Food> searchFood(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    // ------------------ FILTER BY CATEGORY ------------------
    public List<Food> filterByCategory(String category) {
        return repo.findByCategoryIgnoreCase(category);
    }

    // ------------------ SORT ASC ------------------
    public List<Food> sortByPriceAsc() {
        List<Food> list = repo.findAll();
        list.sort(Comparator.comparingDouble(Food::getPrice));
        return list;
    }

    // ------------------ SORT DESC ------------------
    public List<Food> sortByPriceDesc() {
        List<Food> list = repo.findAll();
        list.sort(Comparator.comparingDouble(Food::getPrice).reversed());
        return list;
    }

}
