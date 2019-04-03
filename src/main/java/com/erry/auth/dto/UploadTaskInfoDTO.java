package com.erry.auth.dto;
import com.erry.auth.model.UploadTaskInfo;
import com.erry.util.DateUtil;
public class UploadTaskInfoDTO {

	private String id;
	private String taskseq;
	private String taskowner;
	private String period;
	private String taskcontent;
	private String remark;
	private String taskconfirm;
	private String active;
	private String creator;
	private String createdate;
	private String updator;
	private String updatedate;
	private String dealername;
	private String dealercode;
	
	
 public UploadTaskInfoDTO(UploadTaskInfo uploadtaskinfo) {
	 
	 	this.id = uploadtaskinfo.getId();
	 	this.taskseq = uploadtaskinfo.getTaskseq();
		this.taskowner = uploadtaskinfo.getTaskowner();
		this.period = uploadtaskinfo.getPeriod();
		this.taskcontent = uploadtaskinfo.getTaskcontent();
		this.remark = uploadtaskinfo.getRemark();
		this.taskconfirm = uploadtaskinfo.getTaskconfirm();
		this.active = uploadtaskinfo.getActive();
		this.remark = uploadtaskinfo.getRemark();
		this.creator = uploadtaskinfo.getCreator();
		
		if(uploadtaskinfo.getCreatedate()!=null){
			this.createdate = DateUtil.format(uploadtaskinfo.getCreatedate(),DateUtil.PATTERN_DATE_TIME);
			}
		//this.updator = uploadtaskinfo.getUpdator();
		if(uploadtaskinfo.getUpdatedate()!=null){
			this.updatedate = DateUtil.format(uploadtaskinfo.getUpdatedate(),DateUtil.PATTERN_DATE_TIME);
			}
		if(uploadtaskinfo.getTaskowner().equals("1")){
			this.dealername = uploadtaskinfo.getDealer().getDealername();
			this.dealercode = uploadtaskinfo.getDealer().getDealercode();
		}else{
			this.dealername ="";
			this.dealercode ="";
		}
		
	}


public String getId() {
	return id;
}


public void setId(String id) {
	this.id = id;
}


public String getTaskseq() {
	return taskseq;
}


public void setTaskseq(String taskseq) {
	this.taskseq = taskseq;
}


public String getTaskowner() {
	return taskowner;
}


public void setTaskowner(String taskowner) {
	this.taskowner = taskowner;
}


public String getPeriod() {
	return period;
}


public void setPeriod(String period) {
	this.period = period;
}


public String getTaskcontent() {
	return taskcontent;
}


public void setTaskcontent(String taskcontent) {
	this.taskcontent = taskcontent;
}


public String getRemark() {
	return remark;
}


public void setRemark(String remark) {
	this.remark = remark;
}


public String getTaskconfirm() {
	return taskconfirm;
}


public void setTaskconfirm(String taskconfirm) {
	this.taskconfirm = taskconfirm;
}


public String getActive() {
	return active;
}


public void setActive(String active) {
	this.active = active;
}


public String getCreator() {
	return creator;
}


public void setCreator(String creator) {
	this.creator = creator;
}


public String getCreatedate() {
	return createdate;
}


public void setCreatedate(String createdate) {
	this.createdate = createdate;
}


public String getUpdator() {
	return updator;
}


public void setUpdator(String updator) {
	this.updator = updator;
}


public String getUpdatedate() {
	return updatedate;
}


public void setUpdatedate(String updatedate) {
	this.updatedate = updatedate;
}


public String getDealername() {
	return dealername;
}


public void setDealername(String dealername) {
	this.dealername = dealername;
}


public String getDealercode() {
	return dealercode;
}


public void setDealercode(String dealercode) {
	this.dealercode = dealercode;
}
	
 
}

