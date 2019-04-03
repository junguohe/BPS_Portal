package com.erry.util;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class MessageContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext ac;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}

	
	public static String getMessage(String code, Object[] args, Locale locale){
		return ac.getMessage(code, args, locale);
	}
	
}
