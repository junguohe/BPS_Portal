
Ext.define('BPSPortal.view.price.pricepolicy.PricePolicyViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.pricepolicy',
    
    //上传
    onUploadClick:function(button){
		var attachmentForm = this.getView().down('#uploadForm');
		 var grid = Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
    	var store = grid.getStore();
    	store.removeAll();
    	attachmentForm.getForm().submit({
            waitMsg: '正在上传...',
            success: function(form, action) {
            	if(action.result.info == 'success'){
            	//	console.log(action.result.info);
            		var length=(action.result.list).length;
		    		for(var i=0;i<length;i++){
		    			store.insert(0,(action.result.list)[i]);
		    		}
		    		
            		Ext.Msg.alert("提示", "上传成功！");
            	}else{
            		Ext.Msg.alert("提示",action.result.info);
            	}
            },
            failure: function(form, action) {
            	//Ext.MessageBox.hide();
            	Ext.Msg.alert("提示", "上传失败！");
            }
        });
    },

    //下载
    onDownloadClick:function(button){
    	var code = button.up('form').down('combobox[name=code]').getRawValue(); 
    	if(code==''||code==null){ 
    		Ext.Msg.alert('提示', '请填写价格政策编码');
        	return;
    	}
        window.open("PriceStrategyDetailController/importFileDownload?billno="+code);
    },
    onDeleteClick:function(button){
    	 var grid = Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
     	 var store = grid.getStore();
		 var record = button.getWidgetRecord();
	//	 console.log(record.data.id);
		 
		 store.remove(record);
		 
//		 if(record.data.id==undefined){
//			 store.remove(record);
//		 }else{
//				var param = {id:record.data.id};
//				pricestrategydetailcontroller.DeletePriceStrategyDetail(param,function(result){ 
//		       	    if(result.success){
//		       	    	store.remove(record);
//
////		       	    	Ext.toast({
////			                    title: 'Tips', html: result.msg, align: 't',
////			                    width: 240,
////			                    bodyPadding: 10
////			            });
//		       	    }
//					});		 
//				}
    },
    //验证  提价
    onSaveClick:function(button){
    	var me=this;
    	var code = button.up('form').down('textfield[name=codes]').getValue();
    	var validfrom = button.up('form').down('datefield[name=validfrom]').getValue();
    	var validto = button.up('form').down('datefield[name=validto]').getValue();
    	var publicdate = button.up('form').down('datefield[name=publicdate]').getValue();
    	var grid = Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
     	var store = grid.getStore();
    	if(store.getCount()<1){
         	Ext.Msg.alert('提示', '没有操作数据');
         	return;
         }
    	if(code==''||code==null){
    		Ext.Msg.alert('提示', '政策编码不能为空');
        	return;
    	}
    	if(validfrom==''||validfrom==null){
    		Ext.Msg.alert('提示', '生效时间不能为空');
        	return;
    	}
    	if(validto==''||validto==null){
    		Ext.Msg.alert('提示', '失效时间不能为空');
        	return;
    	}
    	if(publicdate==''||publicdate==null){
    		Ext.Msg.alert('提示', '发布时间不能为空');
        	return;
    	}
    	var value=button.getText();
    	
    	if(value=='验证'){
    		var status='0';
    	}else{
    		var status='1';
    	}
    	var record = Ext.create('BPSPortal.model.PriceStrategy');
    	
    	record.data.sid='-1';
    	record.data.code=code;
    	record.data.validfrom=validfrom;
    	record.data.validto=validto;
    	record.data.publicdate=publicdate;
    	record.data.status=status;
    	
    	
    	var pricedetail=[];
    	var grid = Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
     	var store = grid.getStore();
	    for (var i = 0; i < store.getCount(); i++) {
	    	var records = Ext.create('BPSPortal.model.PriceStrategyDetail');
	    	if(store.getAt(i).data.listprice==null||store.getAt(i).data.listprice==''){
	    		if(store.getAt(i).data.listprice!=0){
	    			Ext.Msg.alert('提示', '市场价格(未税)不能为空');
	            	return;
	    		}
    			
    		}
	    	if(store.getAt(i).data.listpriceinc==null||store.getAt(i).data.listpriceinc==''){
	    		if(store.getAt(i).data.listpriceinc!=0){
	    			Ext.Msg.alert('提示', '市场价格（含税）不能为空');
	            	return;
	    		}
    		}
	    	if(store.getAt(i).data.dealerprice==null||store.getAt(i).data.dealerprice==''){
    			Ext.Msg.alert('提示', '经销商价格(未税)不能为空');
            	return;
    		}
	    	if(store.getAt(i).data.dealerpriceinc==null||store.getAt(i).data.dealerpriceinc==''){
    			Ext.Msg.alert('提示', '经销商价格（含税）不能为空');
            	return;
    		}
	    	if(store.getAt(i).data.currency==null||store.getAt(i).data.currency==''){
    			Ext.Msg.alert('提示', '币种不能为空');
            	return;
    		}
	    	if(store.getAt(i).data.custsp==null||store.getAt(i).data.custsp==''){
    			Ext.Msg.alert('提示', '客户起始特价不能为空');
            	return;
    		}
	    	
	    	if(value!='验证'){
	    		
	    		var errmsg=store.getAt(i).data.errormsg;
	    		if(errmsg!=null&&errmsg!=''){
	    			Ext.Msg.alert('提示', '请在没有错误提示后在提交');
	            	return;
	    		}
	    		
	    		var sid=store.getAt(i).data.sid;
	    		if(sid==null||sid==''){
	    			Ext.Msg.alert('提示', '请先验证在提交');
	            	return;
	    		}
	    	}
	    	records.data.id ='-1' 
	    	records.data.materialcode =store.getAt(i).data.materialcode;
	    	records.data.materialname =store.getAt(i).data.materialname;
	    	records.data.lifecycle =store.getAt(i).data.lifecycle;
	    	records.data.assembly =store.getAt(i).data.assembly;
	    	records.data.listprice =store.getAt(i).data.listprice;
	    	records.data.listpriceinc =store.getAt(i).data.listpriceinc;
	    	records.data.dealerprice =store.getAt(i).data.dealerprice;
	    	records.data.dealerpriceinc =store.getAt(i).data.dealerpriceinc;
	    	if(store.getAt(i).data.dealerprofit=='-∞'){
	    		records.data.dealerprofit =0;
	    	}else{
	    		records.data.dealerprofit =store.getAt(i).data.dealerprofit;
	    	}
//	    	console.log(records.data.dealerprofit);
//	    	return;
	    	records.data.lastprice =store.getAt(i).data.lastprice;
	    	records.data.reduceper =store.getAt(i).data.reduceper;
	    	records.data.ispublic =store.getAt(i).data.ispublic;
	    	records.data.ismain =store.getAt(i).data.ismain;
	    	records.data.currency =store.getAt(i).data.currency;
	    	records.data.custsp =store.getAt(i).data.custsp;
	    	if(store.getAt(i).data.eol ==  null ||store.getAt(i).data.eol ==  undefined){
	    		records.data.eol =null;
	    	}else{
	    		records.data.eol =store.getAt(i).data.eol;
	    	}

	    	pricedetail.push(records.data);
	        }
	    
	       	var param = {records:record.data,pricestrategy:pricedetail}; 
	       	Ext.MessageBox.wait("正在执行操作......", "提示");
	       	pricestrategydetailcontroller.createprice(param,function(result){ 
       	    if(result.success){
       	    	Ext.MessageBox.hide();
       	    	Ext.toast({
	                    title: 'Tips', html: result.msg, align: 't',
	                    width: 240,
	                    bodyPadding: 10
	            });
       	    	console.log(result.data[0].sid);
       	    	
       	    	if(value!='验证'){
       	    		
       	    		me.getView().close();
       	    		
       	    	}else{
       	    		var extraParams = {sid:result.data[0].sid};
   	            	store.on("beforeload", function (store, operation, eOpts) {
   	        					 Ext.apply(store.proxy.extraParams, extraParams);
   	        					});
   	            	store.load();	
       	    	}
       	    
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
    
    onAddClick:function(button){
    	var code = button.up('form').down('textfield[name=codes]').getValue();
    	var validfrom = button.up('form').down('datefield[name=validfrom]').getValue();
    	var validto = button.up('form').down('datefield[name=validto]').getValue();
    	var publicdate = button.up('form').down('datefield[name=publicdate]').getValue();
    	if(code==''||code==null){
    		Ext.Msg.alert('提示', '政策编码不能为空');
        	return;
    	}
    	if(validfrom==''||validfrom==null){
    		Ext.Msg.alert('提示', '生效时间不能为空');
        	return;
    	}
    	if(validto==''||validto==null){
    		Ext.Msg.alert('提示', '失效时间不能为空');
        	return;
    	}
    	if(publicdate==''||publicdate==null){
    		Ext.Msg.alert('提示', '发布时间不能为空');
        	return;
    	}
    	var grid = Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
        var store=grid.getStore();
        var count = (store.getCount() > 0) ? store.getCount() : 0; 
        var record = new BPSPortal.model.PriceSpecialDetail({ismain:0});
        store.insert(0, record); 
        grid.getSelectionModel().select(record);
    },
    
    //调价试算
    onTrialClick:function(button){
    		var code = button.up('form').down('textfield[name=codes]').getValue();
    		if(code==''||code==null){
        		Ext.Msg.alert('提示', '政策编码不能为空');
            	return;
        	}
	        var vmData = {};
	        vmData.code = code;
	        this.fireViewEvent('viewObject', this.getView(), 'pricetrial', vmData);
    },
    getLastPrice:function(field, newValue, oldValue, eOpts){
    	var publicdate = this.lookupReference('publicdates').getValue();
    	var codes = this.lookupReference('codes').getValue();
    	if( field.displayTplData == undefined){
    		return;
    	}
    	var materialid=field.displayTplData[0].id;
    	var param = {materialid:materialid,publicdate:publicdate}; 
    	var grid = Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
    	 var record= grid.getSelectionModel().getSelection()[0];
    	pricestrategydetailcontroller.readPriceStrategys(param,function(result){ 
   	    if(result.success){
   	    	if(result.data[0] == undefined){
   	    		record.set('lastprice',"");
   	    	}else{
   	    		var price=result.data[0].listpriceinc;
   	   	    	record.set('lastprice',price);
   	    	}
   	    	
   	    	
   	    }
   });
    	
    }
});
