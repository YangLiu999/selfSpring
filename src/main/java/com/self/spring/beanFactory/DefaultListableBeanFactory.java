package com.self.spring.beanFactory;

import com.self.spring.annotation.ComponentScan;
import com.self.spring.annotation.Scope;
import com.self.spring.annotation.Service;
import com.self.spring.beanDefinition.AnnotatedBeanDefinition;
import com.self.spring.beanDefinition.AnnotatedGenericBeanDefinition;
import com.self.spring.beanDefinition.BeanDefinitionRegistry;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YL
 * @date 2023/11/06
 **/
public class DefaultListableBeanFactory implements BeanDefinitionRegistry,BeanFactory{

    private static final Map<String,AnnotatedBeanDefinition> beanDefinitionMap
            = new ConcurrentHashMap<>(256);

    //记录BeanDefinitionName
    private List<String> beanDefinitionNames = new ArrayList<>();

    //单例池
    private static final Map<String,Object> singletonObjects
            = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, AnnotatedBeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }


    /**
     * 路径扫描
     */
    public void doScan() {
        for (String beanName : beanDefinitionMap.keySet()) {
            AnnotatedGenericBeanDefinition definition =
                    (AnnotatedGenericBeanDefinition) beanDefinitionMap.get(beanName);
                if (definition.getClazz().isAnnotationPresent(ComponentScan.class)){
                    ComponentScan componentScan = (ComponentScan) definition.getClazz().getAnnotation(ComponentScan.class);
                    //获取扫描路径
                    String basePackage = componentScan.value();
                    URL resource = this.getClass().getClassLoader()
                            .getResource(basePackage.replace(".","/"));
                    System.out.println(resource);
                    //获取文件
                    File file = new File(resource.getFile());
                    if (file.isDirectory()){
                        for (File f : file.listFiles()) {
                            try {
                                Class clazz = this.getClass()
                                        .getClassLoader()
                                        .loadClass(basePackage.concat(".").concat(f.getName().split("\\.")[0]));
                                //注解判断
                                if (clazz.isAnnotationPresent(Service.class)){
                                    String name = ((Service)clazz.getAnnotation(Service.class)).value();
                                    AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition();
                                    beanDefinition.setClazz(clazz);
                                    if (clazz.isAnnotationPresent(Scope.class)){
                                        beanDefinition.setScope(((Scope)clazz.getAnnotation(Scope.class)).value());
                                    }else {
                                        //默认
                                        beanDefinition.setScope("singleton");
                                    }
                                    //beanDefinitionMap存在其他的BeanName,有干扰项
                                    beanDefinitionMap.put(name,beanDefinition);
                                    //需要地方记录真正定义的Bean
                                    beanDefinitionNames.add(name);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        }
        System.out.println("test");
    }

    /**
     * 创建对象然后放到singletonObjects中
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName);
    }

    private Object doGetBean(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (bean != null) return bean;
        //根据BeanDefinition创建Bean
        AnnotatedGenericBeanDefinition annotatedBeanDefinition
                = (AnnotatedGenericBeanDefinition) beanDefinitionMap.get(beanName);
        //createBean完成BeanDefinition转真正实体对象
        Object cbean = createBean(beanName,annotatedBeanDefinition);
        //防止动态加载的单例类被遗漏
        if (annotatedBeanDefinition.getScope().equals("singleton")) {
            singletonObjects.put(beanName, cbean);
        }
        return cbean;
    }

    /**
     * createBean完成BeanDefinition转真正实体对象
     * @param beanName
     * @param annotatedBeanDefinition
     * @return
     */
    private Object createBean(String beanName, AnnotatedGenericBeanDefinition annotatedBeanDefinition) {
        try {
            return annotatedBeanDefinition.getClazz().getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实际方法位置阅读源码
     */
    public void preInstantiateSingletons() {
        //初始化自定义的Bean，所有自定义的BeanName：beanDefinitionNames
        //beanDefinitionNames处于一个并发环境中，如果直接使用beanDefinitionNames进行for循环，
        // 循环过程中，一旦其他线程访问beanDefinitionNames.add(beanName)方法会导致for循环失败(modCount),
        // 所以此处创建一个新的List集合来进行for循环，防止beanDefinitionNames并发环境下的add操作
            List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
        for (String beanName : beanNames) {
            //如果扫描后有动态加载的单例Bean class记载到JVM会被遗漏
            AnnotatedGenericBeanDefinition beanDefinition
                    = (AnnotatedGenericBeanDefinition) beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")){
                //创建Bean，将单例对象保存到我们的单例池（内存缓存）中
                //确保创建bean的时候不会遗漏，所以逻辑放到getBean
                getBean(beanName);
            }

        }

    }
}
