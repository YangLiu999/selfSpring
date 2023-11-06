package com.self.spring.applicationContext;

import com.self.spring.beanDefinition.AnnotatedBeanDefinition;
import com.self.spring.beanDefinition.AnnotatedBeanDefinitionReader;
import com.self.spring.beanDefinition.BeanDefinitionRegistry;

/**
 * ApplicationContext上下文
 * @author YL
 * @date 2023/11/02
 **/
public class AnnotationConfigApplicationContext
        extends GenericApplicationContext
        implements BeanDefinitionRegistry {

    private AnnotatedBeanDefinitionReader reader;


    /**
     * 隐式调用：如果调用此无参构造器，需要先调用父类无参构造,父类进行DefaultListableBeanFactory初始化
     */
    public AnnotationConfigApplicationContext(){
        this.reader = new AnnotatedBeanDefinitionReader(this);
    }

    public AnnotationConfigApplicationContext(Class<?> componentClass) {
        //1.读取componentClass 扫描路径所在的类AppConfig
        //专门定义一个阅读器读取 AnnotatedBeanDefinitionReader
        this();
        //2.将类AppConfig注册到Bean工厂中（BeanDefinition+beanDefinitionRegister+factory）
        register(componentClass);
        //3.扫描路径，提取出路径下所有Bean，注册到Bean工厂（单例Bean初始化）

    }

    private void register(Class<?> componentClass) {
        this.reader.register(componentClass);
    }

    @Override
    public void registerBeanDefinition(String beanName, AnnotatedBeanDefinition beanDefinition) {

    }
}
