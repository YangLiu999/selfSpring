package com.self.spring.beanDefinition;

/**
 * @author YL
 * @date 2023/11/03
 **/
public class AnnotatedGenericBeanDefinition implements AnnotatedBeanDefinition{

    private Class clazz;
    private String scope;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }



}
