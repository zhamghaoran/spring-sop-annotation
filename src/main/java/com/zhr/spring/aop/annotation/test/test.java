package com.zhr.spring.aop.annotation.test;

import com.zhr.spring.aop.annotation.Calculator;
import com.zhr.spring.aop.annotation.CalculatorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import java.nio.channels.Pipe;

public class test {
    @Test
    public void testAOP() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("Aop-annotation.xml");
        Calculator calculator = ioc.getBean(Calculator.class);
        calculator.div(10,1);
    }
}
