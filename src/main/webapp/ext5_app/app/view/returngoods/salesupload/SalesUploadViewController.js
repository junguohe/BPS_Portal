
Ext.define('BPSPortal.view.returngoods.salesupload.SalesUploadViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.salesupload',
    
    

    
    //上传
    onUploadClick:function(button){
    	var year =button.up('form').down('combobox[name=year]').getValue();
    	var month =button.up('form').down('combobox[name=month]').getValue();
    	if(year==null||year==''||month==null||month==''){
    		Ext.Msg.alert('提示', '会计周期的年和月不能为空');
        	return;
    	}
    	var period =year+'-'+month;
    	var attachmentForm = this.getView().down('#uploadForm');
		var grid = Ext.ComponentQuery.query("viewport salesupload grid")[0];
    	var store = grid.getStore();
    	var ss = {period:period}; 
    	bpsmonthlycontrol.readList(ss,function(result){
    		if(result.records.length>0){
    			var date= result.records[0].period
    			var active= result.records[0].active
    			if(date==period&&active==0){
    				Ext.Msg.alert('提示', '会计周期已关闭，不能上传');
    	        	return;
    			}else{
    				
    		    	Ext.MessageBox.wait("正在执行操作......", "提示");
    		    	attachmentForm.getForm().submit({
    		    		params: {
    		    			period: period
    		            },
    		            success: function(form, action) {
    		            	if(action.result.info == 'success'){
    		            		Ext.MessageBox.hide();
    		            		var length=(action.result.list).length;
    		            		if(action.result.tid==null){
        	            			Ext.Msg.alert("提示", "上传失败！");
        	            			 return;
        	            		}
    		            		var extraParams = {
    									tid : action.result.tid,period:null,taskseq:null
    								};
    		            		store.on("beforeload",function(store, operation, eOpts) {
    											Ext.apply(store.proxy.extraParams,extraParams);
    										});
    		            		store.load();
    				    		
    		            		Ext.Msg.alert("提示", "上传成功！");
    		            	}else{
    		            		Ext.MessageBox.hide();
    		            		Ext.Msg.alert("提示",action.result.info);
    		            	}
    		            }
    		        });
    			}
    			
    		}else{
    	    	Ext.MessageBox.wait("正在执行操作......", "提示");
    	    	attachmentForm.getForm().submit({
    	    		params: {
    	    			period: period
    	            },
    	            success: function(form, action) {
    	            	if(action.result.info == 'success'){
    	            		Ext.MessageBox.hide();
    	            		var length=(action.result.list).length;
    	            		if(action.result.tid==null){
    	            			Ext.Msg.alert("提示", "上传失败！");
    	            			 return;
    	            		}
    	            		var extraParams = {
    								tid : action.result.tid,period:null,taskseq:null
    							};
    	            		store.on("beforeload",function(store, operation, eOpts) {
    										Ext.apply(store.proxy.extraParams,extraParams);
    									});
    	            		store.load();
    			    		
    	            		Ext.Msg.alert("提示", "上传成功！");
    	            	}else{
    	            		Ext.MessageBox.hide();
    	            		Ext.Msg.alert("提示",action.result.info);
    	            	}
    	            },
    	            failure: function(form, action) {
    	            	Ext.MessageBox.hide();
    	            	Ext.Msg.alert("提示", "上传失败！");
    	            }
    	        });
    		}
       	});
		
    },
    onSearchClick:function(button){
    	var year =button.up('form').down('combobox[name=year]').getValue();
    	var month =button.up('form').down('combobox[name=month]').getValue();
    	if(year==null||year==''||month==null||month==''){
    		Ext.Msg.alert('提示', '会计周期的年和月不能为空');
        	return;
    	}
    	var period =year+'-'+month;
    	var taskseq =button.up('form').down('combobox[name=taskseq]').getValue();

    	var grid = Ext.ComponentQuery.query("viewport salesupload grid")[0];
    	var store = grid.getStore();
    	var extraParams = {
				tid : null,period:period,taskseq:taskseq
			};
		store.on("beforeload",function(store, operation, eOpts) {
						Ext.apply(store.proxy.extraParams,extraParams);
					});
		store.load();
    	
    },

//    下载
    onDownloadClick:function(button){
        window.open("uploadresalecontrol/importFileDownload");
    },

    //验证 or 提交
    onSaveClick:function(button){
    	var me=this;
    	var year =button.up('form').down('combobox[name=year]').getValue();
    	var month =button.up('form').down('combobox[name=month]').getValue();
    	if(year==null||year==''||month==null||month==''){
    		Ext.Msg.alert('提示', '会计周期的年和月不能为空');
        	return;
    	}
    	var period =year+'-'+month;
       	var param = {dealeruploadresale:dealeruploadresale,value:value}; 
       	Ext.MessageBox.wait("正在执行操作......", "提示");
    	var value=button.getText();
    	
    	var dealeruploadresale=[];
    	var grid = Ext.ComponentQuery.query("viewport salesupload grid")[0];
     	var store = grid.getStore();
	    for (var i = 0; i < store.getCount(); i++) {
	    	var records = Ext.create('BPSPortal.model.UploadReSale');
	    	if(value=='提交'){

	    			
	    		var errmsg=store.getAt(i).data.errormsg;
	    		if(errmsg.indexOf('序列号重复')>=0||errmsg.indexOf('非经销商报备客户')>=0||errmsg.indexOf('无该产品信息')>=0
	    				||errmsg.indexOf('该产品有多条信息请手动选择')>=0||errmsg.indexOf('含税否只能是 是 或者 否')>=0
	    				||errmsg.indexOf('是否特价只能是 是 或者 否')>=0)	
	    		{
	    		
		    		Ext.Msg.alert('提示', '请保持数据没有错误提示后在提交');
		        	return;
		    	}
	    	
	    	if(store.getAt(i).data.region==null||store.getAt(i).data.region==''){
	    		Ext.Msg.alert('提示', '区域不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.dealername==null||store.getAt(i).data.dealername==''){
	    		Ext.Msg.alert('提示', '经销商不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.period==null||store.getAt(i).data.period==''){
	    		Ext.Msg.alert('提示', '会计周期不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.seqno==null||store.getAt(i).data.seqno==''){
	    		Ext.Msg.alert('提示', '序列号不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.custname==null||store.getAt(i).data.custname==''){
	    		Ext.Msg.alert('提示', '订货单位不能为空');
	        	return;
	    	}
	    	
	    	if(store.getAt(i).data.materialname==null||store.getAt(i).data.materialname==''){
	    		Ext.Msg.alert('提示', '产品名称不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.materialcode==null||store.getAt(i).data.materialcode==''){
	    		Ext.Msg.alert('提示', '产品编码不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.currency==null||store.getAt(i).data.currency==''){
	    		Ext.Msg.alert('提示', '币种不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.qty==null||store.getAt(i).data.qty==''){
	    		Ext.Msg.alert('提示', '出货数量不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.paymenttype==null||store.getAt(i).data.paymenttype==''){
	    		Ext.Msg.alert('提示', '付款方式不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.deliverydate==null||store.getAt(i).data.deliverydate==''){
	    		Ext.Msg.alert('提示', '交货日期不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.unitprice==null||store.getAt(i).data.unitprice==''){
	    		Ext.Msg.alert('提示', '单价不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.istax==null||store.getAt(i).data.istax==''){
	    		Ext.Msg.alert('提示', '含税否不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.contractamount==null||store.getAt(i).data.contractamount==''){
	    		Ext.Msg.alert('提示', '未税合同额不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.isspl==null||store.getAt(i).data.isspl==''){
	    		Ext.Msg.alert('提示', '特价不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.dealerstdcost==null||store.getAt(i).data.dealerstdcost==''){
	    		Ext.Msg.alert('提示', '经销商标准成本价（未税）不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.isspl==1){
	    		if(store.getAt(i).data.dealersplcost==null||store.getAt(i).data.dealersplcost==''){
		    		Ext.Msg.alert('提示', '经销商特价成本价（未税）不能为空');
		        	return;
		    	}
	    	}
	    	
	    	if(store.getAt(i).data.dealerstdpo==null||store.getAt(i).data.dealerstdpo==''){
	    		Ext.Msg.alert('提示', '经销商标准采购额（未税）不能为空');
	        	return;
	    	}
	    	if(store.getAt(i).data.dealerstdact==null||store.getAt(i).data.dealerstdact==''){
	    		Ext.Msg.alert('提示', '经销商实际采购额（未税）不能为空');
	        	return;
	    	}
	    	
	    	if(store.getAt(i).data.rebateamount==null||store.getAt(i).data.rebateamount==''){
	    		Ext.Msg.alert('提示', '返货金额（未税）不能为空');
	        	return;
	    	}
	    	
	    	}
	    	records.data.id =store.getAt(i).data.id; 
	    	records.data.tid =store.getAt(i).data.tid;
	    	records.data.region =store.getAt(i).data.region;
	    	records.data.dealername =store.getAt(i).data.dealername;
	    	records.data.period =store.getAt(i).data.period;
	    	records.data.seqno =store.getAt(i).data.seqno;
	    	records.data.custname =store.getAt(i).data.custname;
	    	records.data.materialname =store.getAt(i).data.materialname;
	    	records.data.currency =store.getAt(i).data.currency;
	    	records.data.materialcode =store.getAt(i).data.materialcode;
	    	records.data.batchno =store.getAt(i).data.batchno;
	    	records.data.qty =store.getAt(i).data.qty;
	    	records.data.paymenttype =store.getAt(i).data.paymenttype;
	    	records.data.deliverydate =store.getAt(i).data.deliverydate;
	    	records.data.unitprice =store.getAt(i).data.unitprice;
	    	records.data.istax =store.getAt(i).data.istax;
	    	records.data.contractamount =store.getAt(i).data.contractamount;
	    	records.data.isspl =store.getAt(i).data.isspl;
	    	records.data.dealerstdcost =store.getAt(i).data.dealerstdcost;
	    	records.data.dealersplcost =store.getAt(i).data.dealersplcost;
	    	records.data.dealerstdpo =store.getAt(i).data.dealerstdpo;
	    	records.data.dealerstdact =store.getAt(i).data.dealerstdact;
	    	records.data.rebateamount =store.getAt(i).data.rebateamount;
	    	records.data.bpsstdcost =store.getAt(i).data.bpsstdcost;
	    	records.data.bpssplcost =store.getAt(i).data.bpssplcost;
	    	records.data.rebatediff =store.getAt(i).data.rebatediff;
	    	records.data.remark =store.getAt(i).data.remark;
	    	dealeruploadresale.push(records.data);
	        }
	       	var param = {dealeruploadresale:dealeruploadresale,value:value}; 
	       	Ext.MessageBox.wait("正在执行操作......", "提示");
	       	uploadresalecontrol.SaveOrCommits(param,function(result){ 
       	    if(result.success){
       	    	Ext.MessageBox.hide();
       	    	Ext.toast({
	                    title: 'Tips', html: result.msg, align: 't',
	                    width: 240,
	                    bodyPadding: 10
	            });
       	    	store.load();
       	    	if(value=='提交'){
       	    		me.getView().close();
       	    	}
       	    // me.getView().close();
       	    }else{
       	    	Ext.MessageBox.hide();
	        		 Ext.toast({
		                    title: 'Tips', html: result.msg, align: 't',
		                    width: 240,
		                    bodyPadding: 10
		                });
 	        	}
       });
    	
    	
    },
    //添加
//    onAddClick:function(button){
//    	var grid = Ext.ComponentQuery.query("viewport salesupload grid")[0];
//        var store=grid.getStore();
//        var count = (store.getCount() > 0) ? store.getCount() : 0; 
//        var record = new DealerPortal.model.UploadReSale({id:-1});
//        store.insert(0, record); 
//        grid.getSelectionModel().select(record);
//    },
//    onUploadReSaleLoad:function(store,records,successful,eOpts){
//   		if(store.getCount()<=0){
//   			return;
//   		}
//		for (var i = 0; i < store.getCount(); i++) {
//		     var rec = 	store.getAt(i);
//		     var materialname = rec.get('materialname');
//		     if(materialname!=null){
//		    		var param = {materialname:materialname}; 
//		    		materialInfoControl.getAllList(param,function(result){
//		    			if(result.records.length>0){
//		    				rec.set('materialcode',result.records[0].materialcode);
//		    			}
//		       });
//		    	 
//		    }
//	    }
//	},
	onAfterRender:function(){
		var  me=this;
    	var store = me.getViewModel().getStore("uploadtask");
        var paras = { taskowner:'0'};
        Ext.apply(store.proxy.extraParams, paras);
        store.load();
		
		
		var date=new Date();
		var gedt = Ext.util.Format.date(date, 'Y-m-d');
		var tarr = gedt.split('-');
		var year = tarr[0]; 
		var month =tarr[1];
//		var months= ''+ (month - 01);
//		if(month=='01'){
//			months=''+12;
//			year=''+ (year-1);
//		}
		if(month.length!=2){
			month='0'+month
		}
		var year =this.lookupReference('years').setValue(year);
		var month =this.lookupReference('months').setValue(month);
	},
//	onDeleteClicks:function(btn){
//		var grid=this.lookupReference('uploadresalequerygrid');
//		var store = grid.getStore();
//		var records=grid.getSelectionModel().getSelection();
//		if(records.length<=0){
//			Ext.Msg.alert('提示', '请选择数据');
//         	return;
//		}
//		if(records[0].data.taskconfirm==0){//提交
//			Ext.Msg.alert('提示', '数据已提交不能删除');
//         	return;
//		}
//		if(records[0].data.taskconfirm==1){//确认
//			Ext.Msg.alert('提示', '数据已确认不能删除');
//         	return;
//		}
//		var id=records[0].data.id;
//	    var content='是否执行此操作？';
//	     Ext.Msg.confirm("删除", content, function (btn) {
//	         if (btn == "yes") {
//	            	var param = {tid:id}; 
//	               	Ext.MessageBox.wait("正在执行操作......", "提示");
//	               	uploadresalecontroller.DeleteInfo(param,function(result){ 
//	           	    if(result.success){
//	           	    	Ext.MessageBox.hide();
//	           	    	Ext.toast({
//	                            title: 'Tips', html: result.msg, align: 't',
//	                            width: 240,
//	                            bodyPadding: 10
//	                    });
//	           	    	store.load();
//	           	    }else{
//	           	    	Ext.MessageBox.hide();
//	                		 Ext.toast({
//	        	                    title: 'Tips', html: result.msg, align: 't',
//	        	                    width: 240,
//	        	                    bodyPadding: 10
//	        	                });
//	        	        	}
//	           }); 
//	        	 
//	         }
//	     });
//
//	},
	onDeleteClick:function(btn){
		var grid=this.lookupReference('uploadresalequerygrid');
    	var store = grid.getStore();
    	if(store.getCount()<1){
         	Ext.Msg.alert('提示', '没有操作数据');
         	return;
         }
	    for (var i = 0; i < store.getCount(); i++) {
	    	 var id=store.getAt(0).data.tid; 
	    }
	    var content='是否执行此操作？';
	     Ext.Msg.confirm("删除", content, function (btn) {
	         if (btn == "yes") {
	            	var param = {tid:id}; 
	               	Ext.MessageBox.wait("正在执行操作......", "提示");
	               	uploadresalecontrol.DeleteInfo(param,function(result){ 
	           	    if(result.success){
	           	    	Ext.MessageBox.hide();
	           	    	Ext.toast({
	                            title: 'Tips', html: result.msg, align: 't',
	                            width: 240,
	                            bodyPadding: 10
	                    });
	           	    	store.removeAll();
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
	     });

	},
//	onDetailClick:function(btn){
//		
//		var grid = Ext.ComponentQuery.query("viewport salestaskquery grid")[0];
//		var records=grid.getSelectionModel().getSelection();
//		if(records.length<=0){
//			Ext.Msg.alert('提示', '请选择数据');
//         	return;
//		}
//		 var record=records[0];
//			var vmData = {};
//			vmData.data = record;
//			record.phantom = true;
//			vmData.id = record.data.id;
//
//			this.fireViewEvent('viewObject', this.getView(),
//					'salesquery', vmData);
//		
//	},
//	onAfterRenders:function(){
//		var me = this;
//		var records = me.getViewModel().get('data');
//		var tid = records.data.id;
//		var grid = me.lookupReference('uploadresalequerygrid'), 
//		store = me.getViewModel().getStore("uploadresalequery");
//		var paras = {
//			tid : tid
//		};
//		Ext.apply(store.proxy.extraParams, paras);
//		store.load();
//
//	}
});
