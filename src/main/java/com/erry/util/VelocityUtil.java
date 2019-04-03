package com.erry.util;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.Velocity;
//import org.apache.velocity.exception.ParseErrorException;
//import org.apache.velocity.exception.ResourceNotFoundException;
//

public class VelocityUtil {
	
	private static VelocityUtil instance = null;
	
	public static VelocityUtil getInstance() {
        if (instance == null) {
            instance = new VelocityUtil();
        }
        return instance;
    }
	
	private VelocityUtil() {
//		 initiate Velocity engine
		try {
			Velocity.setProperty("input.encoding", "UTF-8");
			Velocity.setProperty("output.encoding", "UTF-8");
			Velocity.setProperty("resource.loader", "class");
			Velocity.setProperty("class.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			Velocity.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String parseVMContent(String templateContent, Map<String, Object> parameters) {
		try {
			VelocityContext context = new VelocityContext();
			if (parameters != null) {
				for (String key : parameters.keySet()) {
					context.put(key, parameters.get(key));
				}
			}
			
			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, "template", templateContent);
			
			String result = writer.getBuffer().toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Parse Velocity Template Error");
		}
	}
	
    public String parseVMFile(String filename, Map<String, Object> parameters) {
        String content = null;
        try {
            Template template = Velocity.getTemplate(filename);
            VelocityContext context = new VelocityContext();
            if (parameters != null) {
	            for (String key : parameters.keySet()) {
					context.put(key, parameters.get(key));
				}
            }
            
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            content = writer.toString();
        } catch (ResourceNotFoundException exc) {
            exc.printStackTrace();
        } catch (ParseErrorException exc) {
            exc.printStackTrace();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return content;
    }

//    public static void main(String[] args) {
//    	Map<String, Object> map = new HashMap<String, Object>();
//    	map.put("startDate", "2015-05-25");
//    	map.put("endDate", "2015-05-28");
//    	map.put("siteCode", "CSN15042400004");
//    	map.put("boxSerialNum", "ASD123456");
//    	//System.out.println(VelocityUtil.getInstance().parseVMContent("sssss($mawb)bbbbb", map));
//    	//System.out.println("============");
//    	
//    	System.out.println(VelocityUtil.getInstance().parseVMFile("mail_chargebox_status_notify.vm", null));
//    	//System.out.println("============");
//    }
	
}
