package com.example.food_demo.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)throws ServletException,IOException{
			
			String authHeader = request.getHeader("Authorization");
			String token = null;
			String email = null;
			
		
			
			if(authHeader != null && authHeader.startsWith("Bearer")) {
				token = authHeader.substring(7);
				try {
					email = jwtUtil.extractEmail(token);
				}catch (Exception e) {
					System.out.println("Invalid Token!");
				}
				
		}
			
			if(email != null && jwtUtil.validateToken(token) && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				
				
//				String role = jwtUtil.extractRole(token);
//				UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(email,null,List.of(authority));
//				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authtoken);
				
				
			            // Extract role from token
			            String role = jwtUtil.extractRole(token);
			            SimpleGrantedAuthority authority =
			                    new SimpleGrantedAuthority("ROLE_" + role);

			            UsernamePasswordAuthenticationToken authenticationToken =
			                    new UsernamePasswordAuthenticationToken(
			                            email,
			                            null,
			                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
			                    );

			            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
			
			filterChain.doFilter(request, response);
		
	}

}
