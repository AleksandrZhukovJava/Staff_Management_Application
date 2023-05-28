package ru.skypro.lessons.springboot.webLibrary.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());
    @Around("execution(* ru.skypro.lessons.springboot.webLibrary..*.*(..))")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Class<?> className = proceedingJoinPoint.getSignature().getDeclaringType();
        Object[] args = proceedingJoinPoint.getArgs();
        logger.info("Class/Method: {" + className + "}{" + methodName + "} starting with args: {" + Arrays.toString(args) + "}.");
        Object returnedByMethod = proceedingJoinPoint.proceed();
        logger.info("Class/Method: {" + className + "}{" + methodName + "} was finished and returns: {" + returnedByMethod + "}.");
        return returnedByMethod;
    }
}
