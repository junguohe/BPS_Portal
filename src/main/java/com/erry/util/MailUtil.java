package com.erry.util;

import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.erry.common.GlobalConstant;
import com.sun.mail.util.MailSSLSocketFactory;

public class MailUtil {
	
//	public static void sendMail(String toUser, String title, String content) throws Exception {
//		if (!StringUtil.isEmpty(toUser) && !StringUtil.isEmpty(content)) {
//			String [] users = new String[] {toUser};
//			sendMail(users, null, title, content);
//		}
//	}
	
	private static String getMailList(String[] mailArray){  
        
        StringBuffer toList = new StringBuffer();  
    int length = mailArray.length;  
        if(mailArray!=null && length <2){  
             toList.append(mailArray[0]);  
        }else{  
             for(int i=0;i<length;i++){  
                     toList.append(mailArray[i]);  
                     if(i!=(length-1)){  
                         toList.append(",");  
                     }  
  
             }  
         }  
     return toList.toString();  
  
} 
	
	public static void sendMail(String[] ToUsers, String[] CcUsers, String title, String content)throws Exception {
		boolean hasFile = false;
		if (ToUsers == null || ToUsers.length == 0 || StringUtil.isEmpty(content)) {
			return;
		}

		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
			senderImpl.setHost(GlobalConstant.MAIL_HOST); 
			senderImpl.setPort(GlobalConstant.MAIL_PORT);
			senderImpl.setUsername(GlobalConstant.MAIL_USERNAME);
			senderImpl.setPassword(GlobalConstant.MAIL_PASSWORD);
			
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.timeout", GlobalConstant.MAIL_TIMEOUT);
			prop.put("mail.smtp.starttls.enable", "true");
			
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			prop.put("mail.smtp.ssl.socketFactory", sf);
		
			senderImpl.setJavaMailProperties(prop);
			
			
			
			
			String toList = getMailList(ToUsers);  
			InternetAddress[] iaToList = new InternetAddress().parse(toList);
			
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8"); 
			messageHelper.setTo(iaToList);
			if (CcUsers != null && CcUsers.length > 0)
				messageHelper.setCc(CcUsers);
			messageHelper.setFrom(GlobalConstant.MAIL_FROM, GlobalConstant.MAIL_FROM_NAME);
			messageHelper.setSubject(title);
			messageHelper.setText(StringEscapeUtils.unescapeHtml(content), true);
			if (hasFile) {
				//FileSystemResource file = new FileSystemResource(new File(GlobalConstant.MAIL_ATTACH)); 
				//messageHelper.addAttachment(MimeUtility.encodeWord(GlobalConstant.MAIL_ATTACH_NAME), file);
			}
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

//	public static void main(String args[]) throws Exception {
//		MailUtil.sendMail(new String[]{"yingying.wei@erry.com"}, null, "test", StringEscapeUtils.unescapeHtml("<body>&lt;br/&gt;宁波市鄞州庆馨电子有限公司&lt;br/&gt;宁波鄞州威迪电子有限公司&lt;br/&gt;台州市尚远塑业灯饰有限公司&lt;br/&gt;宁波市富来电子科技有限公司&lt;br/&gt;海宁市艾德源电子有限公司&lt;br/&gt;宁波一文照明科技有限公司&lt;br/&gt;宁波光语电子有限公司&lt;br/&gt;余姚市优控电子有限公司&lt;br/&gt;上虞远东照明有限公司&lt;br/&gt;宁波嘉利光电科技有限公司&lt;br/&gt;嘉兴欧源照明电器有限公司&lt;br/&gt;宁海县永正电子有限公司&lt;br/&gt;宁波爱美克思照明电器有限公司&lt;br/&gt;浙江奥司朗照明电器有限公司&lt;br/&gt;宁波合兴电子有限公司&lt;br/&gt;宁波高新区凡尔斯电子科技有限公司&lt;br/&gt;宁波正耀照明电器有限公司&lt;br/&gt;宁波市鄞州龙源照明电器有限公司&lt;br/&gt;浙江九阳光电有限公司&lt;br/&gt;宁波高新区技领电源有限公司&lt;br/&gt;余姚市耀鸿霓虹电子有限公司&lt;br/&gt;浙江上光照明有限公司&lt;br/&gt;浙江和惠照明科技有限公司&lt;br/&gt;慈溪欧蓝得照明灯具有限公司&lt;br/&gt;宁波贝泰灯具有限公司&lt;br/&gt;宁波朗格照明电器有限公司&lt;br/&gt;宁波多力浦工贸有限公司&lt;br/&gt;宁波光极照明科技有限公司&lt;br/&gt;德清盛泰芯电子科技有限公司&lt;br/&gt;宁波光德照明科技有限公司&lt;br/&gt;宁波江东硕明电子有限公司&lt;br/&gt;宁波玖创电子有限公司&lt;br/&gt;上虞市友友照明电器有限公司&lt;br/&gt;宁波市依雷特照明电器有限公司<br/>杭州亿涞光电科技有限公司</body>"));
//		System.out.println("send mail success.");
//	}
}
