package com.rota.rotakamp.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final String APPLICATION_JSON_VALUE = "application/json";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	if (request.getServletPath().equals("/login") || request.getServletPath().equals("/api/token/refresh")) {
	    filterChain.doFilter(request, response);
	} else {
	    String authorizationHeader = request.getHeader("AUTHORIZATION");
	    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		try {
		    String token = authorizationHeader.substring("Bearer ".length());
		    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		    JWTVerifier verifier = JWT.require(algorithm).build();
		    DecodedJWT decodedJWT = verifier.verify(token);
		    String username = decodedJWT.getSubject();
		    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
		    Collection<SimpleGrantedAuthority> authories = new ArrayList<SimpleGrantedAuthority>();
		    java.util.Arrays.stream(roles).forEach(role -> {
			authories.add(new SimpleGrantedAuthority(role));
		    });
		    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			    username, null, authories);
		    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		    filterChain.doFilter(request, response);
		} catch (Exception e) {
		    log.error("Error logging in:{}", e.getMessage());
		    response.setHeader("error", e.getMessage());
		    response.setStatus(403);
		    // response.sendError(403);
		    Map<String, String> tokens = new HashMap<String, String>();
		    tokens.put("error_message", e.getMessage());
		    response.setContentType(APPLICATION_JSON_VALUE);
		    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		}
	    } else {
		filterChain.doFilter(request, response);
	    }
	}
    }

}
