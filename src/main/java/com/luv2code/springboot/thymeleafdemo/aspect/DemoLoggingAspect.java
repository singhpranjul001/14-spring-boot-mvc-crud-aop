package com.luv2code.springboot.thymeleafdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    //setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    //setup pointcut declarations for controller
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    private void forController() {}

    //setup pointcut declarations for service
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    private void forService() {}

    //setup pointcut declarations for dao
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..))")
    private void forDao() {}

    @Pointcut("forController() || forService() || forDao()")
    private void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint){

        //display method we are calling
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info("====> in @Before: calling method: " + method);

        //display the arguments to the method

        //get the arguments
        Object[] args = theJoinPoint.getArgs();

        //loop through to display the args
        for (Object tempArgs: args) {
            myLogger.info("====> arguments: " + tempArgs);
        }
    }

    //add @AfterReturning Advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult){

        //display the method we are returning from
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info("====> in @AfterReturning: from method: " + method);

        //display the data returned
        myLogger.info("====> result: " + theResult);

    }
}
