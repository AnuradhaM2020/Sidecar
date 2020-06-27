package com.sidecar.codingtest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sidecar.codingtest.service.JwtUtil;
import com.sidecar.codingtest.service.UserDetailService;
import com.sidecar.codingtest.constants.*;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	UserDetailService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authorization = request.getHeader(SecurityConstants.HEADER_STRING);
		String jwtToken = null;
		String username = null;
		
		if (authorization != null && authorization.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			jwtToken = authorization.substring(7);

			try {
				username = jwtUtil.extractUsername(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get the jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("Jwt token expired");
			}
		} else {
			System.out.println("");
		}

		// validate token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailService.loadUserByUsername(username);
		
			// if token is valid configure Spring Security to manually set authentication
		
			if (jwtUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamAuthenticationToken);
			}
		}
			filterChain.doFilter(request, response);
	}

}
