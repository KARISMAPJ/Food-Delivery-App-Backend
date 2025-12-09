package com.example.food_demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.food_demo.entity.User;
import com.example.food_demo.exception.ResourceNotFoundException;
import com.example.food_demo.exception.UnauthorizedException;
import com.example.food_demo.repositroy.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	
	public UserService(UserRepository repository) {
		this.repository=repository;
	}
	public User register(User user) {
		
		 // Check email exists
	    Optional<User> existing = repository.findByEmail(user.getEmail());
	    if (existing.isPresent()) {
	        throw new RuntimeException("Email already registered");
	    }

	    // Always assign USER role if not provided
	    if (user.getRole() == null || user.getRole().isBlank()) {
	        user.setRole("USER");
	    }
	    return repository.save(user);
	}

	
	public User login(String email,String password) {
//		 Optional<User> optionalUser = repository.findByEmail(email);
//		 
//		 if(optionalUser.isEmpty()) {
//			 return null;
//		 }
//		 
//		 User existingUser = optionalUser.get();
//		 
//		 if(!existingUser.getPassword().equals(password)) {
//			 User wrongUser = new User();
//			 wrongUser.setId(0);
//			 return wrongUser;
//		 }
//		 
//		 return existingUser;
		
		User user = repository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found with email: !"+email));
		
		if(user.getPassword()==null) {
			throw new RuntimeException("Password is missing for this user!");
			
		}
		if(!user.getPassword().equals(password)) {
			throw new RuntimeException("Incorrect Password");
		}
		return user;
	}
	
	
}
