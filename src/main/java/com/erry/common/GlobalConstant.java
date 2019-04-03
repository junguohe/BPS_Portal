package com.erry.common;

import java.util.ResourceBundle;

public class GlobalConstant {
	
	private static final ResourceBundle systemConfig = ResourceBundle.getBundle("system-config");
	
	public static String getSystemConfig(String key) {
		return systemConfig.getString(key);
	}
	public static  String UPLOAD_FOLDER;
	
	public static final String DEMO = "DEMO";
		
	public static final long CODE_SEQ_DIV = 100000;
	
	public static final int CODE_SEQ_LENGTH = 5;
	
	public static final String DATACALCTASK_PREFIX = "DCT";
	public static final String FILEUPLOADTASK_PREFIX = "FUT";
	public static final String MATERIAL_BILLCODE_PREFIX = "MTR";
	public static final String AD_BILLCODE_PREFIX = "AD";
    
    public static final String OU_TYPE_HEAD_OFFICE   = "001"; //总部
        
    public static final String USER_DEFAULT_PASSWORD = "123456";
    
    //Emial
//	public static String MAIL_HOST = "smtp.erry.com";
//	public static String MAIL_USERNAME = "yingying.wei@erry.com";
//	public static String MAIL_PASSWORD = "wy.950202";
//	public static String MAIL_FROM = "yingying.wei@erry.com";
    
    public static String MAIL_HOST = "smtp.partner.outlook.cn";
    public static String MAIL_USERNAME = "it@bpsemi.com";
	  public static String MAIL_PASSWORD = "bpsemi_123";
	  public static String MAIL_FROM = "it@bpsemi.com";
	
   // public static String MAIL_HOST = "Outlook.office365.com";
   // public static String MAIL_HOST = "smtp.office365.com";
    
	
	public static int MAIL_PORT = 587;
	public static String MAIL_TIMEOUT = "25000";
    public static String MAIL_FROM_NAME = "BPSPortal";
	public static String MAIL_TITLE_1 = "BPSPortal 待处理任务";
	public static String MAIL_TITLE_2 = "BPSPortal 上传任务结果";
	public static String MAIL_TITLE_3 = "BPSPortal 计算任务结果";
	
	public final static String TimeOut_MessageCode = "Login_Time_Out_Error";
	public static final String TASK_UPLOAD_FOLDER = "upload";
	
	//file_upload_task status
	public static final Integer UPLOAD_TASK_STATUS_WAIT    = 1;  //等待
	public static final Integer UPLOAD_TASK_STATUS_RUNNING = 2;  //执行中
	public static final Integer UPLOAD_TASK_STATUS_FAIL    = 3;  //失败
	public static final Integer UPLOAD_TASK_STATUS_SUCCESS = 4;  //成功
	
	//PSI & rebate calculate task status
	public static final Integer CALCULATE_TASK_STATUS_WAIT    = 1;  //等待
	public static final Integer CALCULATE_TASK_STATUS_RUNNING = 2;  //执行中
	public static final Integer CALCULATE_TASK_STATUS_FAIL    = 3;  //失败
	public static final Integer CALCULATE_TASK_STATUS_SUCCESS = 4;  //成功
	
	public static final String TASK_FILETYPE_SELLIN			 		= "sell_in";
	public static final String TASK_FILETYPE_SELLIN_ADJUST			= "sell_in_adjust";
	public static final String TASK_FILETYPE_TRADESTOCK	    		= "trade_stock";
	public static final String TASK_FILETYPE_SELLINTARGET			= "sell_in_target";
	public static final String TASK_FILETYPE_SELLOUTTARGET			= "sell_out_target";
	public static final String TASK_FILETYPE_KAMSELLOUTTARGET		= "kam_sell_out_target";
	public static final String TASK_FILETYPE_ATTACHMENT      		= "attachment";
	public static final String TASK_FILETYPE_LISTINGTARGET			= "listing_target";
	public static final String TASK_FILETYPE_PROMOTIONSONJOB		= "promotions_onjob";
	public static final String TASK_FILETYPE_PROMOTIONSTRAINSCORE	= "promotions_train_score";
	
	public static final String UPLOAD_FOLDER_PROMOTIONS = "promotions";
	
	
	//审批流成单据类型
	public static final String ENTITY_TYPE_MATERIAL = "Material";
	public static final String ENTITY_TYPE_AD		= "AD";
	public static final String ENTITY_TYPE_UPLOAD	= "upload";
	public static final String ENTITY_TYPE_CALC	= "calc";
	
	//审批流成名称
	public static final String PROCESS_NAME_MATERIAL = "Material Maintenance";
	public static final String PROCESS_NAME_AD = "AD Maintenance";
	
	//审批操作
	public static final String PROCESS_ACTION_CREATE    = "Create";
	public static final String PROCESS_ACTION_SUBMIT    = "Submit";
	public static final String PROCESS_ACTION_PASS      = "Pass";
	public static final String PROCESS_ACTION_REJECT    = "Reject";
	public static final String PROCESS_ACTION_CANCEL    = "Cancel";
	public static final String PROCESS_ACTION_UPDATE    = "Update";
	public static final String PROCESS_ACTION_DELETE    = "Delete";
	
	//Bonita配置
//	public static final String BONITA_SERVER_URL = "http://192.168.0.216:8081";
	public static final String BONITA_SERVER_URL_PHILIPS_QA_ENV = "http://161.91.27.30:8088";
	public static final String BONITA_APPLICATION_NAME = "bonita";
	public static final String BONITA_USER_PASSWORD = "1";
	
	public static final String THIS_SYSTEM_AOTO_LOGIN_URL = "http://localhost:8080/BPSPortal/authority/loginAuto";
	
	public static final Integer MATERIAL_STATUS_DELETE   = 0;    //已删除
	public static final Integer MATERIAL_STATUS_CREATE   = 10;   //已创建
	public static final Integer MATERIAL_STATUS_CANCEL   = 12;   //打回
	public static final Integer MATERIAL_STATUS_SUBMIT   = 20;   //处理中
	public static final Integer MATERIAL_STATUS_COMPLETE = 50;   //已完成
	
	public static final Integer AD_STATUS_DELETE   = 0;    //已删除
	public static final Integer AD_STATUS_CREATE   = 10;   //已创建
	public static final Integer AD_STATUS_CANCEL   = 12;   //打回
	public static final Integer AD_STATUS_SUBMIT   = 20;   //处理中
	public static final Integer AD_STATUS_COMPLETE = 50;   //已完成
	
}
