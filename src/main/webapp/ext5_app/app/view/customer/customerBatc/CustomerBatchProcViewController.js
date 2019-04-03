
Ext.define('BPSPortal.view.customer.customerBatc.CustomerBatchProcViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.customerbatchproc',
    /**
     * 上传需要处理的客户
     */
    onUploadClick:function(btn){
    	var attachmentForm = this.getView().down('#uploadForm');
    	var grid = Ext.ComponentQuery.query("viewport customerbatchproc grid")[0];
    	var store = grid.getStore();
    	
    	Ext.MessageBox.wait("正在执行操作......", "提示");
    	attachmentForm.getForm().submit({
    		success: function(form, action) {
    			Ext.MessageBox.hide();
            	if(action.result.info == 'success'){
            		store.removeAll();
           	    	if(action.result.list.length>0){
           	    		for(var i=0;i<(action.result.list).length;i++){
           	    			store.insert(0,action.result.list[i]);
           	    		}
           	    	}
            		Ext.Msg.alert("提示", "上传成功！");
            	}else{
            		Ext.Msg.alert("提示",action.result.info);
            	}
            }
    	});
    },
    /**
     * 保存or修改信息
     */
    onSaveClick:function(btn){
    	var grid = Ext.ComponentQuery.query("viewport customerbatchproc grid")[0];
    	var store = grid.getStore();
    	if(store.getCount<0){
    		Ext.Msg.alert('提示','没有可操作数据');
    		return;
    	}
    	
    	var seq =btn.up('form').down('textfield[name=seq]').getValue();
    	if(seq==null || seq == ""){
    		Ext.Msg.alert('提示', '请填写SEQ');
        	return;
    	}
    	
    	var records = [];
    	for (var i = 0; i < store.getCount(); i++) {
    		var record = Ext.create('BPSPortal.model.CustomerBatch');
    		
    		if(store.getAt(i).data.dealerName==null||store.getAt(i).data.dealerName==''){
	    		Ext.Msg.alert('提示', '经销商不能为空');
	        	return;
	    	}
    		if(store.getAt(i).data.customerCode==null||store.getAt(i).data.customerCode==''){
	    		Ext.Msg.alert('提示', '客户不能为空');
	        	return;
	    	}
    		if(store.getAt(i).data.prodCode==null||store.getAt(i).data.prodCode==''){
	    		Ext.Msg.alert('提示', '产品线不能为空');
	        	return;
	    	}
    		if(store.getAt(i).data.type==null||store.getAt(i).data.type==''){
	    		Ext.Msg.alert('提示', '操作类型不能为空');
	        	return;
	    	}
    		if(store.getAt(i).data.type == 1 && (store.getAt(i).data.regDate==null||store.getAt(i).data.regDate=='')){
	    		Ext.Msg.alert('提示', '延期操作的延长期限不能为空');
	        	return;
	    	}
    		
    		record.data.id = store.getAt(i).data.id;
    		record.data.dealerName = store.getAt(i).data.dealerName;
    		record.data.seq = seq;
    		record.data.customerCode = store.getAt(i).data.customerCode;
    		record.data.prodCode = store.getAt(i).data.prodCode;
    		record.data.type = store.getAt(i).data.type;
    		record.data.regDate = store.getAt(i).data.regDate;
    		records.push(record.data);
    	}
    	
    	var param = {customerBatch:records};
    	Ext.MessageBox.wait("正在执行操作......", "提示");
    		customerControl.saveCustomerBatchInfo(param,function(result){
        		Ext.MessageBox.hide();
        		if(result.success == false){
        			Ext.toast({
                        title: 'Tips', html: result.msg, align: 't',
                        width: 240,
                        bodyPadding: 10
                    });
        		}else{
        			Ext.toast({
                        title: 'Tips', html: result.msg, align: 't',
                        width: 240,
                        bodyPadding: 10
    	            });
        			var paras = { seq: result.data };
        	        Ext.apply(store.proxy.extraParams, paras);
        	        store.load();
        		}
        	});
    },
    /**
     * 查询
     */
    onSearchClick: function (btn){
    	var seq =btn.up('form').down('textfield[name=seq]').getValue();
    	if(seq==null || seq == ""){
    		Ext.Msg.alert('提示', '请填写SEQ');
        	return;
    	}
    	
    	var grid = Ext.ComponentQuery.query("viewport customerbatchproc grid")[0];
    	var store = grid.getStore();
    	
    	var extraParams = {seq:seq};
    	store.on("beforeload", function (store, operation, eOpts) {
			 Ext.apply(store.proxy.extraParams, extraParams);
			});
    	store.load();	
    	
    },
    /**
     * 执行对应的操作
     */
    onSubmitClick:function(btn){
    	var seq =btn.up('form').down('textfield[name=seq]').getValue();
    	if(seq==null || seq == ""){
    		Ext.Msg.alert('提示', '请填写SEQ');
        	return;
    	}
    	
    	var grid = Ext.ComponentQuery.query("viewport customerbatchproc grid")[0];
    	var store = grid.getStore();
    	
    	for (var i = 0; i < store.getCount(); i++) {
    		if(store.getAt(i).data.status == 1){
    			Ext.Msg.alert('提示', '存在已执行过的数据');
            	return;
    		}
    	}
    	var param = {seq:seq};
    	Ext.MessageBox.wait("正在执行操作......", "提示");
    	customerControl.ProcCustoemrBatch(param,function(result){
    		Ext.MessageBox.hide();
    		if(result.success == false){
    			Ext.toast({
                    title: 'Tips', html: result.msg, align: 't',
                    width: 240,
                    bodyPadding: 10
                });
    		}else{
    			Ext.toast({
                    title: 'Tips', html: result.msg, align: 't',
                    width: 240,
                    bodyPadding: 10
	            });
    			var paras = { seq: seq};
    	        Ext.apply(store.proxy.extraParams, paras);
    	        store.load();
    		}
    	})
    }
    	    
});
