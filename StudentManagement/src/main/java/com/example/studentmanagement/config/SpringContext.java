package com.example.studentmanagement.config;



import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    // 初始化 Spring 应用上下文
    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(AppConfig.class);

    /**
     * 获取 Spring 管理的 Bean 实例
     *
     * @param requiredType 需要获取的 Bean 类型
     * @param <T>          Bean 类型
     * @return Bean 实例
     */
    public static <T> T getBean(Class<T> requiredType) {
        return CONTEXT.getBean(requiredType);
    }
}

