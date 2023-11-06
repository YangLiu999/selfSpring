package com.self.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仿写Spring ComponentScan注解
 * @author YL
 * @date 2023/11/02
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
    /**
     * scope默认单例
     * @return
     */
    String value() default "";
}
