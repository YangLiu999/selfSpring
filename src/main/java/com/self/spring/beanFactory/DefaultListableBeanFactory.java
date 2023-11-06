package com.self.spring.beanFactory;

import com.self.spring.beanDefinition.AnnotatedBeanDefinition;
import com.self.spring.beanDefinition.BeanDefinitionRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YL
 * @date 2023/11/06
 **/
public class DefaultListableBeanFactory implements BeanDefinitionRegistry,BeanFactory{

    private static final Map<String,AnnotatedBeanDefinition> beanDefinitionMap
            = new ConcurrentHashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, AnnotatedBeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
