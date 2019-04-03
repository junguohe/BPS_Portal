
Ext.define('BPSPortal.view.user.PassWordWinViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.PassWordWin',
    //确认
    onSaveParamClick:function(button){
    	 var password = button.up('window').down('textfield[name=password]').getValue();
         var newpassword = button.up('window').down('textfield[name=newpassword]').getValue();
         var confirmpassword = button.up('window').down('textfield[name=confirmpassword]').getValue();
         
         if(password==null||password==''){
        	 Ext.Msg.alert('提示', '原始密码不能为空');
         	return;
         }
         
         if(newpassword==null||newpassword==''){
        	 Ext.Msg.alert('提示', '新密码不能为空');
         	return;
         }
         if(confirmpassword==null||confirmpassword==''){
        	 Ext.Msg.alert('提示', '确认密码不能为空');
         	return;
         }
         
         if(newpassword!=confirmpassword){
        	 Ext.Msg.alert('提示', '确认密码必须和新密码相同');
          	return;
         }
         var param = {password:password,newpassword:newpassword}; 
         auuserController.updateAuUser(param,function(result){
 	        	if(result.success){	
 	        		
 	        		Ext.Msg.alert("Tips", result.msg, function(btn) {
						//if (btn == "yes") {
							window.location.href="resources/j_spring_security_logout";
						//}
				   });


 	        	}else{
  	        		 Ext.toast({
		                    title: 'Tips', html: result.msg, align: 't',
		                    width: 240,
		                    bodyPadding: 10
		                });
 	        	}
 	        });
    },
    //取消
    onCancelClick:function(button){
    	button.up("window").close();
    }
    
    

});