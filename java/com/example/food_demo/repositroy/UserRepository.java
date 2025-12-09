package com.example.food_demo.repositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.food_demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User>findByEmail(String email);

}
