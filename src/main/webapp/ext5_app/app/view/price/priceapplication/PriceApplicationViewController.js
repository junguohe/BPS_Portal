
Ext.define('BPSPortal.view.price.priceapplication.PriceApplicationViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.priceapplication',
    
    
    onAfterRender:function(){
    	var me =this;
    	var applicator=me.lookupReference('applicator');
    	var param = {}; 
    	auuserController.getUser(param,function(result){ 
	    	if(result.success){
	    		applicator.setValue(result.data.username);
	    	}
      	});
    },
    onValueChange:function(field, newValue, oldValue, eOpts){
      	  var grid=this.lookupReference('pricespecialdetail');
      	  var record = grid.getSelectionModel().getSelection()[0];
      	  var store=grid.getStore();
      	 for (var i = 0; i < store.getCount(); i++) {
   	        var prive=store.getAt(i).data.applyprice;
      	 }
    },

    
    onAddClice:function(button){
    	var grid=this.lookupReference('pricespecialdetail');
    	var store=grid.getStore();
    	var count = (store.getCount() > 0) ? store.getCount() : 0;
    	var seqnos=count+1;
    	 var record = new BPSPortal.model.PriceSpecial({seqno:seqnos,isnotrebate:0});
         store.insert(count, record);       
         grid.getSelectionModel().select(record);
    	
    	
    },
    //提交申请
    onSumbitClick:function(button){
    	var me=this;
    	var form = button.up('form');
	    var obj = me.getView().getForm().getValues();
	    var record = Ext.create('BPSPortal.model.PriceSpecial');
	    record.data.spid='-1';
	    record.data.billno=obj.billno;
	    record.data.applytype=obj.applytype;
	    record.data.applicator = obj.applicator;
	    record.data.applydate = obj.applydate;
	    record.data.billstatus = 1;
	    var pricespecialdetail=[];
	    var contact = [];
	    var store=me.getViewModel().getStore('pricespecialdetailstore');
        for (var i = 0; i < store.getCount(); i++) {
        	
        	var records = Ext.create('BPSPortal.model.PriceSpecialDetail');
        	 if(store.getAt(i).data.cpid==""||store.getAt(i).data.cpid==null||(store.getAt(i).data.cpid).length!=36){
 	        	Ext.Msg.alert('提示', '客户名称不能为空');
	            	return;
 	        }
        	 if(store.getAt(i).data.materialid==""||store.getAt(i).data.materialid==null||(store.getAt(i).data.materialid).length!=36){
  	        	Ext.Msg.alert('提示', '产品编号不能为空');
 	            	return;
  	        }
        	 if(store.getAt(i).data.projname==""||store.getAt(i).data.projname==null){
   	        	Ext.Msg.alert('提示', '项目名称不能为空');
  	            	return;
   	        }
        	if(store.getAt(i).data.projstatus==""||store.getAt(i).data.projstatus==null){
    	        	Ext.Msg.alert('提示', '项目状态不能为空');
   	            	return;
    	    }
        	if(store.getAt(i).data.volyear==""||store.getAt(i).data.volyear==null){
	        	Ext.Msg.alert('提示', '年用量不能为空');
	            	return;
        	}
        	if(store.getAt(i).data.compmaterial==""||store.getAt(i).data.compmaterial==null){
	        	Ext.Msg.alert('提示', '竞争对手型号不能为空');
	            	return;
        	}
        	if((store.getAt(i).data.comppriceinc =="" || store.getAt(i).data.comppriceinc==null )&& store.getAt(i).data.comppriceinc!=0){
	        	Ext.Msg.alert('提示', '竞争对手价格（含税）不能为空');
	            	return;
        	}
        	if((store.getAt(i).data.applypriceinc==""||store.getAt(i).data.applypriceinc==null)&&store.getAt(i).data.applypriceinc!=0){
	        	Ext.Msg.alert('提示', '客户期望价格（含税）不能为空');
	            	return;
        	}
        	if((store.getAt(i).data.applyprice==""||store.getAt(i).data.applyprice==null)&& store.getAt(i).data.applyprice!=0){
	        	Ext.Msg.alert('提示', '客户期望价格（未税）不能为空');
	            	return;
        	}
        	records.data.did ='-1'; 
	    	records.data.seqno =store.getAt(i).data.seqno; 
	    	
	    	var type = button.up('form').down('combobox[name=applytype]').getValue();
	    	records.data.applytype=type; 
	    	records.data.cpid =store.getAt(i).data.cpid; 
	    	records.data.materialid =store.getAt(i).data.materialid; 
	    	records.data.projname =store.getAt(i).data.projname; 
	    	records.data.projstatus =store.getAt(i).data.projstatus; 
	    	records.data.volyear =store.getAt(i).data.volyear; 
	    	records.data.compmaterial =store.getAt(i).data.compmaterial; 
	    	records.data.comppriceinc =store.getAt(i).data.comppriceinc; 
	    	records.data.applypriceinc =store.getAt(i).data.applypriceinc; 
	    	records.data.applyprice =store.getAt(i).data.applyprice; 
	    	records.data.remark =store.getAt(i).data.remark;  
	    	records.data.currency =store.getAt(i).data.currency; 
	    	 if(store.getAt(i).data.isnotrebate=='是' || store.getAt(i).data.isnotrebate=='1'){
			    	records.data.isnotrebate=1;
			    }else{
			    	records.data.isnotrebate=0;
			    }
	    	pricespecialdetail.push(records.data);
	    }
        var param = {record:record.data,pricespecialdetail:pricespecialdetail}; 
        Ext.MessageBox.wait("正在执行操作......", "提示");
	       	priceSpecialControl.createPriceSpecial(param,function(result){ 
		    	if(result.success){
		    		Ext.MessageBox.hide();
		    		 Ext.toast({
		                    title: 'Tips', html: result.msg, align: 't',
		                    width: 240,
		                    bodyPadding: 10
		                });
	        			 
	        			 me.getView().close();
    	        		 
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
    
    onDelClice:function(button){
    	var grid = this.lookupReference('pricespecialdetail');
		var record = button.getWidgetRecord();
		var store = grid.getStore();
		var content='是否删除此数据？';
        Ext.Msg.confirm("删除", content, function (btn) {
            if (btn == "yes") {
                store.remove(record);
               
            }
        })
    },
    onGetCustClick:function(thisGrid, records){
    },
    onGetMaterial:function(text,event,eOpts){
    	var value=text.getValue();
    	   var param = {materialcode:value}; 
    	   materialInfoControl.getAllList(param,function(result){ 
		    	if(result.success){
		    	}
	       	});
    	
    	var grid=this.lookupReference('pricespecialdetail');
    	var record=grid.getSelectionModel().getSelection()[0];
    	    record.set('materialname',value);
    	
    }
    
});
