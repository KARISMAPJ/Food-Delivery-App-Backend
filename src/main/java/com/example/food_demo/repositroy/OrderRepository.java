package com.example.food_demo.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.food_demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	List<Order>findByUserEmail(String email);

}
