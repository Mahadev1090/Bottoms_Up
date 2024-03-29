package com.ins.web.security.filter;

import com.ins.web.security.service.JwtService;
import com.ins.web.security.service.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LogManager.getLogger(JwtFilter.class);

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserInfoService userInfoService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		logger.log(Level.INFO, "From JwtFilter class -> START -> (JwtFilter)-> (doFilterInternal)");
        String authHeader = request.getHeader("Authorization");
        String token= null;
        String userName = null;
        if(authHeader !=null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
            userName =jwtService.extractUserName(token);
        }
        if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userInfoService.loadUserByUsername(userName);
            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
		logger.log(Level.INFO, "From JwtFilter class -> END -> (JwtFilter)-> (doFilterInternal)");

    }
}
