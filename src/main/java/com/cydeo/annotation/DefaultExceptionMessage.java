package com.cydeo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultExceptionMessage {

    String defaultMessage() default "";

}

/*
I can add this annotation (@defaultExceptionMessage) on top of any method and it's gonna provide default message, here which is empty.
Otherwise, I can use DTO DefaultException message, on each method.
 */