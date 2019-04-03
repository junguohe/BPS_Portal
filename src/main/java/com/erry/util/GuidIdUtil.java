package com.erry.util;

import java.util.Random;


public class GuidIdUtil {
	public static String getGuId() {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < 32; i++) {
			if(i==9){
				val+="-";
			}
			if(i==14){
				val+="-";
			}
			if(i==19){
				val+="-";
			}
			if(i==24){
				val+="-";
			}
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val.toUpperCase();
	}
	
	public static String getPassword() {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val.toUpperCase();
	}
}
