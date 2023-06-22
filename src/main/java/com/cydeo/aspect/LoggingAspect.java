package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //Logger logger = LoggerFactory.getLogger(LoggingAspect.class); @Slf4j this annotation gives absolutely the same result
    //first * means any return type; inside the ProjectController any method, with any kind/number of parameters or
    //same type execution in TaskController

    private String getUsername (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
        //these three lines gives availability to track  the logged-in username in KeyCloak.
    }
    @Pointcut ("execution(* com.cydeo.controller.ProjectController.*(..)) ||execution(* com.cydeo.controller.TaskController.*(..)) ")
    //Target object will be TaskController, and will create one Proxy object from TaskController. Joint points are all TaskController methods.
    public void anyProjectAndTaskControllerPC (){}

    //then we need to create "Advice"
    @Before("anyProjectAndTaskControllerPC()")
    public void beforeAnyProjectAndTaskControllerAdvice (JoinPoint joinPoint){
        //I want to see which method is running and which user runs that method
        log.info("Before -> Method: {}, User: {}",
                joinPoint.getSignature().toShortString(),
                getUsername()); //we'll create one method getUsername to get info about logged-in user in Keycloak.

    }


    @AfterReturning(pointcut = "anyProjectAndTaskControllerPC()", returning = "results")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Object results){
        log.info("After Returning -> Method: {}, User: {}, Results: {}",
                joinPoint.getSignature().toShortString(),
                getUsername());
    }

    @AfterThrowing(pointcut = "anyProjectAndTaskControllerPC()", throwing = "exception")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Exception exception){
        log.info("After Throwing -> Method: {}, User: {}, Results: {}",
                joinPoint.getSignature().toShortString(),
                getUsername(),
                exception.getMessage());
    }

}
