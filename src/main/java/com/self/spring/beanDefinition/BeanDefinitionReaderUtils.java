package com.self.spring.beanDefinition;

import com.self.spring.util.CommonUtils;

/**
 * @author YL
 * @date 2023/11/03
 **/
public class BeanDefinitionReaderUtils {
    /**
     * 通知注册器注册beanDefinition
     * @param beanDefinition
     * @param registry
     */
    public static void registerBeanDefinition(
            AnnotatedBeanDefinition beanDefinition,
            BeanDefinitionRegistry registry) {
        //beanName is AppConfig需要将首字母修改为小写
        String beanName = CommonUtils.toLowerCaseFirstOne(
                ((AnnotatedGenericBeanDefinition)beanDefinition)
                        .getClazz()
                        .getSimpleName());
        registry.registerBeanDefinition(beanName,beanDefinition);
    }
}
