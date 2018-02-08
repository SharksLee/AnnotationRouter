package com.example.annatationlib;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由注解
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface RouterTarget {
    String value();
}