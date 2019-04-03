package com.erry.common;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 取得国际化资源文件信息
 * @author Lishiyong
 * @date 2015年11月12日
 */
@Component
public class MessageHandler implements ApplicationContextAware {

	private static final Locale LOCALE_CHINA = Locale.CHINA;	//zh_CN
	
	private static final Locale LOCALE_US = Locale.US;	//en_US
	
	private static final Locale LOCALE_DEFAULT = LOCALE_CHINA;
	
	private static ApplicationContext ac;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}
	
	public static String getMessage(String key) {
		return getMessage(key, new String[0]);
	}
	
	public static String getMessage(String key, String[] args) {
		return getMessage(key, args, getSysLocale());
	}
	
	public static String getMessage(String key, Locale locale) {
		return getMessage(key, null, locale);
	}
	
	public static String getMessage(String key, Object[] args, Locale locale) {
		return ac.getMessage(key, args, locale);
	}
	
	public static Locale getSysLocale() {
		// get the locale of current user
		return LOCALE_DEFAULT;
	}

}
