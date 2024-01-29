package com.ins.web.security.config;
import com.ins.web.security.filter.JwtFilter;
import com.ins.web.security.service.UserInfoService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public UserDetailsService userDetailsService(){
		logger.log(Level.INFO, "From Security Config class -> START-END -> (SecurityConfig)-> (userDetailsService)");
        return new UserInfoService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		logger.log(Level.INFO, "From Security Config class -> START-END -> (SecurityConfig)-> (securityFilterChain)");
        return httpSecurity.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/welcome","/auth/addUser","/auth/login","/api/masterUsers/adduser","/api/masterUsers/getAllUsers","/api/masterUsers/search","/api/masterUsers/update","/api/masterUsers/update","/api/masterProjects/getAllProjects","/api/masterProjects/addProject","/api/masterProjects/update")
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
		logger.log(Level.INFO, "From Security Config class -> START -> (SecurityConfig)-> (authenticationProvider)");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
		logger.log(Level.INFO, "From Security Config class -> END -> (SecurityConfig)-> (authenticationProvider)");
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
		logger.log(Level.INFO, "From Security Config class -> START-END -> (SecurityConfig)-> (passwordEncoder)");
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		logger.log(Level.INFO, "From Security Config class -> START-END -> (SecurityConfig)-> (authenticationManager)");
        return config.getAuthenticationManager();
    }

}
