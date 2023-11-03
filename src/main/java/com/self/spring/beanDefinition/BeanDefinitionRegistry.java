package com.self.spring.beanDefinition;

/**
 * 注册器接口
 * @author YL
 * @date 2023/11/03
 **/
public interface BeanDefinitionRegistry {
    /**
     * 通知注册beanDefinition
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName,
                                AnnotatedBeanDefinition beanDefinition);
}
