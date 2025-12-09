package com.example.food_demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="Orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int quantity;
	private double totalPrice;
	private String status = "Placed";
	private LocalDateTime orderTime = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne 
	@JoinColumn(name = "food_id")
	private Food food;

}
