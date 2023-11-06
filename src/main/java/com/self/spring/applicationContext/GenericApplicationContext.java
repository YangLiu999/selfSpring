package com.self.spring.applicationContext;

import com.self.spring.beanDefinition.AnnotatedBeanDefinition;
import com.self.spring.beanDefinition.BeanDefinitionRegistry;
import com.self.spring.beanFactory.DefaultListableBeanFactory;

/**
 * @author YL
 * @date 2023/11/06
 **/
public class GenericApplicationContext implements BeanDefinitionRegistry {

    private DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext(){
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, AnnotatedBeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName,beanDefinition);
    }
}
