package com.example.food_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.food_demo.repositroy.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
	
	@Autowired
	private UserRepository repository;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		
		com.example.food_demo.entity.User user = repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
		
		return User.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities("USER")
				.build();
	}

}
