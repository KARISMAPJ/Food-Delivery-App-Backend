package com.example.food_demo.Controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.food_demo.entity.Food;
import com.example.food_demo.jwt.JwtUtil;
import com.example.food_demo.service.FoodService;

@RestController
@RequestMapping("/food")
public class FoodController {
	
	@Autowired
	private FoodService service;
	
	
//	@PostMapping("/add")
//	public ResponseEntity<Food>addFood(@RequestBody Food food){
//		return ResponseEntity.ok(service.addFood(food));
//		
//	}
	
	//ADDFOOD
	
	@PostMapping("/add")
	public ResponseEntity<?>addFood(@RequestBody Food food,Authentication auth){
		
		String role = auth.getAuthorities().iterator().next().getAuthority();
		if(!role.equals("ROLE_ADMIN")) {
			return ResponseEntity.status(403).body("Only Admin can add food items");
		}
		
		String email = auth.getName();
		Food saved = service.addFood(food, email);
		return ResponseEntity.ok(saved);
	}
	
	//ALL
	@GetMapping("/all")
	public ResponseEntity<List<Food>>getFood(){
		return ResponseEntity.ok(service.getAllFood());
	}
	
	//GET BY ID
	@GetMapping("/{id}")
	public ResponseEntity<Food>getFoodById(@PathVariable int id){
		return ResponseEntity.ok(service.getFoodById(id));
		
		
	}
	
	//DELETE
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?>deleteFood(@PathVariable int id,Authentication auth){
		
		String role =auth.getAuthorities().iterator().next().getAuthority();
		if(!role.equals("ROLE_ADMIN")) {
			return ResponseEntity.status(403).body("Only admin can delete food");
		}
		
		//String email = auth.getName();
		 boolean deleted = service.deleteFood(id);
		
		if(!deleted) {
			return ResponseEntity.status(403).body("Not allowed to delete this food item");
		}
		return ResponseEntity.ok("Food Deleted Successfully");
	}
		
		
	//UPDATE
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?>updateFood(@PathVariable int id,@RequestBody Food newFoodData, Authentication auth){
		
		String role = auth.getAuthorities().iterator().next().getAuthority();
		if (!role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Only admin can update food items");
        }
	
		Food updated = service.updateFood(id,newFoodData);
		
		if(updated == null) {
			return ResponseEntity.status(404).body("Food not found or not allowed");
		}
		
		return ResponseEntity.ok(updated);
	}
	
	//  SEARCH 
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchFood(@PathVariable String name) {
        return ResponseEntity.ok(service.searchFood(name));
    }

    // FILTER 
    
    @GetMapping("/category/{category}")
    public ResponseEntity<?> filterCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.filterByCategory(category));
    }

    //  SORTING 
    @GetMapping("/sort/asc")
    public ResponseEntity<?> sortAsc() {
        return ResponseEntity.ok(service.sortByPriceAsc());
    }

    @GetMapping("/sort/desc")
    public ResponseEntity<?> sortDesc() {
        return ResponseEntity.ok(service.sortByPriceDesc());
    }

}
