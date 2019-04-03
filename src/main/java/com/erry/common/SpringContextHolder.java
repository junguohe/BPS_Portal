package com.erry.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext ac;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}
	
	public static Object getBean(String beanName) throws BeansException {
		return ac.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> cls) throws BeansException {
		return ac.getBean(cls);
	}
	
}
