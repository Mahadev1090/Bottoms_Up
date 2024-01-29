package com.ins.web.security.service;

import com.ins.web.security.entity.UserInfo;
import com.ins.web.security.repository.UserInfoRepository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
	private static final Logger logger = LogManager.getLogger(UserInfoService.class);
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.log(Level.INFO, "From JwtFilter class -> START -> (UserInfoService)-> (loadUserByUsername)");
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
		logger.log(Level.INFO, "From JwtFilter class -> END -> (UserInfoService)-> (loadUserByUsername)");
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
    }
    public String addUser(UserInfo userInfo){
		logger.log(Level.INFO, "From JwtFilter class -> START -> (UserInfoService)-> (addUser)");
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
		logger.log(Level.INFO, "From JwtFilter class -> END -> (UserInfoService)-> (addUser)");
        return "User added successfully";
    }
    public List<UserInfo> getAllUser(){
		logger.log(Level.INFO, "From JwtFilter class -> START-END -> (UserInfoService)-> (getAllUser)");
         return userInfoRepository.findAll();
    }
    public UserInfo getUser(Integer id){
		logger.log(Level.INFO, "From JwtFilter class -> START-END -> (UserInfoService)-> (getUser)");
        return userInfoRepository.findById(id).get();
    }
}
