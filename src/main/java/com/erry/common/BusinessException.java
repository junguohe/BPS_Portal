package com.erry.common;

import java.util.Collection;
import java.util.Map;

/**
 * 业务异常
 * @author Lishiyong
 * @date 2015年11月12日
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static final String PREFIX = "business_exception_";
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(Throwable e){
		super(e);
	}
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(int errorCode) {
		super(MessageHandler.getMessage(BusinessException.PREFIX + errorCode));
	}
	
	public BusinessException(int errorCode, String... args) {
		super(MessageHandler.getMessage(BusinessException.PREFIX + errorCode, args));
	}
	
	public BusinessException(Collection<Map<Integer, String[]>> excepCol) {
		super(genMessages(excepCol));
	}
	
	private static String genMessages(Collection<Map<Integer, String[]>> excepCol) {
		StringBuffer sb = new StringBuffer();
		for (Map<Integer, String[]> excepMap : excepCol) {
			Integer code = excepMap.keySet().iterator().next();
			if (sb.length() > 0)
				sb.append("<br/>");
			sb.append(MessageHandler.getMessage(BusinessException.PREFIX + code, excepMap.get(code)));
		}
		return sb.toString();
	}
	
}
