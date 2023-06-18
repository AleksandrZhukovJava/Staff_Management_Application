package ru.skypro.lessons.springboot.webLibrary.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspects {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspects.class);


    @Around("execution(* ru.skypro.lessons.springboot.webLibrary.service.*.*.*(..))")
    public Object logEmployeeServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        if (args.length != 0){
            LOGGER.info("Was invoked method: {} that take: {}.",methodName,Arrays.toString(args));
        }
        Object returnedByMethod = proceedingJoinPoint.proceed();
        if (returnedByMethod != null){
            LOGGER.info("Was invoked method: {} that returns: {}.",methodName,returnedByMethod );
        }
        return returnedByMethod;
    }

    @Around("execution(* ru.skypro.lessons.springboot.webLibrary.repository.*.*(..))")
    public Object logEmployeeRepositories(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object returnedByMethod = proceedingJoinPoint.proceed();
        if (returnedByMethod == null){
            LOGGER.debug("Successfully invoked method: {}.",methodName);
        }
        else {
            LOGGER.debug("Successfully invoked method: {} and returns: {}.",methodName,returnedByMethod);
        }

        return returnedByMethod;
    }
}


