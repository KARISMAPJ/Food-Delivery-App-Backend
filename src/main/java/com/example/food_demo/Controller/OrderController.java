package com.example.food_demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.food_demo.entity.Order;
import com.example.food_demo.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@PostMapping("/place/{foodId}")	
	public ResponseEntity<?>placeOrder(@PathVariable int foodId,@RequestBody Order orderRequest, Authentication auth){
		String email = auth.getName();
		int quantity = orderRequest.getQuantity();
		Order order =service.placeOrder(foodId,quantity, email);
		
		if(order == null) {
			return ResponseEntity.badRequest().body("Food not Found!");
		}
		
		return ResponseEntity.ok(order);
		
	}
	
	@GetMapping("/myorders")
	public ResponseEntity<?>getMyOrders(Authentication auth){
		String email = auth.getName();
		List<Order>list = service.getMyOrder(email);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?>getAllOrders(){
		return ResponseEntity.ok(service.getAllOrder());
	}
	
	@DeleteMapping("/deleteorder/{id}")
	public ResponseEntity<?>deleteOrder(@PathVariable int id,Authentication auth){
		String email = auth.getName();
		boolean deleted = service.deleteOrder(id,email);
		
		if(!deleted) {
			return ResponseEntity.status(403).body("Not allowedto delete this order");
			}
		
		return ResponseEntity.ok("Order deleted Successfully");
		
	}
	
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateOrderStatus(
	        @PathVariable int id,
	        @RequestBody Order orderRequest,
	        Authentication auth) {

	    String email = auth.getName();
	    String newStatus = orderRequest.getStatus();
	    Order updated = service.updateOrderStatus(id, newStatus,email);

	    if (updated == null) {
	        return ResponseEntity.status(403).body("Not allowed to update this order");
	    }

	    return ResponseEntity.ok(updated);
	}

	

}
