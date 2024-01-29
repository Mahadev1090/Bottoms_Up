package com.ins.web.security.date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

    public String getCurrentUsername() {
		logger.log(Level.INFO, "From AuthenticationService class -> START -> (AuthenticationService)-> (getCurrentUsername)");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
    		logger.log(Level.INFO, "From AuthenticationService class -> END -> (AuthenticationService)-> (getCurrentUsername)");
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
		logger.log(Level.INFO, "From AuthenticationService class -> END-ERROR -> (AuthenticationService)-> (getCurrentUsername)");

        return "unknown";
    }
}
