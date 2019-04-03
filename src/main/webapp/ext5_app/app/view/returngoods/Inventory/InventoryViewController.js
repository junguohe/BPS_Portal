
Ext.define('BPSPortal.view.returngoods.Inventory.InventoryViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.Inventory',
    
    
    onAfterRenderW:function(){
	    	var date=new Date();
			var gedt = Ext.util.Format.date(date, 'Y-m-d');
			var tarr = gedt.split('-');
			var year = tarr[0]; 
			var month =tarr[1];
			var months= ''+ (month - 01);
			if(month=='01'){
				months=''+12;
				year=''+ (year-1);
			}
			if(months.length!=2){
				months='0'+months
			}
			var year =this.lookupReference('years').setValue(year);
			var month =this.lookupReference('months').setValue(months);
	    },
	    onExportClicks:function(button){
	    	var grid = Ext.ComponentQuery.query("viewport Inventory grid")[0];
			var records=grid.getSelectionModel().getSelection();
			if(records.length <=0){
				Ext.Msg.alert('提示', '请选择数据');
	         	return;
			}
			var tid=records[0].data.id;
			var type='inventory';
			window.open("uploadtaskinfocontrol/UploadDownload?tid="+tid+'&type='+type);
	    },
    onSearchClick:function(btn){
        var grid = Ext.ComponentQuery.query("viewport Inventory grid")[0];
        var year =btn.up('form').down('combobox[name=year]').getValue();
    	var month =btn.up('form').down('combobox[name=month]').getValue();
    	var period =null;
    	if(year!=null&&year!=''&&month!=null&&month!=''){
    		var period =year+'-'+month;
    	}
    	if(year!=null&&year!=''&&month==null&&month==''){
    		var period =year;
    	}
    	if(year==null&&year==''&&month==null&&month==''){
    		var period =month;
    	}
        var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
        var taskconfirm = btn.up('form').down('combobox[name=taskconfirm]').getValue();
        var store = grid.getStore(); 
        /*传name到后台查询*/
        var extraParams = {period:period,dealername:dealername,taskconfirm:taskconfirm,taskcontent:'inventory'};
        
        store.on("beforeload", function (store, operation, eOpts) {
					 Ext.apply(store.proxy.extraParams, extraParams);
					 });
        store.load({
            callback: function () {
            	store.proxy.extraParams = {};
            }
       });			
    },
    onDeleteClick:function(button){
    	var grid=this.lookupReference('taskGrid');
    	var store=grid.getStore();
    	if (grid.getSelectionModel().getSelection().length <= 0) {
    		Ext.Msg.alert('提示', '请选择数据');
        	return;
        }
    	var	records =grid.getSelectionModel().getSelection()[0];
    	var tid =records.data.id;
    	var content='是否删除此数据？';
        Ext.Msg.confirm("删除", content, function (btn) {
            if (btn == "yes") {
            	var param = {tid:tid,type:'inventory'}; 
               	Ext.MessageBox.wait("正在执行操作......", "提示");
               	UploadTaskInfoController.DeleteTask(param,function(result){ 
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
    },
    onDetailClick:function(btn){
    	var grid=this.lookupReference('taskGrid');
    	var store=grid.getStore();
    	if (grid.getSelectionModel().getSelection().length <= 0) {
    		Ext.Msg.alert('提示', '请选择数据');
        	return;
        }
    	
    	var	records =grid.getSelectionModel().getSelection()[0];
    	if(records.data.taskconfirm==2){
			Ext.Msg.alert('提示', '此数据已被驳回');
			return;
		}
    	var vmData = {};
    	 vmData.data = records;
	     records.phantom = true;
	     vmData.id = records.data.id;
	     this.fireViewEvent('viewObject', this.getView(), 'inventorydetail', vmData);
    	
        
    	
    },

    onAfterRenders:function(){
    	var me=this;
    	var records=me.getViewModel().get('data');
    	var tid=records.data.id;
    	  var grid = me.lookupReference('uploadinventoryGrid'),
              store = me.getViewModel().getStore("uploadinventory");
    	    var paras = { tid: tid};
	        Ext.apply(store.proxy.extraParams, paras);
	        store.load();
    	  
 
    },
    onsubmitClick:function(button){
    	var me = this;
    	var inventory=[];
    	var grid=this.lookupReference('uploadinventoryGrid');
     	var store = grid.getStore();
	    for (var i = 0; i < store.getCount(); i++) {
	    	var records = Ext.create('BPSPortal.model.UploadInventory');
	    	records.data.id =store.getAt(i).data.id; 
	    	records.data.tid =store.getAt(i).data.tid;
	    	records.data.dealername =store.getAt(i).data.dealername;
	    	records.data.period =store.getAt(i).data.period;
	    	records.data.materialname =store.getAt(i).data.materialname;
	    	records.data.materialcode =store.getAt(i).data.materialcode;
	    	records.data.qty =store.getAt(i).data.qty;
	    	records.data.adjdate =store.getAt(i).data.adjdate;
	    	records.data.errormsg =store.getAt(i).data.errormsg;
	    	inventory.push(records.data);
	        }
	       	var param = {inventory:inventory}; 
	       	Ext.MessageBox.wait("正在执行操作......", "提示");
	       	uploadinventorycontroller.SaveOrCommit(param,function(result){ 
       	    if(result.success){
       	    	Ext.MessageBox.hide();
       	    	Ext.toast({
	                    title: 'Tips', html: result.msg, align: 't',
	                    width: 240,
	                    bodyPadding: 10
	            });
       	    	me.getView().close();
       	    	var grid = Ext.ComponentQuery.query("viewport Inventory grid")[0];
       	        var store = grid.getStore(); 
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
    	
    	
    },
	onRejectClick:function(button){
		var grid = this.lookupReference('taskGrid');
		var store = grid.getStore();
		if (grid.getSelectionModel().getSelection().length <= 0) {
			Ext.Msg.alert('提示', '请选择数据');
			return;
		}
		var records = grid.getSelectionModel().getSelection()[0];
		
		if(records.data.taskconfirm!=0){
			Ext.Msg.alert('提示', '请选择待确认的数据进行操作');
			return;
		}
		
		var tid = records.data.id;
		var content = '是否驳回此数据？';
		Ext.Msg.confirm("驳回", content, function(btn) {
			if (btn == "yes") {
    	        Ext.create("Ext.window.Window", {
    	        	requires: [
    	        	           'BPSPortal.view.returngoods.Inventory.InventoryViewController',
    	        	       ],
    	        	       controller: 'Inventory',
    	        	title:'驳回意见',
    	        	modal:true,
    	        	height: 200,
    	            width: 400,
    	            items: [
                            {
                                xtype: 'fieldcontainer',
                                layout: 'column',
                                padding: '40 20 20 20',
                                fieldLabel: '',
                                items: [
        	                        {
        	                            xtype: 'textfield',
        	                            fieldLabel: '意见',
        	                            name: 'remark',
        	                            reference:'remark'
        	                        }
                                ]
                            }
                        ],
                        dockedItems:[
                                     {
                                         xtype: 'toolbar',
                                         dock: 'bottom',
                                         items: [
                                             {
                                                 xtype: 'button',
                                                 text: '确认',
                                                 listeners: {
                                                     click:function(button){
                                                    	var remark = button.up('window').down('textfield[name=remark]').getValue();
                                                    	if (remark==null||remark=='') {
                                							Ext.Msg.alert('提示', '请填写意见');
                                							return;
                                						}
                         								var param = {
                         									tid : tid,
                         									remark:remark,
                         									type : 'inventory'
                         								};
                                                 		Ext.MessageBox.wait("正在执行操作......", "提示");
                        								UploadTaskInfoController.DeleteTask(param,
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
                        												button.up('window').close();
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
                                                 }
                                             }
                                         ]
                                     }
                                 ]
    	          }).show();
		
			}
		});
	}
});
