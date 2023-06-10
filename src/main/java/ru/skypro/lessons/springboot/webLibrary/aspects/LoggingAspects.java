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
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspects.class);


    @Around("execution(* ru.skypro.lessons.springboot.webLibrary.service.*.*(..))")
    public Object logEmployeeServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        if (args.length != 0)
            logger.info("Was invoked method: " + methodName + " that take: " + Arrays.toString(args) + ".");
        Object returnedByMethod = proceedingJoinPoint.proceed();
        if (returnedByMethod != null)
            logger.info("Was invoked method: " + methodName + " that returns: " + returnedByMethod + ".");
        return returnedByMethod;
    }

    @Around("execution(* ru.skypro.lessons.springboot.webLibrary.repository.*.*(..))")
    public Object logEmployeeRepositories(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object returnedByMethod = proceedingJoinPoint.proceed();
        if (returnedByMethod == null)
            logger.debug("Successfully invoked method: " + methodName + ".");
        else
            logger.debug("Successfully invoked method: " + methodName + " and returns: " + returnedByMethod + ".");

        return returnedByMethod;
    }
}


