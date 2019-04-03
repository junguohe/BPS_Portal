

Ext.define('BPSPortal.view.user.UpdateDealerWinViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.updatedealerwin',
    
    
    onAfterRender:function(){
    	var me=this;
    	var record = me.getView().record;
    	console.log(record);
    	var dealername = me.lookupReference('dealername');
    	dealername.setValue(record.dealername);
    	var dealercode = me.lookupReference('dealercode');
    	dealercode.setValue(record.dealercode);
    	var loginname = me.lookupReference('loginname');
    	loginname.setValue(record.loginname);
    	var telno = me.lookupReference('telno');
    	telno.setValue(record.telno);
    	var userids=me.lookupReference('userids');
    	userids.setValue(record.userid);
    	var enames=me.lookupReference('enames');
    	enames.setValue(record.ename);
    	var email=me.lookupReference('emails');
    	email.setValue(record.email);
    	var expiredates=me.lookupReference('expiredates');
    	expiredates.setValue(record.expiredate);
//    	
    	
    },
    onSubmitClick:function(btn){
    	 var userid=this.lookupReference('userids').getValue();
    	 var dealername = this.lookupReference('dealername').getValue();
    	 var dealercode = this.lookupReference('dealercode').getValue();
    	 var loginname = this.lookupReference('loginname').getValue();
    	 var telno = this.lookupReference('telno').getValue();
    	 var ename=this.lookupReference('enames').getValue();
    	 var email=this.lookupReference('emails').getValue();
    	 var expiredate=this.lookupReference('expiredates').getValue();
    	 if (ename==''||ename==null) {
     		Ext.Msg.alert('提示', 'ename不能为空');
         	return;
          }
    	 if (email==''||email==null) {
     		Ext.Msg.alert('提示', 'email不能为空');
         	return;
          }
    	  var ss=email.replace(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+(,([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4}))*$/,"true");
    	  if(ss!='true'){
    		Ext.Msg.alert('提示', '请输入正确的格式:xxx.xxx@xxx.xxx!各个邮件之间用英文逗号隔开');
           	return;
    	  }
  		var param = {userid:userid,dealername:dealername,dealercode:dealercode,loginname:loginname,telno:telno,ename:ename,email:email,expiredate:expiredate}; 
  		adduserscontrol.UpdateUserDealer(param,function(result){
	        	if(result.success){
	        		 Ext.toast({
		                    title: 'Tips', html: result.msg, align: 't',
		                    width: 240,
		                    bodyPadding: 10
		                }); 
	        		 var grid = Ext.ComponentQuery.query("viewport dealeradd grid")[0];
	     	         var store=grid.getStore();
	     	         store.load();
	        		 btn.up('window').close();
	        	
	        	}else{
   	        		 Ext.toast({
		                    title: 'Tips', html: result.msg, align: 't',
		                    width: 240,
		                    bodyPadding: 10
		                });
	        	}
	        });
    }
    
    
});
