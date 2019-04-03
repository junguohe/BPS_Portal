
Ext.define('BPSPortal.view.customer.customerupdate.CustomerUpdateViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.customerupdate',
    onAfterRenderW:function(){
	    	var me=this;
	    	var form = me.getView().getForm();
	    	me.getView().down('textfield[name="dealername"]').focus(true, 100)
	    	var button = me.getView().down('button[itemId=searchBtn]');
	    	this.keyNav=Ext.create('Ext.util.KeyNav',this.getView().down('form').el,{
	    		enter:function(){
	    			me.onSearchClick(button);
	    		},
	    		scope:this
	    	})
	    },
  //搜索
    onSearchClick: function (btn){
        var roleGrid = Ext.ComponentQuery.query("viewport customerupdate grid")[0];
        var name = btn.up('form').down('textfield[name=name]').getValue();
        var prodid = btn.up('form').down('combobox[name=prodid]').getValue();
        var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
        var bpssales = btn.up('form').down('textfield[name=bpssales]').getValue();
        var bpsfae = btn.up('form').down('textfield[name=bpsfae]').getValue();
        var regstatus = btn.up('form').down('combobox[name=regstatus]').getValue();
        var bestlatertime = btn.up('form').down('datefield[name=bestlatertime]').getValue();
        var roleStore = roleGrid.getStore(); 
        /*传name到后台查询*/
        
     
        var extraParams = {name:name,prodid:prodid,regstatus:regstatus,region:null,
        		dealername:dealername,isshare:null,bpssales:bpssales,bpsfae:bpsfae,
        		parenetcompany:null,telno:null,bestlatertime:bestlatertime,address:null,custtype:null,taxno:null};
        roleStore.on("beforeload", function (roleStore, operation, eOpts) {
					 Ext.apply(roleStore.proxy.extraParams, extraParams);
					});
		roleStore.load();			
    },
    
    //批量转移
    onTransferClick:function(){
    	var grid=this.lookupReference('customerGrid');
    	var store=grid.getStore();
    	if (grid.getSelectionModel().getSelection().length <= 0) {
    		Ext.Msg.alert('提示', '请选择要转移的数据');
        	return;
        }
    	
    	var records = grid.getSelectionModel().getSelection();
    	for(var j =0;j<records.length;j++){
        	if(records[j].data.regstatus!=1){
        		Ext.Msg.alert('提示', '请选择正确的数据进行操作');
            	return;
        	}
		}
    	var vmData = {};
	        vmData.data = records;
	        Ext.create("BPSPortal.view.customer.customerupdate.UpdateWin", {
	        	records: records
	          }).show();
    	
        
    	
    },
    onAfterRenders:function(){
    	var me=this;
    	var records=me.getView().records;
    	var stores = me.getViewModel().getStore("customers");
        for(var j =0;j<records.length;j++){
	    	stores.insert(0,records[j]);
		}
    },
    //批量取消
    onCancelClick:function(){
    	var grid=this.lookupReference('customerGrid');
    	var store=grid.getStore();
    	if (grid.getSelectionModel().getSelection().length <= 0) {
    		Ext.Msg.alert('提示', '请选择要取消的数据');
        	return;
        }
    	var ids=[];
    	var records = grid.getSelectionModel().getSelection()
        for(var j =0;j<records.length;j++){
        	if(records[j].data.regstatus!=1){
        		Ext.Msg.alert('提示', '请选择正确的数据进行操作');
            	return;
        	}
	    	ids.push(records[j].data.cpid);
	    	
		}
    	var param = {ids:ids}; 
    	
    	
		var content='确定执行此操作？';
        Ext.Msg.confirm("提示", content, function (btn) {
            if (btn == "yes") {
            	customerControl.cancelCust(param,function(result){
              		 if(result.success){
                  		 Ext.toast({
           	                    title: 'Tips', html: result.msg, align: 't',
           	                    width: 240,
           	                    bodyPadding: 10
                  		 }); 
                  		store.load();
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
    	
    },
    //批量转移提交
    onsubmitClick:function(button){
    	var me=this;
    	var grids=me.lookupReference('customersGrid');
    	var stores=grids.getStore();
    	var ids=[];
    	if(stores.getCount()<=0){
   		 Ext.Msg.alert('提示', '没有要提交的数据');
        	return;
    	}
    	 for(var j =0;j<stores.getCount();j++){
 	    	ids.push(stores.getAt(j).data.cpid);
 		}
    	 var dealerid = button.up('window').down('dealerpicker[name=dealerid]').getValue();
    	 var bpsid = button.up('window').down('personsalespicker[name=bpsid]').getValue();
    	 var faeid = button.up('window').down('personpicker[name=faeid]').getValue();
    	 
    	 if((dealerid==null||dealerid=='')&&(bpsid==null||bpsid=='')&&(faeid==null||faeid=='')){
    		 Ext.Msg.alert('提示', '没有新的数据可以更改');
         	return;
    	 }
    	 var param = {ids:ids,dealerid:dealerid,bpsid:bpsid,faeid:faeid}; 
    	 customerControl.updateCustInfo(param,function(result){
    		 if(result.success){
        		 Ext.toast({
	                    title: 'Tips', html: result.msg, align: 't',
	                    width: 240,
	                    bodyPadding: 10
	                }); 
        		 stores.removeAll();
        		 button.up('window').close();
        	}else{
	        		 Ext.toast({
		                    title: 'Tips', html: result.msg, align: 't',
		                    width: 240,
		                    bodyPadding: 10
		                });
 	        	}
    	 });
    	
    	
    },
    //
    onUpdateStatue:function(button){
    	
    	var grid= this.lookupReference('customerGrid');
    	var records = grid.getSelectionModel().getSelection();
    	for(var j =0;j<records.length;j++){
        	if(records[j].data.regstatus!=1){
        		Ext.Msg.alert('提示', '请选择正确的数据进行操作');
            	return;
        	}
		}
    	var vmData = {};
	        vmData.data = records;
	        Ext.create("BPSPortal.view.customer.customerupdate.UpdateWin", {
	        	records: records
	          }).show();
    },
    onDeleteClick:function(button){
    	var grid = this.lookupReference('customersGrid');
		var record = button.getWidgetRecord();
		var store = grid.getStore();
          	store.remove(record);
    },
    onCustomerLoad : function(store, records, successful, eOpts) {
		for (var i = 0; i < store.getCount(); i++) {
				var rec = store.getAt(i);
				if(rec.get('isbps')== 1&&rec.get('active')==1&&rec.get('approvestatus')!=3){
					rec.set('dealername','上海晶丰');
				}
				
	      }
		store.commitChanges();
		
	},
	onUploadRegDateClick:function(){
		var grid= this.lookupReference('customerGrid');
		var store=grid.getStore();
    	var records = grid.getSelectionModel().getSelection();
    	
    	if (grid.getSelectionModel().getSelection().length != 1) {
    		Ext.Msg.alert('提示', '请选择一条数据');
        	return;
        }
    	var record=grid.getSelectionModel().getSelection()[0];
    	
    	if(record.data.regstatus!=1){
    		Ext.Msg.alert('提示', '请选择正确的数据进行操作');
        	return;
    	}
    	
    	
    	
    	//console.log(record.data);
    	var cpid =record.data.cpid;
    	var regstartdate=record.data.regstartdate;
    	console.log(regstartdate);
    	var param = {cpid:cpid};
    	Ext.MessageBox.wait("正在执行操作......", "提示");
   	 		customerControl.UploadRegDate(param,function(result){
	   		 if(result.success){
	   			 Ext.MessageBox.hide();
	       		 Ext.toast({
		                    title: 'Tips', html: result.msg, align: 't',
		                    width: 240,
		                    bodyPadding: 10
		                }); 
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
});
