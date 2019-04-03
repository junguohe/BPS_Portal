
Ext.define('BPSPortal.view.price.applicationapproval.ApprovalDetailViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.approvaldetail',
    onAfterRender:function(){
    	var me=this;
    	var records=me.getViewModel().get('data');
    	var materialname=records.data.materialname;
    	var store = me.getViewModel().getStore('material');
        var paras = { materialname: materialname };
        Ext.apply(store.proxy.extraParams, paras);
        store.load();   
    },
    // 取消
    onCancelClick:function(button){
		 button.up("form").close();
	},
	// 审批通过
	onThroughClick:function(button){

    	var value=button.getText();
		var me=this;
		var form = button.up('form');
			var spid = button.up('form').down('textfield[name=spid]').getValue();// spid
			var did = button.up('form').down('textfield[name=did]').getValue();// did
			var sugcustspl = button.up('form').down('numberfield[name=sugcustspl]').getValue();//
			var sugcustsplinc = button.up('form').down('numberfield[name=sugcustsplinc]').getValue();//
			var sugdealerspl = button.up('form').down('numberfield[name=sugdealerspl]').getValue();//
			var sugdealersplinc = button.up('form').down('numberfield[name=sugdealersplinc]').getValue();//
			var sugdealerprofit = button.up('form').down('numberfield[name=sugdealerprofit]').getValue()/100;//
			var isspl = button.up('form').down('combobox[name=isspl]').getValue();//
			var isrebate = button.up('form').down('combobox[name=isrebate]').getValue();//
			var activedate = button.up('form').down('datefield[name=activedate]').getValue();//
			var splno = button.up('form').down('textfield[name=splno]').getValue();//
			var projname = button.up('form').down('textfield[name=projname]').getValue();//
			var projstatus = button.up('form').down('textfield[name=projstatus]').getValue();//
			var volyear = button.up('form').down('textfield[name=volyear]').getValue();//
			var auditremark = button.up('form').down('textareafield[name=auditremark]').getValue();//
			var currency = button.up('form').down('combobox[name=currencys]').getValue();//
			var materialid = button.up('form').down('combobox[name=materialid]').getValue();//

			var record = Ext.create('BPSPortal.model.PriceStrategyAudit');
			record.data.id='-1';
			record.data.spid=spid;
			record.data.did=did;
			record.data.sugcustspl=sugcustspl;
			record.data.sugcustsplinc=sugcustsplinc;
			record.data.sugdealerspl=sugdealerspl;
			record.data.sugdealersplinc=sugdealersplinc;
			record.data.sugdealerprofit=sugdealerprofit;
			record.data.isspl=isspl;
			record.data.isrebate=isrebate;
			record.data.activedate=activedate;
			record.data.splno=splno;
			record.data.auditremark=auditremark;
			record.data.projname=projname;
			record.data.projstatus=projstatus;
			record.data.volyear=volyear;
			record.data.currency=currency;
			var isnotrebates=button.up('form').down('combobox[name=isnotrebates]').getValue();//
        	var istax = button.up('form').down('combobox[name=istax]').getValue();//
			var param = {record:record.data,value:value,isnotrebate:isnotrebates,materialid:materialid,istax:istax};
			var content='是否执行此操作？';
             Ext.Msg.confirm("提示", content, function (btn) {
                 if (btn == "yes") {
                	 if(value=='审批通过'){
         				
     					if(form.isValid()){
     						Ext.MessageBox.wait("正在执行操作......", "提示");
     						 pricespecialauditcontroller.ThroughSpecial(param,function(result){ 
     						    	if(result.success){
     						    		Ext.MessageBox.hide();
     						    		 Ext.toast({
     						                    title: 'Tips', html: result.msg, align: 't',
     						                    width: 240,
     						                    bodyPadding: 10
     						                });
     					        			 
     					        			 me.getView().close();
     					        			 var roleGrid = Ext.ComponentQuery.query("viewport applicationapproval grid")[0];
     					        			 var store=roleGrid.getStore();
     					        			 store.load();
     				 	        		 
     						    	}else{
     						    		 Ext.MessageBox.hide();
                       	        		 Ext.toast({
     	             		                    title: 'Tips', html: result.msg, align: 't',
     	             		                    width: 240,
     	             		                    bodyPadding: 10
     	             		                });
                      	        	}
     					       	});
     					}else{
     						Ext.toast({
     			                title: 'Tips', html: '请填写完整数据', align: 't',
     			                width: 240,
     			                bodyPadding: 10
     			            });
     					}
     				}		else{
    					Ext.MessageBox.wait("正在执行操作......", "提示");
    					pricespecialauditcontroller.ThroughSpecial(param,function(result){ 
    				    	if(result.success){
    				    		Ext.MessageBox.hide();
    				    		 Ext.toast({
    				                    title: 'Tips', html: result.msg, align: 't',
    				                    width: 240,
    				                    bodyPadding: 10
    				                });
    			        			 me.getView().close();
    			        			 var roleGrid = Ext.ComponentQuery.query("viewport applicationapproval grid")[0];
    			        			 var store=roleGrid.getStore();
    			        			 store.load();
    				    	}else{
    				    		Ext.MessageBox.hide();
              	        		 Ext.toast({
          		                    title: 'Tips', html: result.msg, align: 't',
          		                    width: 240,
          		                    bodyPadding: 10
          		                });
             	        	}
    			       	});
    				}
                	 
                 }
             });
	

		
	
	},
	// 审批驳回
	onRefuseClick:function(button){
		var spid = button.up('form').down('textfield[name=spid]').getValue();
		var did = button.up('form').down('textfield[name=did]').getValue();
		
	},
	onSaveClick:function(button){

		var me=this;
		var form = button.up('form');
			var spid = button.up('form').down('textfield[name=spid]').getValue();// spid
			var did = button.up('form').down('textfield[name=did]').getValue();// did
			var sugcustspl = button.up('form').down('numberfield[name=sugcustspl]').getValue();//
			var sugcustsplinc = button.up('form').down('numberfield[name=sugcustsplinc]').getValue();//
			var sugdealerspl = button.up('form').down('numberfield[name=sugdealerspl]').getValue();//
			var sugdealersplinc = button.up('form').down('numberfield[name=sugdealersplinc]').getValue();//
			var sugdealerprofit = button.up('form').down('numberfield[name=sugdealerprofit]').getValue()/100;//
			var isspl = button.up('form').down('combobox[name=isspl]').getValue();//
			var isrebate = button.up('form').down('combobox[name=isrebate]').getValue();//
			var activedate = button.up('form').down('datefield[name=activedate]').getValue();//
			var splno = button.up('form').down('textfield[name=splno]').getValue();//
			var auditremark = button.up('form').down('textareafield[name=auditremark]').getValue();//
			var currency = button.up('form').down('combobox[name=currencys]').getValue();//
			var materialid = button.up('form').down('combobox[name=materialid]').getValue();//
			var record = Ext.create('BPSPortal.model.PriceStrategyAudit'); 
			
			record.data.spid=spid;
			record.data.did=did;
			record.data.sugcustspl=sugcustspl;
			record.data.sugcustsplinc=sugcustsplinc;
			record.data.sugdealerspl=sugdealerspl;
			record.data.sugdealersplinc=sugdealersplinc;
			record.data.sugdealerprofit=sugdealerprofit;
			record.data.isspl=isspl;
			record.data.isrebate=isrebate;
			record.data.activedate=activedate;
			record.data.splno=splno;
			record.data.auditremark=auditremark;
			record.data.currency=currency;
			console.log(record.data.currency);
			var isnotrebates=button.up('form').down('combobox[name=isnotrebates]').getValue();//
             var istax = button.up('form').down('combobox[name=istax]').getValue();//
			 var param = {record:record.data,isnotrebate:isnotrebates,materialid:materialid,istax:istax};
			 var content='是否执行此操作？';
             Ext.Msg.confirm("提示", content, function (btn) {
                 if (btn == "yes") {
     					if(form.isValid()){
     						Ext.MessageBox.wait("正在执行操作......", "提示");
     						pricespecialauditcontroller.UpdateSpecial(param,function(result){ 
     						    	if(result.success){
     						    		Ext.MessageBox.hide();
     						    		 Ext.toast({
     						                    title: 'Tips', html: result.msg, align: 't',
     						                    width: 240,
     						                    bodyPadding: 10
     						                });
     					        			 
     					        			 me.getView().close();
     					        			 var roleGrid = Ext.ComponentQuery.query("viewport applicationapproval grid")[0];
     					        			 var store=roleGrid.getStore();
     					        			 store.load();
     				 	        		 
     						    	}else{
     						    		 Ext.MessageBox.hide();
                       	        		 Ext.toast({
     	             		                    title: 'Tips', html: result.msg, align: 't',
     	             		                    width: 240,
     	             		                    bodyPadding: 10
     	             		                });
                      	        	}
     					       	});
     					}else{
     						Ext.toast({
     			                title: 'Tips', html: '请填写完整数据', align: 't',
     			                width: 240,
     			                bodyPadding: 10
     			            });
     					}
                	 
                 }
             });
	}
             });
