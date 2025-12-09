package com.example.food_demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.food_demo.entity.User;
import com.example.food_demo.repositroy.OrderRepository;
import com.example.food_demo.repositroy.UserRepository;
import com.example.food_demo.service.OrderService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private OrderService service;
	
	@GetMapping("/users")
	public ResponseEntity<?>getAllUsers(){
		List<User>list = repository.findAll();
		return ResponseEntity.ok(list);
	}
	
	
	@DeleteMapping("/delete/user/{id}")
	public ResponseEntity<?>deleteUsers(@PathVariable int id){
		
		
		repository.deleteById(id);
		return ResponseEntity.ok("User deleted Successfully");
	}
	
	public ResponseEntity<?>deleteOrder(@PathVariable int id){
		service.deleteOrderByAdmin(id);
	
		return ResponseEntity.ok("Order deleted successfully by admin");
	}

	
	

}
