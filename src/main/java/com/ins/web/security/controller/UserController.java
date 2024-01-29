package com.ins.web.security.controller;

import com.ins.web.security.entity.AuthRequest;
import com.ins.web.security.entity.UserInfo;
import com.ins.web.security.service.JwtService;
import com.ins.web.security.service.UserInfoService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome(){
		logger.log(Level.INFO, "From UserController class -> START-END -> (UserController)-> (welcome)");
        return "Welcome to Bottoms-Up !!";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo){
		logger.log(Level.INFO, "From UserController class -> START-END -> (UserController)-> (addUser)");
        return userInfoService.addUser(userInfo);
    }
    
    @PostMapping("/login")
    public String userLogin(@RequestBody AuthRequest authRequest){
		logger.log(Level.INFO, "From UserController class -> START -> (UserController)-> (userLogin)");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
    		logger.log(Level.INFO, "From UserController class -> END -> (UserController)-> (userLogin)");
            return jwtService.generateToken(authRequest.getUserName());
        }else {
    		logger.log(Level.INFO, "From UserController class -> Invalid user request -> (UserController)-> (userLogin)");
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
    
    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public List<UserInfo> getAllUsers(){
		logger.log(Level.INFO, "From UserController class -> START-END -> (UserController)-> (getAllUsers)");
        return userInfoService.getAllUser();
    }
    
    @GetMapping("/getUsers/{id}")
    @PreAuthorize("hasAuthority('USER_ROLES')")
    public UserInfo getAllUsers(@PathVariable Integer id){
		logger.log(Level.INFO, "From UserController class -> START-END -> (UserController)-> (getAllUsers-by-id)");
        return userInfoService.getUser(id);
    }
}
