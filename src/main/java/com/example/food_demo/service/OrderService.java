package com.example.food_demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food_demo.entity.Food;
import com.example.food_demo.entity.Order;
import com.example.food_demo.entity.User;
import com.example.food_demo.repositroy.FoodRepository;
import com.example.food_demo.repositroy.OrderRepository;
import com.example.food_demo.repositroy.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private FoodRepository repo;
	
	@Autowired
	private UserRepository repository;
	
	
	public Order placeOrder(int foodId,int quantity, String email) {
		Food food = repo.findById(foodId).orElse(null);
		if(food == null) {
			return null;
		}
		User user = repository.findByEmail(email).orElse(null);
		if(user == null) {
			return null;
		}
		
		
		Order order = new Order();
		order.setFood(food);
		order.setUser(user);
		order.setQuantity(quantity);
		order.setTotalPrice(food.getPrice()*quantity);
		order.setOrderTime(LocalDateTime.now());
		order.setStatus("Placed");
		
		return orderRepo.save(order);
	}
	
	
	public List<Order>getMyOrder(String email){
		return orderRepo.findByUserEmail(email);
	}
	
	
	public List<Order>getAllOrder(){
		return orderRepo.findAll();
	}
	
	public boolean deleteOrder(int id, String email) {

        Optional<Order> optionalOrder = orderRepo.findById(id);

        if (optionalOrder.isEmpty()) {
            return false; // Not found
        }

        Order order = optionalOrder.get();

        if (!order.getUser().getEmail().equals(email)) {
            return false; // You are not allowed to delete others' orders
        }

        orderRepo.delete(order);
        return true;
    }
	
	public Order updateOrderStatus(int id, String newStatus ,String email) {
		
		Optional<Order>optionalOrder = orderRepo.findById(id);
		
		if(optionalOrder.isEmpty()) {
			return null;
		}
		
		Order order = optionalOrder.get();
		if(!order.getUser().getEmail().equals(email)) {
			return null;
		}
		order.setStatus(newStatus);
		return orderRepo.save(order);
	}
	
	
	public void deleteOrderByAdmin(int id) {
		orderRepo.deleteById(id);
	}

}
