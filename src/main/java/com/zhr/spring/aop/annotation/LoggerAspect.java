package com.zhr.spring.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 在切面中需要通过指定的注解将方法标识为通知方法
 * {@code @Before:前置通知在目标方法执行之前执行}
 * <p>
 * 切入点表达式：设置在标识通知的注解value属性中
 * execution(public int com.zhr.spring.aop.annotation.CalculatorImpl.add(int,int))
 * execution(* com.zhr.spring.aop.annotation.CalculatorImpl.*(..))
 * 第一个*表示任意访问的修饰符和返回值类型
 * 第二个*表示类中任意的方法
 * .. 表示任意的参数列表
 * 类的地方也可以使用*，表表示包下所有的类
 *
 * 重用切入点表达式
 *  @ Pointcut声明一个公共的切入点表达式
 *  @ Pointcut("execution(* com.zhr.spring.aop.annotation.CalculatorImpl.*(..))")
 *     public void pointcut() {
 *     }
 *使用方式 @Before("pointcut()")
 *
 *
 * 获取连接点的信息
 * 在通知方法的参数位置，设置joinpoint类型的参数，就可以获取连接点所对应的方法信息
 * //获取连接点所对应的方法的签名信息
 * Signature signature = joinPoint.getSignature();
 * //获取连接点所对应的方法的参数
 * Object[] args = joinPoint.getArgs();
 */
@Component
@Aspect // 将当前组件标识为切面
public class LoggerAspect {
    @Pointcut("execution(* com.zhr.spring.aop.annotation.CalculatorImpl.*(..))")
    public void pointcut() {
    }


    //    @Before("execution(public int com.zhr.spring.aop.annotation.CalculatorImpl.add(int,int))")
//    @Before("execution(* com.zhr.spring.aop.annotation.CalculatorImpl.*(..))") //这个类下的所有方法都包含在内
    @Before("pointcut()")
    public void beforeAdviceMethod(JoinPoint joinPoint) {
        //获取连接点所对应的方法的签名信息
        Signature signature = joinPoint.getSignature();
        //获取连接点所对应的方法的参数
        Object[] args = joinPoint.getArgs();
        System.out.println("LoggerAspect,方法：" + signature.getName() + " , 参数" + Arrays.toString(args));
    }

    @After("pointcut()")
    public void afterAdviceMethod() {

    }

}
