package com.example.food_demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.food_demo.dto.LoginRequest;
import com.example.food_demo.entity.User;
import com.example.food_demo.exception.ResourceNotFoundException;
import com.example.food_demo.exception.UnauthorizedException;
import com.example.food_demo.jwt.JwtUtil;
import com.example.food_demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public UserController (UserService service) {
		this.service = service;
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user){
		User savedUser =  service.register(user);
		return ResponseEntity.ok(savedUser);
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<String>login(@RequestBody LoginRequest request){
//		
//		User loggedUser = service.login(request.getEmail(), request.getPassword());
//		
//		if(loggedUser == null) {
//			return ResponseEntity.badRequest().body("User Not Found");
//		}
//		
//		if(loggedUser.getId()==0) {
//			return ResponseEntity.badRequest().body("Incorrect Password");
//		}
//		
//		String token = JwtUtil.generateToken(loggedUser.getEmail());
//		
//		return ResponseEntity.ok("Login Successfull!! Welocme "+loggedUser.getName()+" Your Token: "+token);
//	}

	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {

		User dbUser = service.login(user.getEmail(), user.getPassword());

//	    if (dbUser == null) {
//	        return ResponseEntity.status(404).body("User not found");
//	    }
//
//	    if (dbUser.getId() == 0) {
//	        return ResponseEntity.status(401).body("Wrong password");
//	    }

	    String token = jwtUtil.generateToken(dbUser.getEmail(),dbUser.getRole());

	    return ResponseEntity.ok(token);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<?>getProfile(HttpServletRequest request){
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			
			return ResponseEntity.status(401).body("Missing or invalid token");
		}
		
		String token = authHeader.substring(7);
		String email = jwtUtil.extractEmail(token);
		
		return ResponseEntity.ok("Your profile email: "+email);
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//
//	    try {
//	        User dbUser = service.login(request.getEmail(), request.getPassword());
//
//	        String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getRole());
//
//	        return ResponseEntity.ok(token);
//
//	    } catch (ResourceNotFoundException e) {
//	        return ResponseEntity.status(404).body(e.getMessage());
//
//	    } catch (UnauthorizedException e) {
//	        return ResponseEntity.status(401).body(e.getMessage());
//
//	    } catch (Exception e) {
//	        return ResponseEntity.status(500).body("Something went wrong");
//	    }
	}

	
	


