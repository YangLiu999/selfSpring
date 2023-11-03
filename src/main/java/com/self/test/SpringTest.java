package com.self.test;

import com.self.spring.applicationContext.AnnotationConfigApplicationContext;
import com.self.test.config.AppConfig;

/**
 * spring test类
 * @author YL
 * @date 2023/11/02
 **/
public class SpringTest {

    public static void main(String[] args) {

        //create applicationContext（注解）
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        //调用getBean
        //applicationContext.getBean("");
    }
}
