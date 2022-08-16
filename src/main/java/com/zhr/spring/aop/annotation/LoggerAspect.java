package com.zhr.spring.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * 在切面中需要通过指定的注解将方法标识为通知方法
 * {@code @Before:前置通知在目标方法执行之前执行}
 * {@code @After} :后置通知，在目标方法的finally字句中执行的
 *  {@code @AfterReturning} : 返回通知，在目标对象返回值之后执行
 * {@code @AfterThrowing} :异常通知，在目标对象方法的catch字句中执行
 *
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
 *
 * 切面的优先级：
 * {@code @Order属性来设置优先级，默认值为int的最大值，越小优先级越高}
 *
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
    public void afterAdviceMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect,方法：" + signature.getName() + ",执行完毕");
    }
    // 在返回通知中若要获取目标对象方法的返回值
    // 只需要通过@AfterReturning注解的returning属性值
    //就可以将通知方法的某个参数指定为接收目标对象方法的返回参数
    @AfterReturning(value = "pointcut()",returning = "result")
    public void afterReturningAdviceMethod(JoinPoint joinPoint,Object result) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect,方法：" + signature.getName() + ", 结果 " + result);
    }

    // 在返回通知中若要获取目标对象方法的异常
    // 只需要通过@AfterReturning注解的throwing属性值
    //就可以将通知方法的某个参数指定为接收目标对象方法出现异常的参数
    @AfterThrowing(value = "pointcut()",throwing = "exception")
    public void afterThrowingAdviceMethod(JoinPoint joinPoint,Throwable exception) {
        Signature signature = joinPoint.getSignature();
        System.out.println("LoggerAspect,方法：" + signature.getName() + ", 异常通知 ：" + exception.toString());
    }
    @Around("pointcut()")
    // 环绕通知方法的返回值一定要与目标对象方法的返回值一致
    public Object aroundAdviceMethod(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;

        try {
            System.out.println("环绕通知 --> 前置通知");
            //目标方法的执行
            result = proceedingJoinPoint.proceed();
            System.out.println("环绕通知 --> 返回通知");
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("环绕通知 --> 异常通知");
        } finally {
            System.out.println("环绕通知 --> 后置通知");
        }
        return result;
    }
}
