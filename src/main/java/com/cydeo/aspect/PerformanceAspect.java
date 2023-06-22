package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Pointcut ("@annotation(com.cydeo.annotation.ExecutionTime)")
    public void executionTimePC (){}

    @Around("executionTimePC()")
    public Object aroundAnyExecutionTimeAdvice (ProceedingJoinPoint proceedingJoinPoint) { //if it is Around, we need to provide ProceedingJoinPOint parameter.
        long beforeTime = System.currentTimeMillis(); //current real time in milli-seconds;
        Object result= null;
        log.info("Execution starts:");

        try {
            result = proceedingJoinPoint.proceed();
        } catch(Throwable throwable){
            throwable.printStackTrace();
        }

        long afterTime = System.currentTimeMillis(); //when my method ended up in milli-seconds
        log.info("Time taken to execute: {} ms - Method: {}", //first curly-braces will be the first parameter
                (afterTime-beforeTime), proceedingJoinPoint.getSignature().toShortString());

        return result;
    }
}


