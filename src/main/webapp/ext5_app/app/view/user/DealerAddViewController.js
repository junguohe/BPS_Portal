
Ext.define('BPSPortal.view.user.DealerAddViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.dealeradd',
    
    	 onSearchClick: function (btn){
    	        var grid = Ext.ComponentQuery.query("viewport dealeradd grid")[0];
    	        var name = btn.up('form').down('textfield[name=dealername]').getValue();
    	        var store=grid.getStore();
    	        /*传name到后台查询*/
    	        var extraParams = {name:name};
    	        store.on("beforeload", function (store, operation, eOpts) {
    						 Ext.apply(store.proxy.extraParams, extraParams);
    						});
    	        store.load({
    	            callback: function () {
    	            	store.proxy.extraParams = {};
    	            }
    	       });			
    	    },
    	    onSaveClick:function(button){
    	    	var grid = Ext.ComponentQuery.query("viewport dealeradd grid")[0];
    	        var store=grid.getStore();
    	    	var me =this;
    	    	var form = Ext.ComponentQuery.query("viewport dealeradd form")[1];
    	    	var obj = form.getValues();
    			var id=-1;
    			var dealercode=obj.dealercode;
    			var dealername=obj.dealername;
    			var email=obj.email;
    			var loginname=obj.loginname;
    			var telno=obj.telno;
    			var ename=obj.ename;
    			var param = {id:id,loginname:loginname,telno:telno,email:email,dealercode:dealercode,ename:ename,dealername:dealername}; 
    			 if(form.isValid()){
    				 Ext.MessageBox.wait("正在执行操作......", "提示");
    				 adduserscontrol.createUserDealer(param,function(result){
     			        	if(result.success){
     			        		Ext.MessageBox.hide();
     			        		 Ext.toast({
     			                    title: 'Tips', html: result.msg, align: 't',
     			                    width: 240,
     			                    bodyPadding: 10
     			                });
     			        		    form.getForm().reset();
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
    		
    			
    	    },
    	    onUpdateClick:function(btn){
    	    	var grid = this.lookupReference('DealerGrid');
    	    	var records = grid.getSelectionModel().getSelection();
   	         	if(records.length<=0){
	   	         	Ext.Msg.alert('提示', '请选择数据');
	   	        	return;
   	         	}
 	         Ext.create("BPSPortal.view.user.UpdateDealerWin", {
 	        	 record : records[0].data
 	          }).show();
 	         
    	    },
    	    
    	    onAfterRenderDealer:function(){
    	    	var me=this;
    	    	var record=me.getView().record;
    	    	var ename=me.lookupReference('ename');
    	    	var email=me.lookupReference('email');
    	    	var expiredate=me.lookupReference('expiredate');
    	    	console.log(record.email);
    	    	ename.setValue(record.ename);
    	    	email.setValue(record.email);
    	    	expiredate.setValue(record.expiredate);
    	    },
    	    onAddClick:function(){
    	         var editDealerPanel = this.lookupReference('editDealerPanel');
    	         editDealerPanel.setCollapsed(false);
    	         var grid = Ext.ComponentQuery.query("viewport dealeradd grid")[0];
    	         var store = grid.getStore();
    	         var count = (store.getCount() > 0) ? store.getCount() : 0; 
    	         var rec = new BPSPortal.model.AddDealer({
    	             id: -(count+1)
    	         });
    	         store.insert(0, rec);
    	         grid.getSelectionModel().select(rec);
    	    },
    	    onFlushClick:function(btn){
    	    	var grid = this.lookupReference('DealerGrid');
    	    	var records = grid.getSelectionModel().getSelection();
   	         	if(records.length<=0){
	   	         	Ext.Msg.alert('提示', '请选择数据');
	   	        	return;
   	         	}
   	         	var userid = records[0].data.userid;
   	         	var param = {userid:userid}; 
				 Ext.MessageBox.wait("正在执行操作......", "提示");
				 auuserController.fulisheAuUser(param,function(result){
 			        	if(result.success){
 			        		Ext.MessageBox.hide();
 			        		 Ext.toast({
 			                    title: 'Tips', html: result.msg, align: 't',
 			                    width: 240,
 			                    bodyPadding: 10
 			                });
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
    	    onEndClick:function(btn){
    	    	var grid = this.lookupReference('DealerGrid');
    	        var store=grid.getStore();
    	    	var records = grid.getSelectionModel().getSelection();
   	         	if(records.length<=0){
	   	         	Ext.Msg.alert('提示', '请选择数据');
	   	        	return;
   	         	}
   	         	var userid = records[0].data.userid;
   	         	var param = {userid:userid}; 
				 Ext.MessageBox.wait("正在执行操作......", "提示");
				 adduserscontrol.endUser(param,function(result){
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
    	    },
    	    //币种管理
    	    onCurrencyClick:function(btn){
    	    	var grid = this.lookupReference('DealerGrid');
    	    	var records = grid.getSelectionModel().getSelection();
   	         	if(records.length<=0){
	   	         	Ext.Msg.alert('提示', '请选择经销商');
	   	        	return;
   	         	}
   	         	var record=records[0];
    	        Ext.create("Ext.window.Window", {
    	        	 requires: [
								'BPSPortal.view.user.DealerAddViewModel',
								'BPSPortal.view.user.DealerAddViewController'
    	        	        ],
        	        viewModel: {
        	            type: 'dealeradd'
        	        },
        	        controller: 'dealeradd',
        	        record: record,
    	        	title:'币种',
    	        	modal:true,
    	        	minHeight: 250,
    	            width: 400,
    	            items: [
                            {
                                xtype: 'gridpanel',
                                reference:'currencyGrid',
                                bind:{
                             	   store: '{currencytype}'
                                },
                                minHeight: 200,
                                listeners: {
                              	  afterrender: 'onAfterRender'
                                },
                                columns: [

									{
									    xtype: 'checkcolumn',
									    header: '启用？',
									    dataIndex: 'checked',
									    width: 180,
									    stopSelection: false
									},
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'value',
                                        width:200,
                                        text: '币种'
                                       
                                    }
                                ],
                                dockedItems: [
                                      {
                                          xtype: 'toolbar',
                                          dock: 'bottom',
                                          items: [
 											{
											    xtype: 'tbfill'
											},	
                                              {
                                                  xtype: 'button',
                                                  text: '提交',
                                                  iconCls: 'common_icon_save',
                                                  listeners: {
                                                      click: 'onSubmitCurrencyClick'
                                                  }
                                              }
                                          ]
                                      }

                                  ]
                            }
                        ]
    	          }).show();
    	    
            },
            onAfterRender:function(){
            	var me=this;
            	var dealerid=me.getView().record.data.userid;
            	var store = me.getViewModel().getStore("currencytype");
            	var paras = { dealerid: dealerid };
     	        Ext.apply(store.proxy.extraParams, paras);
     	       store.load();
     	        
            },
            onSubmitCurrencyClick:function(btn){
            	var grid=this.lookupReference('currencyGrid');
            	var store=grid.getStore();
            	var currencyids=[];
            	var dealerid=this.getView().record.data.userid;
            	  for (var i = 0; i < store.getCount(); i++) {
            		  if(store.getAt(i).data.checked){
            			  currencyids.push(store.getAt(i).data.id);
            		  }
            		   
            	  }
          		var param = {dealerid:dealerid,currencyids:currencyids}; 
          		dealercurrencycontrol.SaveOrUpdate(param,function(result){
        	        	if(result.success){
        	        		 Ext.toast({
     		                    title: 'Tips', html: result.msg, align: 't',
     		                    width: 240,
     		                    bodyPadding: 10
     		                }); 
        	        		 btn.up('window').close();
        	        	}else{
           	        		 Ext.toast({
     		                    title: 'Tips', html: result.msg, align: 't',
     		                    width: 240,
     		                    bodyPadding: 10
     		                });
        	        	}
        	        });
            },
            //产品线管理
            onProdLineClick:function(btn){
    	    	var grid = this.lookupReference('DealerGrid');
    	    	var records = grid.getSelectionModel().getSelection();
   	         	if(records.length<=0){
	   	         	Ext.Msg.alert('提示', '请选择经销商');
	   	        	return;
   	         	}
   	         	var record=records[0];
    	        Ext.create("Ext.window.Window", {
    	        	 requires: [
								'BPSPortal.view.user.DealerAddViewModel',
								'BPSPortal.view.user.DealerAddViewController'
    	        	        ],
        	        viewModel: {
        	            type: 'dealeradd'
        	        },
        	        controller: 'dealeradd',
        	        record: record,
    	        	title:'产品线',
    	        	modal:true,
    	        	minHeight: 250,
    	            width: 400,
    	            items: [
                            {
                                xtype: 'gridpanel',
                                reference:'ProdLineGrid',
                                bind:{
                             	   store: '{prodlinetype}'
                                },
                                minHeight: 200,
                                listeners: {
                              	  afterrender: 'onAfterRenders'
                                },
                                columns: [

									{
									    xtype: 'checkcolumn',
									    header: '启用？',
									    dataIndex: 'checked',
									    width: 180,
									    stopSelection: false
									},
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'prodname',
                                        width:200,
                                        text: '产品线'
                                       
                                    }
                                ],
                                dockedItems: [
                                      {
                                          xtype: 'toolbar',
                                          dock: 'bottom',
                                          items: [
 											{
											    xtype: 'tbfill'
											},	
                                              {
                                                  xtype: 'button',
                                                  text: '提交',
                                                  iconCls: 'common_icon_save',
                                                  listeners: {
                                                      click: 'onSubmitProdLineClick'
                                                  }
                                              }
                                          ]
                                      }

                                  ]
                            }
                        ]
    	          }).show();
    	    
            },
            onAfterRenders:function(){
            	var me=this;
            	var dealerid=me.getView().record.data.userid;
            	var store = me.getViewModel().getStore("prodlinetype");
            	var paras = { dealerid: dealerid };
     	        Ext.apply(store.proxy.extraParams, paras);
     	       store.load();
     	        
            },
            onSubmitProdLineClick:function(btn){
            	
            	
            	var grid=this.lookupReference('ProdLineGrid');
            	var store=grid.getStore();
            	
            	if(store.getCount()<1){
                 	Ext.Msg.alert('提示', '没有操作数据');
                 	return;
                 }
            	
            	var prodids=[];
            	var dealerid=this.getView().record.data.userid;
            	  for (var i = 0; i < store.getCount(); i++) {
            		  if(store.getAt(i).data.checked){
            			  prodids.push(store.getAt(i).data.prodid);
            		  }
            		   
            	  }
          		var param = {dealerid:dealerid,prodids:prodids}; 
          		dealerprodlinecontrol.SaveOrUpdate(param,function(result){
        	        	if(result.success){
        	        		 Ext.toast({
     		                    title: 'Tips', html: result.msg, align: 't',
     		                    width: 240,
     		                    bodyPadding: 10
     		                }); 
        	        		 btn.up('window').close();
        	        	}else{
           	        		 Ext.toast({
     		                    title: 'Tips', html: result.msg, align: 't',
     		                    width: 240,
     		                    bodyPadding: 10
     		                });
        	        	}
        	        });
            },
            onRegNumClick:function(){
    	    	var grid = this.lookupReference('DealerGrid');
    	    	var records = grid.getSelectionModel().getSelection();
   	         	if(records.length<=0){
	   	         	Ext.Msg.alert('提示', '请选择经销商');
	   	        	return;
   	         	}
   	         	var record=records[0];
    	        Ext.create("Ext.window.Window", {
    	        	 requires: [
								'BPSPortal.view.user.DealerAddViewModel',
								'BPSPortal.view.user.DealerAddViewController'
    	        	        ],
        	        viewModel: {
        	            type: 'dealeradd'
        	        },
        	        controller: 'dealeradd',
        	        record: record,
    	        	title:'报备数',
    	        	modal:true,
    	        	minHeight: 250,
    	            width: 450,
    	            items: [
                            {
                                xtype: 'gridpanel',
                                reference:'DealerRegGrid',
                                bind:{
                             	   store: '{dealerregnum}'
                                },
                                minHeight: 200,
                                listeners: {
                              	  afterrender: 'onAfterRegRender'
                                },
                                plugins: [
                                          {
                                              ptype: 'cellediting',
                                              clicksToEdit:1
                                          }
                                        ],
                                columns: [
                                    {
                                        xtype: 'gridcolumn',
                                        dataIndex: 'prodname',
                                        width:200,
                                        text: '产品线'
                                    },
                                    {
                                        xtype: 'numbercolumn',
                                        dataIndex: 'regmax',
                                        width:200,
                                        format:'0,000', 
                                        align:'right',
                                        text: '最大报备数',
                                        editor:{
                                        	xtype : 'numberfield',
                    						minValue:0,
                    						name:'regmax'
                                        }
                                       
                                    }
                                ],
                                dockedItems: [
                                      {
                                          xtype: 'toolbar',
                                          dock: 'bottom',
                                          items: [
 											{
											    xtype: 'tbfill'
											},	
                                              {
                                                  xtype: 'button',
                                                  text: '提交',
                                                  iconCls: 'common_icon_save',
                                                  listeners: {
                                                      click: 'onSubmitDealerRegNumClick'
                                                  }
                                              }
                                          ]
                                      }

                                  ]
                            }
                        ]
    	          }).show();
    	    
            },
            onAfterRegRender:function(){
            	var me=this;
            	var dealerid=me.getView().record.data.userid;
            	var store = me.getViewModel().getStore("dealerregnum");
            	var paras = { dealerid: dealerid };
     	        Ext.apply(store.proxy.extraParams, paras);
     	       store.load();
     	        
            },
            onSubmitDealerRegNumClick:function(btn){
            	var grid=this.lookupReference('DealerRegGrid');
            	var store=grid.getStore();
            	
            	
            	var DealerReg=[];
            	var dealerid=this.getView().record.data.userid;
            	
            	  for (var i = 0; i < store.getCount(); i++) {
            		  var records = Ext.create('BPSPortal.model.DealerRegNum');
            		  
            		  if((store.getAt(i).data.regmax==null||store.getAt(i).data.regmax=='')&&store.getAt(i).data.regmax!=0){
          	    		Ext.Msg.alert('提示', '报备数不能为空');
          	        	return;
          	    	 }
        			  records.data.dealerid=store.getAt(i).data.dealerid;
        			  records.data.prodid=store.getAt(i).data.prodid;
        			  records.data.regmax=store.getAt(i).data.regmax;
        			  DealerReg.push(records.data);
            	  }
          		var param = {DealerReg:DealerReg}; 
          		dealerprodlinecontrol.SaveOrUpdateRegNum(param,function(result){
        	        	if(result.success){
        	        		 Ext.toast({
     		                    title: 'Tips', html: result.msg, align: 't',
     		                    width: 240,
     		                    bodyPadding: 10
     		                }); 
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
