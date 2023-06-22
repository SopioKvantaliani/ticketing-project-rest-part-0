package com.cydeo.annotation;

import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //I want to see how fast my method works
@Retention(RetentionPolicy.RUNTIME) //adding retention policy
public @interface ExecutionTime {

}
