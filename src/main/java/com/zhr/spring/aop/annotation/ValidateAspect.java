package com.zhr.spring.aop.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1) // 切面的优先级,越小优先级越高，默认值为int的最大值
public class ValidateAspect {
//    @Pointcut("execution(* com.zhr.spring.aop.annotation.CalculatorImpl.*(..))")
//    public void pointCut(){}
    @Before("com.zhr.spring.aop.annotation.LoggerAspect.pointcut()")
    public void beforeMethod() {
        System.out.println("ValidateAspect --> 前置通知");
    }
}
