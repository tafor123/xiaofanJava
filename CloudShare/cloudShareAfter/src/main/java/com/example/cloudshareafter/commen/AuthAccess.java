package com.example.cloudshareafter.commen;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 权限放开作用
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
}
