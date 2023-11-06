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

    protected void refresh() {
        // 获取Bean工厂
        DefaultListableBeanFactory beanFactory = obtainBeanFactory();
        // 把appconfig路径下的Bean扫描，注册到Bean工厂beanDefinitionMap
        invokeBeanFactoryPostProcessors(beanFactory);
        //初始化BeanDefinition代表的单例Bean，放到单例Bean的容器（缓存）里
        finishBeanFactoryInitialization(beanFactory);
    }

    /**
     * 初始化BeanDefinition代表的单例Bean，放到单例Bean的容器（缓存）里
     * @param beanFactory
     */
    private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) {
        //实例化剩下的所有单例对象(非懒加载的)
        this.beanFactory.preInstantiateSingletons();
    }

    /**
     * 把appconfig路径下的Bean扫描，注册到Bean工厂beanDefinitionMap
     * @param beanFactory
     */
    private void invokeBeanFactoryPostProcessors(DefaultListableBeanFactory beanFactory) {
        //此处简化类：实际Spring源码中doScan不在beanFactory中
        beanFactory.doScan();
    }

    /**
     * 获取Bean工厂
     * @return
     */
    private DefaultListableBeanFactory obtainBeanFactory() {
        return this.beanFactory;
    }

    public Object getBean(String beanName) {
        return this.beanFactory.getBean(beanName);
    }
}
