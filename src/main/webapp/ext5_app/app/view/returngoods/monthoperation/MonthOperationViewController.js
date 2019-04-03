

Ext.define('BPSPortal.view.returngoods.monthoperation.MonthOperationViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.monthoperation',
    
    onAfterRender:function(){
    	var me =this;
    	var applicator=me.lookupReference('creators');
    	var param = {}; 
    	auuserController.getUser(param,function(result){ 
	    	if(result.success){
	    		applicator.setValue(result.data.username);
	    	}
      	});
    },
    onClick:function(button){
    	var grid = Ext.ComponentQuery.query("viewport monthoperation grid")[0];
    	var store=grid.getStore();
    	var year =button.up('form').down('combobox[name=year]').getValue();
    	var month =button.up('form').down('combobox[name=month]').getValue();
    	if(year==null||year==''||month==null||month==''){
    		Ext.Msg.alert('提示', '会计周期的年和月不能为空');
        	return;
    	}
    	var period =year+'-'+month;
    	var value=button.getText();
    	var active=1;
    	if(value=='Open'){
    		active=1;
    	}else{
    		active=0;
    	}
    	console.log(active);
		var content = '是否执行此操作？';
		Ext.Msg.confirm("提示", content, function(btn) {
			if (btn == "yes") {
				var param = {
						period : period,
						active : active
				};
				Ext.MessageBox.wait("正在执行操作......", "提示");
				bpsmonthlycontrol.saveOrUpdateMonth(param,
						function(result) {
							if (result.success) {
								Ext.MessageBox.hide();
								Ext.toast({
									title : 'Tips',
									html : result.msg,
									align : 't',
									width : 240,
									bodyPadding : 10
								});
								store.load();
							} else {
								Ext.MessageBox.hide();
								Ext.toast({
									title : 'Tips',
									html : result.msg,
									align : 't',
									width : 240,
									bodyPadding : 10
								});
							}
						});
			}
		});
    	
    	
    	
    }
});
