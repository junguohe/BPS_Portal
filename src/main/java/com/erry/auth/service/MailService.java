package com.erry.auth.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.dto.CustomerBatchDTO;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.BPSMail;
import com.erry.auth.model.Customer;
import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.model.PriceSpecialDetail;
import com.erry.auth.model.UserDealer;
import com.erry.auth.repository.CustomerRepository;
import com.erry.auth.repository.MailRepository;
import com.erry.auth.repository.PriceSpecialDetailRepository;
import com.erry.auth.repository.UserDealerRepostory;
import com.erry.util.DateUtil;
import com.erry.util.EncryptDecryptUtil;
import com.erry.util.MailUtil;
import com.erry.util.VelocityUtil;


@Service("mailService")
@Transactional
public class MailService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MailRepository mailRepository;
	
	@Autowired
	private UserDealerRepostory dealerRepostory;
	
	@Autowired
	private PriceSpecialDetailRepository priceSpecialDetailRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	/**
	 * job
	 */
	public void sendMailTask() {
		//获取待发送的邮件 status=0
		List<BPSMail> mails = mailRepository.findBystauts();
		for(BPSMail m : mails){
			sendBonitaMail(m);
		}
	}
	
 
	/**
	 * 发邮件
	 * @param mail
	 */
	public void sendBonitaMail(BPSMail mail){
		String to = mail.getTo();
		System.out.println("开始发送邮件。。。。。。。。。。。。。。。。。。。"+to);
		String title = mail.getSubject();
		String content="";
		if(mail.getContent()!=null&&mail.getContent()!=""){
			content = mail.getContent();
		}
		try {
			String tousers[]={to};
			MailUtil.sendMail(tousers,null,title,content);
			mail.setLastsenttime(new Date());
			mail.setStatus("1");
			mail.setErrrormsg("");
			System.out.println("发送成功。。。。。。。。。。。。。。。。。。。");
		} catch (Exception e) {
			System.out.println("发送失败。。。。。。。。。。。。。。。。。。。");
			e.printStackTrace();
			mail.setErrrormsg(e.getMessage());
		}
		entityManager.merge(mail);
	}
	/**
	 * 特价邮件
	 * statuts 1:通过 2：驳回
	 * to：收件人
	 * content：邮件内容
	 * status：结果
	 * custname：用户名
	 */
	public void createPriceMail(PriceSpecialAudit audit,String status){
		BPSMail mail = new BPSMail();
		mail.setCreatetime(new Date());
		UserDealer user = dealerRepostory.findByUserId(audit.getPriceSpecial().getApplicator());
		PriceSpecialDetail detail = priceSpecialDetailRepository.findBydid(audit.getPriceSpecialDetail().getDid());//获取特价申请明细
		Customer customer = customerRepository.findCustBycpid(detail.getCpid());//获取客户信息
		mail.setTo(user.getEmail());
		mail.setContent(createPriceCont(user.getUsername(),status,customer,audit));
		mail.setSubject("特价申请");
		mail.setStatus("0");
		entityManager.persist(mail);
	}
	/**
	 * 重置密码email
	 * @param dealer
	 * @throws Exception 
	 */
	public void createFlushUserEmail(UserDealer dealer,AuUser user) throws Exception{
		BPSMail mail = new BPSMail();
		mail.setCreatetime(new Date());
		mail.setTo(dealer.getEmail());
		mail.setContent(createFlushUser(dealer,user));
		mail.setSubject("密码重置");
		mail.setStatus("0");
		entityManager.persist(mail);
	}
	private String createFlushUser(UserDealer dealer,AuUser user) throws Exception{
		String cont = null;
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("username", dealer.getUsername());
		para.put("loginname", user.getUsercode());
		para.put("password",EncryptDecryptUtil.detryptByAES(user.getPassword()));
		cont = VelocityUtil.getInstance().parseVMFile("mail_FlushuserPassword.vm", para);
		return cont;
	}
	/**
	 * 新建用户通知email
	 * @param dealer
	 * @throws Exception 
	 */
	public void createAddUserEmail(UserDealer dealer,AuUser user) throws Exception{
		BPSMail mail = new BPSMail();
		mail.setCreatetime(new Date());
		mail.setTo(dealer.getEmail());
		mail.setContent(createAddUser(dealer,user));
		mail.setSubject("Dealer用户新建");
		mail.setStatus("0");
		entityManager.persist(mail);
	}
	private String createAddUser(UserDealer dealer,AuUser user) throws Exception{
		String cont = null;
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("username", dealer.getUsername());
		para.put("loginname", user.getUsercode());
		para.put("password", EncryptDecryptUtil.detryptByAES(user.getPassword()));
		cont = VelocityUtil.getInstance().parseVMFile("mail_adduser.vm", para);
		return cont;
	}
	/**
	 * 客户报备邮件
	 * @param dealerid 
	 * @param custname
	 * @param status 1:通过 2：驳回
	 * @param remark 审批意见
	 *               @param  en true:en
	 */
	public void createCustMail(String dealerid,String custname,String status,String remark,boolean en){
		BPSMail mail = new BPSMail();
		mail.setCreatetime(new Date());
		UserDealer user = dealerRepostory.findById(dealerid);
		mail.setTo(user.getEmail());
		mail.setContent(createCont(user.getUsername(),status,custname,remark,en));
		mail.setStatus("0");
		mail.setSubject("客户报备");
		entityManager.persist(mail);
	}
	
	/**
	 * 客户open
	 * @param dealerid 
	 * @param custname
	 * @param remark open
	 */
	public void createCustOpenMail(String dealerid,String custname,String remark,boolean en){
		BPSMail mail = new BPSMail();
		mail.setCreatetime(new Date());
		UserDealer user = dealerRepostory.findById(dealerid);
		mail.setTo(user.getEmail());
		mail.setContent(createOpenCont(user.getUsername(),custname,remark,en));
		mail.setStatus("0");
		mail.setSubject("客户Open");
		entityManager.persist(mail);
	}
	/**
	 * 分装customer批量操作邮件
	 * @param dealerName
	 * @param email
	 * @param dtos
	 */
	public void createCustomerBatchOpenMail(String dealerName,String email,List<CustomerBatchDTO> dtos){
		BPSMail mail = new BPSMail();
		mail.setCreatetime(new Date());
		mail.setTo(email);
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("userName", dealerName);
		Map<String,String> header = new HashMap<>();
		if(dtos.get(0).getRegion().equals("海外")){
			header.put("客户名称","Customer Name");
			header.put("区域","Region");
			header.put("产品线","Product Line");
			header.put("结果","Result");
			para.put("mailBody", getCustoemrBatchMailBody(dtos,header));
			mail.setContent(VelocityUtil.getInstance().parseVMFile("mail_customer_Batch_open.vm", para));
		}else{
			header.put("客户名称","客户名称");
			header.put("区域","区域");
			header.put("产品线","产品线");
			header.put("结果","结果");
			para.put("mailBody", getCustoemrBatchMailBody(dtos,header));
			mail.setContent(VelocityUtil.getInstance().parseVMFile("mail_customer_Batch_open.vm", para));
		}
		mail.setStatus("0");
		mail.setSubject("客户Open");
		entityManager.persist(mail);
	}
	
	public String getCustoemrBatchMailBody(List<CustomerBatchDTO> dtos,Map<String,String> header){
		StringBuffer mailBody = new StringBuffer("<table class='gridtable'>"
												+ "<tr ><th width=400px align='left'>"+header.get("客户名称")+"</th>"
												+ "<th width=80px align='left'>"+header.get("区域")+"</th>"
												+ "<th width=80px align='left'>"+header.get("产品线")+"</th>"
												/*+ "<td style='border-left:1px solid #F00;border-top:1px solid #F00'>报备结束日期</td>"*/
												+ "<th width=80px align='left'>"+header.get("结果")+"</th>");
		mailBody.append("</tr>");
		for(CustomerBatchDTO dto : dtos){
			mailBody.append("<tr><td>");
			mailBody.append(dto.getCustName());
			mailBody.append("</td>");
			mailBody.append("<td>");
			mailBody.append(dto.getRegion());
			mailBody.append("</td>");
			mailBody.append("<td>");
			mailBody.append(dto.getProdName());
			mailBody.append("</td>");
/*			mailBody.append("<td style='border-left:1px solid #F00;border-top:1px solid #F00'>");
			mailBody.append(dto.getRegDate()==null?"":dto.getRegDate());
			mailBody.append("</td>");*/
			mailBody.append("<td>");
			mailBody.append(dto.getStatus());
			mailBody.append("</td></tr>");
		}
		mailBody.append("</table>");
		return mailBody.toString();
	}
	/**
	 * 封装客户申请邮件内容
	 */
	private String createCont(String to,String status,String custname,String remark,boolean en){
		String cont = null;
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("username", to);
			para.put("custname", custname);
			para.put("remark", remark);
			if(status.equals("1")){
				if(en){
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_upload_notify_en.vm", para);
				}else{
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_upload_notify.vm", para);
				}
			}else{
				if(en){
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_open_notify_en.vm", para);
				}else{
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_open_notify.vm", para);
				}
			}
		return cont;
	}
	/**
	 * 封装客户申请邮件内容
	 */
	private String createOpenCont(String to,String custname,String remark,boolean en){
		String cont = null;
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("username", to);
			para.put("custname", custname);
			para.put("remark", remark);
			if(en){
				cont = VelocityUtil.getInstance().parseVMFile("mail_customer_open_en.vm", para);
			}else{
				cont = VelocityUtil.getInstance().parseVMFile("mail_customer_open.vm", para);
			}
		return cont;
	}
	/**
	 * 封装特价申请邮件内容
	 */
	private String createPriceCont(String to,String status,Customer custname,PriceSpecialAudit audit){
			Map<String, Object> para = new HashMap<String, Object>();
			String cont = null;
			para.put("username", to);
			if(status.equals("1")){
				para.put("custname", custname.getCustname());
				para.put("code", audit.getPriceSpecialDetail().getMaterialInfo().getMaterialcode());
				para.put("name", audit.getPriceSpecialDetail().getMaterialInfo().getMaterialname());
				para.put("sugcustsplinc", audit.getSugcustsplinc());
				para.put("sugdealersplinc", audit.getSugdealersplinc());
				String pofit = (Double.valueOf(audit.getSugdealerprofit())*100)+"%";
				para.put("sugdealerprofit", pofit);
				para.put("sugcustspl", audit.getSugcustspl());
				para.put("sugdealerspl", audit.getSugdealerspl());
				para.put("isnotrebate", audit.getPriceSpecialDetail().getIsnotrebate().equals("1")?"是":"否");
				para.put("activedate", DateUtil.format(audit.getActivedate(),DateUtil.PATTERN_DATE));
				para.put("auditremark", audit.getAuditremark());
				if(custname.getRegion().equals("海外")){
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_open_price_notify_en.vm", para);
				}else{
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_open_price_notify.vm", para);
				}
			}else{
				para.put("custname", custname.getCustname());
				para.put("auditremark", audit.getAuditremark());
				if(custname.getRegion().equals("海外")){
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_close_price_notify_en.vm", para);
				}else{
					cont = VelocityUtil.getInstance().parseVMFile("mail_person_close_price_notify.vm", para);
				}
			}
		return cont;
	}
	
	
}
