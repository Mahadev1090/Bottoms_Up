package com.ins.web.security.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ins.web.security.date.AuthenticationService;
import com.ins.web.security.date.DateTimeProvider;
import com.ins.web.vo.MasterUserVo;

@Aspect
@Component
public class UpdateInfoAspect {

	private static final Logger logger = LogManager.getLogger(UpdateInfoAspect.class);

    @Autowired
    private AuthenticationService authenticationService; 

    @Autowired
    private DateTimeProvider dateTimeProvider; 

    @Before("execution(* com.ins.web.controller.MasterUserController.*(..))") // Adjust the package and method name
    public void beforeUpdate(JoinPoint joinPoint) {
		logger.log(Level.INFO, "From UpdateInfoAspect class -> START -> (UpdateInfoAspect)-> (beforeUpdate)");

        // Get the current user and update the fields
        String currentUser = authenticationService.getCurrentUsername();
        String currentDateTime = dateTimeProvider.getCurrentDateTime(); 

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof MasterUserVo) { 
                MasterUserVo masterUser = (MasterUserVo) arg;
                masterUser.setUpdatedBy(currentUser);
                masterUser.setUpdatedOn(currentDateTime);
            }
        }
		logger.log(Level.INFO, "From UpdateInfoAspect class -> END -> (UpdateInfoAspect)-> (beforeUpdate)");
    }
}
