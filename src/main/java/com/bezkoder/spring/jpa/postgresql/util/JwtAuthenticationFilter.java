package com.bezkoder.spring.jpa.postgresql.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/products/*") // Filter requests to the Product API
public class JwtAuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		 String token = ((HttpServletRequest) request).getHeader("Authorization");
		 
		         if (token != null && token.startsWith("Bearer ")) {
		             token = token.substring(7); // Remove "Bearer " prefix
		 
		             // Validate the JWT token
		             if (JwtUtil.verifyJwt(token) != null) {
		                 // If the token is valid, continue to the next filter or API endpoint
		                 chain.doFilter(request, response);
		             } else {
		                 ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Invalid token
		                 response.getWriter().write("Unauthorized: Invalid token");
		             }
		         } else {
		             ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED); // No token provided
		             response.getWriter().write("Unauthorized: No token provided");
		         }
		
	}
}
