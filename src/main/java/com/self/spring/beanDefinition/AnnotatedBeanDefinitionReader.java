package com.self.spring.beanDefinition;

import com.self.spring.annotation.Scope;

/**
 * @author YL
 * @date 2023/11/02
 **/
public class AnnotatedBeanDefinitionReader {
    /**
     * 注册器
     */
    private BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }
    /**
     * 注册，路径扫描这个Bean到BeanFactory中
     * @param componentClass
     */
    public void register(Class<?> componentClass) {
        registerBean(componentClass);
    }

    private void registerBean(Class<?> componentClass) {
        doRegisterBean(componentClass);
    }

    private void doRegisterBean(Class<?> componentClass) {
        //将AppConfig读成一个BeanDefinition
        AnnotatedGenericBeanDefinition beanDefinition
                = new AnnotatedGenericBeanDefinition();
        beanDefinition.setClazz(componentClass);
        //设置scope属性
        if (componentClass.isAnnotationPresent(Scope.class)){
            String scope = componentClass.getAnnotation(Scope.class).value();
            beanDefinition.setScope(scope);
        } else {
            beanDefinition.setScope("singleton");
        }
        //beanDefinition创建完成后交给beanFactory注册
        //注册beanDefinition
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinition,this.registry);
    }
}
