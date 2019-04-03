
Ext.define('BPSPortal.view.customer.reportapprove.ReportApproveViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.reportapprove',
    onAfterRenderW:function(){
	    	var me=this;
	    	var form = me.getView().getForm();
	    	me.getView().down('textfield[name="name"]').focus(true, 100)
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
        var roleGrid = Ext.ComponentQuery.query("viewport reportapprove grid")[0];
        
        var name = btn.up('form').down('textfield[name=name]').getValue();
        var prodid = btn.up('form').down('combobox[name=prodid]').getValue();
        var approveresult = btn.up('form').down('combobox[name=approveresult]').getValue();
        var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
        
        var startdate = btn.up('form').down('datefield[name=startdate]').getValue();
        var enddate = btn.up('form').down('datefield[name=enddate]').getValue();
        
        var roleStore = roleGrid.getStore(); 
        /*传name到后台查询*/
        var extraParams = {name:name,prodid:prodid,approveresult:approveresult,dealername:dealername,startdate:startdate,enddate:enddate,regstatus:null};
        roleStore.on("beforeload", function (roleStore, operation, eOpts) {
					 Ext.apply(roleStore.proxy.extraParams, extraParams);
					});
		roleStore.load();			
    },
    
    	onCustRegLoad:function(store,records,successful,eOpts){
    		for (var i = 0; i < store.getCount(); i++) {
    		     var rec = 	store.getAt(i);
    		     var id = rec.get('id');
    		     var cname = rec.get('custname');
    		     var texno = rec.get('taxno');
    		     var regstatus = rec.get('regstatus');
    		     var ict_name = 0;
    		     var ict_text = 0;
    		     if(rec.get('dealername')==null&&rec.get('isbps')=='1'&&rec.get('regstatus')!=2){
    		    	 rec.set('dealername','上海晶丰');
    		     }
    		     
    		     for(var j =0;j<store.getCount();j++){ 
    		    	 var inner_rec = store.getAt(j);
    		    	 
    		    	 var inner_id = inner_rec.get('id');
        		     var inner_cname = inner_rec.get('custname');
        		     var inner_regstatus=inner_rec.get('regstatus');
        		     var inner_texno = inner_rec.get('taxno');
        		     var inner_prodname= inner_rec.get('prodid');
        		     if(id != inner_id){ // 排除自己
        		    	 if(regstatus=='0'){
        		    		 
        		    		 if(cname != null && cname == inner_cname)  ict_name ++;
            		    	 if(inner_texno!=''){//排除税号是空
                		    	 if(texno != null && texno == inner_texno)  ict_text ++;
            		    	 }
        		    	 }
        		    	 
        		    	
        		    	 
        		     }
    		     }
    		     rec.set('ict_name',ict_name);
    		     rec.set('ict_text',ict_text);
    		     
	        }
    		
    		for (var i = 0; i < store.getCount(); i++) {
    			var rec = 	store.getAt(i);	
    			 var ict_name = rec.get('ict_name');
    		     var ict_text = rec.get('ict_text');
    		     
    		     var i_msg = "";
    		     
    		     if(ict_name>0){
    		    	 i_msg ='名称冲突';
    		     }
    		     if(ict_text>0) {
    		    	 i_msg ='税号冲突';
    		     }
    		     if(ict_name>0&&ict_text>0){
    		    	 i_msg ='名称冲突、税号冲突';
    		     }
    		     rec.set('ct',i_msg);
    			
    		}	
    		store.commitChanges();
    	},
    	//待处理
        onUpdateClick:function(button){
        	var grid = Ext.ComponentQuery.query("viewport reportapprove grid")[0];
        	var store=grid.getStore();
    		if(grid.getSelectionModel().getSelection().length<=0){
    			Ext.Msg.alert('提示', '请选择数据');
    			return;
    		}
    		
    		var	records =grid.getSelectionModel().getSelection();
    		var id=[];
    		for(var j =0;j<records.length;j++){
    			if(records[j].data.approveresult==1){
            		Ext.Msg.alert('提示', '数据已是待处理状态');
        	    	return;
        	    }
    			if(records[j].data.approveresult==2||records[j].data.approveresult==3){
            		Ext.Msg.alert('提示', '请选择未处理状态的客户进行操作');
        	    	return;
        	    }
    			id.push(records[j].data.id);
    		}
    		
        	//var id=records.data.id;
        	var param = {id:id}; 
    		custRegController.updateCustre(param,function(result){
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
        },
        //onThroughClick 审批通过
        	onThroughClick:function(button){
        		var grid = this.lookupReference('custregGrid');
        		
        	    if (grid.getSelectionModel().getSelection().length <= 0) {
        	    	Ext.Msg.alert('提示', '请选择要审批的数据');
        	    	return;
        	    }
        	    var reg=[];
        	    var cpid=[];
        	    var custid=[];
        	    var custname=[];
        	    var prodid=[];
        	    var records = grid.getSelectionModel().getSelection();
        	    for(var j =0;j<records.length;j++){
        	    	if(records[j].data.approveresult==3||records[j].data.approveresult==2){
        	    		Ext.Msg.alert('提示', '请选择待处理和未处理状态的客户进行操作');
            	    	return;
            	    }
        	    	reg.push(records[j].data.id);
        	    	cpid.push(records[j].data.cpid);
        	    	custid.push(records[j].data.custid);
        	    	custname.push(records[j].data.custname);
        	    	prodid.push(records[j].data.prodid);
        		}
        		var store = grid.getStore();
        		var content='确定执行此操作？';
                Ext.Msg.confirm("提示", content, function (btn) {
                    if (btn == "yes") {
                    	var param = {reg:reg,cpid:cpid,custid:custid,custname:custname,prodid :prodid}; 
                		custRegController.updateCustreg(param,function(result){

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
        	//onRejectClick 驳回
        	onRejectClick:function(button){
        		var grid = this.lookupReference('custregGrid');
        		
        	    if (grid.getSelectionModel().getSelection().length <= 0) {
        	    	Ext.Msg.alert('提示', '请选择要审批的数据');
        	    	return;
        	    }
        		
        	    var records = grid.getSelectionModel().getSelection();
        	    var reg=[];
        	    var cpid=[];
        	    for(var j =0;j<records.length;j++){
        	    	if(records[j].data.approveresult==3||records[j].data.approveresult==2){
        	    		Ext.Msg.alert('提示', '请选择待处理和未处理状态的客户进行操作');
            	    	return;
            	    }
        	    	reg.push(records[j].data.id);
        	    	cpid.push(records[j].data.cpid);
        		}
        		var store = grid.getStore();
        		var content='确定执行此操作？';
                Ext.Msg.confirm("提示", content, function (btn) {
                    if (btn == "yes") {
                    	var window = new Ext.Window({  
                            title : '请填写审批意见',  
                            closable : true,  
                            modal:true,
                            border : false,  
                            width : 500,  
                            height : 180,
                            items:[
									{
									    xtype: 'textareafield',
									    padding: '10 0 0 0',
									    width: 450,
									    fieldLabel: '审批意见',
									    name:'remark'
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
											text: '确定',
											listeners: {
												click:function(button){	
													var remark=button.up("window").down('textareafield[name=remark]').getValue();
													if(remark==''||remark==null){
														Ext.Msg.alert('提示', '请填写审批意见!');
									        	    	return;
													}
													button.up("window").close();
													
													
													
													var param = {reg:reg,cpid:cpid,remark:remark}; 
							                		custRegController.updateCustregStatus(param,function(result){
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
											}
										}
    			                      ]
    			                  }
    			              ]
                        }).show(); 
                    }
                });
        	
        	},
        	
        	//更新状态
        	onUpdateStatusClick:function(button){
        		var grid = Ext.ComponentQuery.query("viewport reportapprove grid")[0];
    			if(grid.getSelectionModel().getSelection().length<=0){
    				Ext.Msg.alert('提示', '请选择数据');
    				return;
    			}
    			var	records =grid.getSelectionModel().getSelection()[0];
    			records.phantom = true;
     	        var vmData = {};
     	        vmData.data = records;
     	        vmData.id = records.data.id;

     	        this.fireViewEvent('viewObject', this.getView(), 'updatestate', vmData);
     	        
        	},
        	//通过
        	onThroughsClick:function(button){
        		var me=this;
        		 var id = button.up('form').down('textfield[name=id]').getValue();
        		 var cpids = button.up('form').down('textfield[name=cpid]').getValue();
        		 var custids = button.up('form').down('textfield[name=custid]').getValue();
        	     var reg=[];
        	     var cpid=[];
        	     var custid=[];
        	    	reg.push(id);
        	    	cpid.push(cpids);
        	    	custid.push(custids);
        		var param = {reg:reg,cpid:cpid,custid:custid}; 
        		custRegController.updateCustreg(param,function(result){
    	        	if(result.success){
    	        		 Ext.toast({
 		                    title: 'Tips', html: result.msg, align: 't',
 		                    width: 240,
 		                    bodyPadding: 10
 		                }); 
    	        		 me.getView().close();
    	        		 
    	        	}else{
      	        		 Ext.toast({
  		                    title: 'Tips', html: result.msg, align: 't',
  		                    width: 240,
  		                    bodyPadding: 10
  		                });
     	        	}
    	        });
        	},
        	
        	//驳回
        	onRejectsClick:function(button){
        		 var id = button.up('form').down('textfield[name=id]').getValue();
        	     var reg=[];
        	    	 reg.push(id);
        		var param = {reg:reg};  
        		custRegController.updateCustregStatus(param,function(result){
    	        	if(result.success){
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
     	        	}
    	        });
        	},
        	
          // 冲突查询
            	
            	onConfilctQueryClick:function(button){
            		var record = button.getWidgetRecord();
            		var ct =record.data.ct;
            		var custname=record.data.custname;
            		var taxno=record.data.taxno;
            		var ids=record.data.id;
            		if(ct =='名称冲突'){
            	         Ext.create("BPSPortal.view.customer.customerupdate.UpdateStatusWin", {
            	        	 custname: custname,
            	        	 ids:ids
            	          }).show();
            			
            		}else if (ct =='税号冲突'){
            			Ext.create("BPSPortal.view.customer.customerupdate.UpdateStatusWin", {
            	        	 taxno: taxno,
            	        	 ids:ids
            	          }).show();    
            		}else if(ct=='名称冲突、税号冲突'){
            	         Ext.create("BPSPortal.view.customer.customerupdate.UpdateStatusWin", {
            	        	 taxno: taxno,
            	        	 custname: custname,
            	        	 ids:ids
            	          }).show();
            		}else{
            			Ext.Msg.alert('提示', '没有冲突');
            	    	return;
            			
            		}
            	},
            	
            	
            	
            	onAfterRender:function(){
                	if(this.getViewModel().get("data")==null){
                		return;
                	}
                	var data = this.getViewModel().get("data").data;
                	var custid=data.custid;
                	var cpid=data.cpid
                	var creator=data.creator;
                	if(data.regstatus==1){
                		creator=null;
        	        }
                	
                	var dealerid=data.dealerid;
                	 	var store1 = this.getViewModel().getStore("address");
            	        var store2 = this.getViewModel().getStore("contact");
            	        var store3 = this.getViewModel().getStore("project");
            	        var store4 = this.getViewModel().getStore("custBPS");
            	        var store5 = this.getViewModel().getStore("dealer");
            	        if (cpid == "-1" || cpid == "" || cpid == undefined) {
            	        	store1.removeAll();
            	        	store2.removeAll();
            	        	store3.removeAll();
            	        	store4.removeAll();
            	            return;
            	        }
            	        
            	        var paras = { cpid: cpid,creator:creator};
            	        Ext.apply(store1.proxy.extraParams, paras);
            	        store1.load({
                            callback: function () {
                                //加载完后 将 store的参数清空
                            	store1.proxy.extraParams = {};
                            }
                        });
            	        
            	        var paras = { cpid: cpid,creator :creator };
            	        Ext.apply(store2.proxy.extraParams, paras);
            	        store2.load({
                            callback: function () {
                                //加载完后 将 store的参数清空
                            	store2.proxy.extraParams = {};
                            }
                        });
            	        
            	        var paras = { cpid: cpid ,creator:creator};
            	        Ext.apply(store3.proxy.extraParams, paras);
            	        store3.load({
                            callback: function () {
                                //加载完后 将 store的参数清空
                            	store3.proxy.extraParams = {};
                            }
                        });
            	        
            	        var paras = { cpid: cpid };
            	        Ext.apply(store4.proxy.extraParams, paras);
            	        store4.load({
                            callback: function () {
                                //加载完后 将 store的参数清空
                            	store4.proxy.extraParams = {};
                            }
                        });

            	        if (dealerid == "-1" || dealerid == "" || dealerid == undefined) {
            	        	store5.removeAll();
        	        		return;
        	           }
            	        
            	        var paras = { cpid: cpid,dealerid:dealerid };
            	        Ext.apply(store5.proxy.extraParams, paras);
            	        store5.load({
                            callback: function () {
                                //加载完后 将 store的参数清空
                            	store5.proxy.extraParams = {};
                            }
                        });
            	        
            	      
                },
                //添加地址
                onAddAddressClick:function(button){
                //	alert(1);
                    var grid = this.lookupReference('addressGrid');
                    var store=grid.getStore();
                    var name = button.up('form').down('combobox[name=prodid]').getRawValue();
                	if(name==''||name==null){
                		Ext.Msg.alert('提示', '请选择产品线');
                    	return;
                	}
                    var count = (store.getCount() > 0) ? store.getCount() : 0; 
                    if(count==0){
                    	var record = new BPSPortal.model.Address({isdefault:true,prodname:name});
                    }else{
                    	var record = new BPSPortal.model.Address({prodname:name});
                    }
                    store.insert(count, record); 
                    grid.getSelectionModel().select(record);
                },
                 	//删除地址
                   onDelAddressClick:function(button){
                   		var grid = this.lookupReference('addressGrid');
                   		var record = button.getWidgetRecord();
                   		var store = grid.getStore();
                   		var id=record.data.id;
                   		//return;
                   		var content='是否删除此数据？';
               	        Ext.Msg.confirm("删除", content, function (btn) {
               	            if (btn == "yes") {
               	            	if(id=='-1'){
               	            		store.remove(record);
               	            	}else{
               	            	var param = {id:id}; 
               	            	custAddressControl.deleteAdd(param,function(result){
               	        	        	if(result.success){
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
               	        	        	}
               	        	        });
               	                store.remove(record);
               	            }
               	               
               	            }
               	        });
                   	},
                   	//保存数据
                   	onSaveAddressClick:function(button){
                   		var grid = this.lookupReference('addressGrid');
                   		var store = grid.getStore();
                   		var records = button.getWidgetRecord();
                   		var cpid = button.up('form').down('textfield[name=cpid]').getValue();
                   
               		    var record = Ext.create('BPSPortal.model.Address');
	               		 if(records.data.addtype==''||records.data.addtype==null){
	 	        	 		Ext.Msg.alert('提示', '地址类型不能为空');
	 	        	 		return;
	 	        	 	 }
	               		if(records.data.province==''||records.data.province==null){
	            	 		Ext.Msg.alert('提示', '省不能为空');
	            	 		return;
	            	 	 }
	            	 	if(records.data.city==''||records.data.city==null){
	            	 		Ext.Msg.alert('提示', '市不能为空');
	            	 		return;
	            	 	 }
	 	        	 	 if(records.data.address==''||records.data.address==null){
	 	        	 		Ext.Msg.alert('提示', '详细地址不能为空');
	 	        	 		return;
	 	        	 	 }
               		    	record.data.id = records.data.id;
               			    record.data.cpid = cpid;
               			    record.data.addtype =records.data.addtype;
               			    record.data.province =records.data.province;
               			    record.data.city = records.data.city;
               			    record.data.address =records.data.address;
               			    if(records.data.isdefault==true){
               			    	record.data.isdefault = 1;
               			    }else{
               			    	record.data.isdefault = 0;
               			    }
               			    
               			    record.data.remark = records.data.remark;
               			    record.data.active =records.data.active;
               			    record.data.creator = records.data.creator;
               			    record.data.createdate =records.data.createdate;
               			    record.data.updator =records.data.updator;
               			    record.data.updatedate =records.data.updatedate;   
               			    var param = {record:record.data,cpid:cpid}; 
               			    custAddressControl.updateOrsave(param,function(result){
                       	        	if(result.success){
                       	        		 Ext.toast({
                    		                    title: 'Tips', html: result.msg, align: 't',
                    		                    width: 240,
                    		                    bodyPadding: 10
                    		                }); 
                       	        		store.commitChanges();
                       	        	}else{
	                   	        		 Ext.toast({
		             		                    title: 'Tips', html: result.msg, align: 't',
		             		                    width: 240,
		             		                    bodyPadding: 10
		             		                });
	                    	        	}
                       	        });
                              
                   	},
                       //添加联系人
                   	onAddContactClick:function(button){
                   		var name = button.up('form').down('combobox[name=prodid]').getRawValue();
                    	if(name==''||name==null){
                    		Ext.Msg.alert('提示', '请选择产品线');
                        	return;
                    	}
                           var grid = this.lookupReference('contactGrid');
                           var store=grid.getStore();
                           var record = new BPSPortal.model.ContactInfo({prodname:name});
                           store.insert(0, record);       
                           grid.getSelectionModel().select(record);
                       },
                       	//删除联系人
                       onDelContactClick:function(button){
                       	var grid = this.lookupReference('contactGrid');
                   		var record = button.getWidgetRecord();
                   		var store = grid.getStore();
                   		var id=record.data.id;
                   		//return;
                   		var content='是否删除此数据？';
                           Ext.Msg.confirm("删除", content, function (btn) {
                               if (btn == "yes") {
                               	if(id=='-1'){

                               		 store.remove(record);
                               	}else{

                               		var param = {id:id}; 
                               		contactControl.deleteAdd(param,function(result){
                               	        	if(result.success){
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
                               	        	}
                               	        });
                                   	 store.remove(record);
                               	}
                               	
                                  
                                  
                               }
                           });
                       },
                       //保存联系人
                       onSaveContactClick:function(button){
                   		var grid = this.lookupReference('contactGrid');
                   		var store = grid.getStore();
                   		var records = button.getWidgetRecord();
                   
                   		var cpid = button.up('form').down('textfield[name=cpid]').getValue();
               		    var record = Ext.create('BPSPortal.model.ContactInfo');
		               		 if(records.data.title==''||records.data.title==null){
		 	     	        	Ext.Msg.alert('提示', '联系人职位不能为空');
		 	 	            	return;
		 	     	        }
		 	 	        	if(records.data.name==''||records.data.name==null){
		 	     	        	Ext.Msg.alert('提示', '联系人姓名不能为空');
		 	 	            	return;
		 	     	        }
		 	 	        	if((records.data.mobile==''||records.data.mobile==null) && (records.data.telno ==''||records.data.telno==null)){
		 	     	        	Ext.Msg.alert('提示', '联系人手机和电话至少填一个');
		 	 	            	return;
		 	     	        }

		 		        	if(records.data.mobile!=''&&records.data.mobile!=null){
		 		        		if(store2.getAt(i).data.mobile.length!=11){
		 	    	 	        	Ext.Msg.alert('提示', '联系人手机必须是十一位');
		 	    	            	return;
		 	    	 	        }
		 		        	}

               		    	record.data.id = records.data.id;
               			    record.data.cpid = cpid;
               			    record.data.title =records.data.title;
               			    record.data.name =records.data.name;
               			    record.data.mobile = records.data.mobile;
               			    record.data.telno =records.data.telno;
               			    record.data.email = records.data.email;
               			    record.data.im = records.data.im;
               			    record.data.remark = records.data.remark;
               			    record.data.active =records.data.active;
               			    record.data.creator = records.data.creator;
               			    record.data.createdate =records.data.createdate;
               			    record.data.updator =records.data.updator;
               			    record.data.updatedate =records.data.updatedate;   
               			    var param = {record:record.data,cpid:cpid}; 
               			    contactControl.updateOrsave(param,function(result){
                       	        	if(result.success){
                       	        		 Ext.toast({
                    		                    title: 'Tips', html: result.msg, align: 't',
                    		                    width: 240,
                    		                    bodyPadding: 10
                    		                }); 
                       	        		store.commitChanges();
                       	        	}else{
	                   	        		 Ext.toast({
		             		                    title: 'Tips', html: result.msg, align: 't',
		             		                    width: 240,
		             		                    bodyPadding: 10
		             		                });
	                    	        	}
                       	        });
                               
                   	},
                           //添加项目
                   	onAddProjectClick:function(button){
                   		var name = button.up('form').down('combobox[name=prodid]').getRawValue();
                    	if(name==''||name==null){
                    		Ext.Msg.alert('提示', '请选择产品线');
                        	return;
                    	}
                               var grid = this.lookupReference('projectGrid');
                               var store=grid.getStore();
                               var record = new BPSPortal.model.ProjectInfo({prodname:name});
                               store.insert(0, record);       
                               grid.getSelectionModel().select(record);
                           },
                           	//删除项目
                           onDelProjectClick:function(button){
                           	var grid = this.lookupReference('projectGrid');
                       		var record = button.getWidgetRecord();
                       		var store = grid.getStore();
                       		var id=record.data.id;
                       		//return;
                       		var content='是否删除此数据？';
                               Ext.Msg.confirm("删除", content, function (btn) {
                                   if (btn == "yes") {
                                   	if(id=='-1'){

                                   		 store.remove(record);
                                   	}else{

                                   		var param = {id:id}; 
                                   		custprojectinfoContro.deleteAdd(param,function(result){
                                   	        	if(result.success){
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
                                   	        	}
                                   	        });
                                       	 store.remove(record);
                                   	}
                                   	
                                      
                                      
                                   }
                               });
                           	},
                           	//保存项目
                           	onSaveProjectClick:function(button){
                           		var grid = this.lookupReference('projectGrid');
                           		var store = grid.getStore();
                           		var records = button.getWidgetRecord();
                           		var cpid = button.up('form').down('textfield[name=cpid]').getValue();
                           
                       		    var record = Ext.create('BPSPortal.model.ProjectInfo');
                       		    	record.data.id = records.data.id;
                       			    record.data.cpid = cpid;
                       			    record.data.materialid =records.data.materialid;
                       			    record.data.projtype =records.data.projtype;
                       			    record.data.projname = records.data.projname;
                       			    record.data.compname =records.data.compname;
                       			    record.data.compprod = records.data.compprod;
                       			    record.data.shipvol = records.data.shipvol;
                       			    record.data.massproddate = records.data.massproddate;
                       			    record.data.remark = records.data.remark;
                       			    record.data.active =records.data.active;
                       			    record.data.creator = records.data.creator;
                       			    record.data.createdate =records.data.createdate;
                       			    record.data.updator =records.data.updator;
                       			    record.data.updatedate =records.data.updatedate;   
                       			    var param = {record:record.data,cpid:cpid}; 
                       			     custprojectinfoContro.updateOrsave(param,function(result){
                               	        	if(result.success){
                               	        		 Ext.toast({
                            		                    title: 'Tips', html: result.msg, align: 't',
                            		                    width: 240,
                            		                    bodyPadding: 10
                            		                }); 
                               	        		store.commitChanges();
                               	        	}else{
	       	                   	        		 Ext.toast({
	     	             		                    title: 'Tips', html: result.msg, align: 't',
	     	             		                    width: 240,
	     	             		                    bodyPadding: 10
	     	             		                });
                               	        	}
                               	        });
                                       
                           	},
                               //添加客户管理
                           	onAddCustBPSClick:function(button){
                           		var name = button.up('form').down('combobox[name=prodid]').getRawValue();
                            	if(name==''||name==null){
                            		Ext.Msg.alert('提示', '请选择产品线');
                                	return;
                            	}
                                   var grid = this.lookupReference('custBPSGrid');
                                   var store=grid.getStore();
                                   var record = new BPSPortal.model.CustBPSInfo({prodname:name});
                                   store.insert(0, record);       
                                   grid.getSelectionModel().select(record);
                               },
                               	//删除客户管理
                               onDelCustBPSClick:function(button){
                               	var grid = this.lookupReference('custBPSGrid');
                           		var record = button.getWidgetRecord();
                           		var store = grid.getStore();
                      
                           		var id=record.data.id;
                           		//return;
                           		var content='是否删除此数据？';
                                   Ext.Msg.confirm("删除", content, function (btn) {
                                       if (btn == "yes") {
                                       	if(id<0){

                                       		 store.remove(record);
                                       	}else{
                                       		var param = {id:id}; 
                                       		custbpscontrol.deleteAdd(param,function(result){
                                       	    
                                       	        	if(result.success){
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
                                       	        	}
                                       	        });
                                           	 store.remove(record);
                                       	}
                                       	
                                          
                                          
                                       }
                                   });
                               },
                               //保存客户信息
                               onSaveCustBPSClick:function(button){
                           		var grid = this.lookupReference('custBPSGrid');
                           		var store = grid.getStore();
                           		var records = button.getWidgetRecord();

                           		var cpid = button.up('form').down('textfield[name=cpid]').getValue();
                           		
                       		    var record = Ext.create('BPSPortal.model.CustBPSInfo');
                       		    	record.data.id = records.data.id;
                       			    record.data.cpid = cpid;
                       			    record.data.isshare =records.data.isshare;
                       			    
                       			 if(records.data.isshare=='是' || records.data.isshare=='1'){
         	     			    	record.data.isshare=1;
                       			 }else{
         	     			    	record.data.isshare=0;
                       			 }
                       			    record.data.custtype =records.data.custtype;
                       			    record.data.custcategory1 = records.data.custcategory1;
                       			    record.data.custcategory2 =records.data.custcategory2;
                       			    record.data.custcategory3 = records.data.custcategory3;
                       			    record.data.dealersales = records.data.dealersales;
                       			    record.data.bpssales = records.data.bpssales;
                       			    record.data.bpsfae = records.data.bpsfae;
                       			    record.data.active =records.data.active;
                       			    record.data.creator = records.data.creator;
                       			    record.data.createdate =records.data.createdate;
                       			    record.data.updator =records.data.updator;
                       			    record.data.updatedate =records.data.updatedate;   
                       			    var param = {record:record.data,cpid:cpid}; 
                       			    custbpscontrol.updateOrsave(param,function(result){
              
                               	        	if(result.success){
                               	        		 Ext.toast({
                            		                    title: 'Tips', html: result.msg, align: 't',
                            		                    width: 240,
                            		                    bodyPadding: 10
                            		                }); 
                               	        		store.commitChanges();
                               	        	}else{
	       	                   	        		 Ext.toast({
	     	             		                    title: 'Tips', html: result.msg, align: 't',
	     	             		                    width: 240,
	     	             		                    bodyPadding: 10
	     	             		                });
                               	        	}
                               	        });
                                      
                           	},
          // 明细查询
            	onDetailClick:function(button){
            		var grid = Ext.ComponentQuery.query("viewport reportapprove grid")[0];
        			if(grid.getSelectionModel().getSelection().length<=0){
        				Ext.Msg.alert('提示', '请选择数据');
        				return;
        			}
        			var	records =grid.getSelectionModel().getSelection()[0];
          	        records.phantom = true;
         	        var vmData = {};
         	        vmData.data = records;
         	        vmData.id = records.data.id;

         	        this.fireViewEvent('viewObject', this.getView(), 'reportdetail', vmData);
            	},
            	onResertClick:function(button){
            		 button.up("form").close();
            	},
            	onAddressLoad:function(store,records,successful,eOpts){
        	   		if(store.getCount()<0){
        	   			return;
        	   		}
        		for (var i = 0; i < store.getCount(); i++) {
        		     var rec = 	store.getAt(i);
        		     var isdefault = rec.get('isdefault');
        		     if(isdefault==1){
        		    	 rec.set('isdefault',true);
        		    }else{
        		    	 rec.set('isdefault',false);
        		    }
                }
        	},
        	 onValueChange:function(field, newValue, oldValue, eOpts){
           	  var city = this.getViewModel().getStore("city");
                 if (oldValue != null) {
               	  var grid=this.lookupReference('addressGrid');
               	  city.removeAll();
                 }
                 var paras = { name: newValue };
                 Ext.apply(city.proxy.extraParams, paras);
                 city.load()

             },
             onsaveClick:function(button){
            	 var me=this;
        		 var region = button.up('form').down('textfield[name=region]').getValue();
        		 var custname = button.up('form').down('textfield[name=custname]').getValue();
        		 var custcode = button.up('form').down('textfield[name=custcode]').getValue();
        		 var taxno = button.up('form').down('textfield[name=taxno]').getValue();
        		 var parenetcompany = button.up('form').down('customerpickers[name=parenetcompany]').getValue();
        		 var custid = button.up('form').down('textfield[name=custid]').getValue();
        		 var cpid = button.up('form').down('textfield[name=cpid]').getValue();
        		 var prodid = button.up('form').down('combobox[name=prodid]').getValue();
        		 var customer = Ext.create('BPSPortal.model.Customer');
        		
        		var parenetcompanys = button.up('form').down('customerpickers[name=parenetcompany]').getValue();//id
     		    var parenetcompanyss = button.up('form').down('customerpickers[name=parenetcompany]').getRawValue();//value
     		    if(parenetcompanyss==''||parenetcompanyss==undefined||parenetcompanyss==null){
     		    	customer.data.isparent = 1
     		    	customer.data.parenetcompany=null;
     		    }else{
     		    	if(parenetcompanys.length!=36&&(parenetcompanyss!=null||parenetcompanyss!='')){
           	    	Ext.Msg.alert('提示', '母公司请选择存在下拉列表中存在的数据');
                   	return;
           	    }else{
           	    	customer.data.isparent = 0;
       		    	customer.data.parenetcompany=parenetcompanys;
           	    }
     		    	
     		    }
        		 customer.data.region=region;
        		 customer.data.taxno=taxno;
        		 customer.data.id=custid;
        		 customer.data.custname=custname; 
        		 customer.data.custcode=custcode;
        		 
        		 var address = [];
        		    var store1 =this.getViewModel().getStore('address');
        		    if(store1.getCount()<1){
        	        	Ext.Msg.alert('提示', '请添加至少一条地址信息');
        	        	return;
        	        }
        	        for (var i = 0; i < store1.getCount(); i++) {
        	        	 var record = Ext.create('BPSPortal.model.Address');
        	        	 	if(store1.getAt(i).data.addtype==''||store1.getAt(i).data.addtype==null){
        	        	 		Ext.Msg.alert('提示', '地址类型不能为空');
        	        	 		return;
        	        	 	}
        	        	 	if(store1.getAt(i).data.province==''||store1.getAt(i).data.province==null){
        	        	 		Ext.Msg.alert('提示', '省不能为空');
        	        	 		return;
        	        	 	 }
        	        	 	if(store1.getAt(i).data.city==''||store1.getAt(i).data.city==null){
        	        	 		Ext.Msg.alert('提示', '市不能为空');
        	        	 		return;
        	        	 	 }
        	        	 	if(store1.getAt(i).data.address==''||store1.getAt(i).data.address==null){
        	        	 		Ext.Msg.alert('提示', '详细地址不能为空');
        	        	 		return;
        	        	 	}
        	        	 	
        			    	record.data.id =store1.getAt(i).data.id; 
        			    	record.data.cpid =store1.getAt(i).data.cpid; 
        			    	record.data.addtype=store1.getAt(i).data.addtype; 
        			    	record.data.province =store1.getAt(i).data.province; 
        			    	record.data.city =store1.getAt(i).data.city; 
        			    	record.data.address =store1.getAt(i).data.address; 
        			    	record.data.remark =store1.getAt(i).data.remark; 
        			 
        			    	if(store1.getAt(i).data.isdefault==true){
        			    		record.data.isdefault =1; 
        			    	}else{
        			    		record.data.isdefault =0 ;
        			    	}
        	        	address.push(record.data);
        	        }
        		    var contact = [];
        		    var store2 =this.getViewModel().getStore('contact');
        		    if(store2.getCount()<1){
        	        	Ext.Msg.alert('提示', '请添加至少一条联系人信息');
        	        	return;
        	        }
        	        for (var i = 0; i < store2.getCount(); i++) {
        	        	var record = Ext.create('BPSPortal.model.ContactInfo');
        	          	if(store2.getAt(i).data.title==''||store2.getAt(i).data.title==null){
        	 	        	Ext.Msg.alert('提示', '联系人职位不能为空');
        	            	return;
        	 	        }
        	        	if(store2.getAt(i).data.name==''||store2.getAt(i).data.name==null){
        	 	        	Ext.Msg.alert('提示', '联系人姓名不能为空');
        	            	return;
        	 	        }
        	        	
        	        	if(store2.getAt(i).data.mobile==''&&store2.getAt(i).data.telno==''){
        	 	        	Ext.Msg.alert('提示', '联系人手机和电话至少填一个');
        	            	return;
        	 	        }
        	        	if(store2.getAt(i).data.mobile!=''&&store2.getAt(i).data.mobile!=null){
        	        		if(store2.getAt(i).data.mobile.length!=11){
            	 	        	Ext.Msg.alert('提示', '联系人手机必须是十一位');
            	            	return;
            	 	        }
        	        	}
        		    	record.data.id =store2.getAt(i).data.id; 
        		    	record.data.cpid =store2.getAt(i).data.cpid; 
        		    	record.data.title=store2.getAt(i).data.title; 
        		    	record.data.name =store2.getAt(i).data.name; 
        		    	record.data.mobile =store2.getAt(i).data.mobile; 
        		    	record.data.telno =store2.getAt(i).data.telno; 
        		    	record.data.email =store2.getAt(i).data.email; 
        		    	record.data.im =store2.getAt(i).data.im; 
        		    	record.data.remark =store2.getAt(i).data.remark; 
        		    	contact.push(record.data);
        	        }
        	        var project = [];
        		    var store3 =this.getViewModel().getStore('project');
        	        for (var i = 0; i < store3.getCount(); i++) {
        	  
        	        	var record = Ext.create('BPSPortal.model.ProjectInfo');
        		    	record.data.id =store3.getAt(i).data.id; 
        		    	record.data.projtype =store3.getAt(i).data.projtype;
        		    	record.data.projname =store3.getAt(i).data.projname;
        		    	record.data.materialid =store3.getAt(i).data.materialid;
        		    	record.data.compname =store3.getAt(i).data.compname;
        		    	record.data.compprod =store3.getAt(i).data.compprod;
        		    	record.data.shipvol =store3.getAt(i).data.shipvol;
        		    	record.data.massproddate =store3.getAt(i).data.massproddate;
        		    	record.data.remark =store3.getAt(i).data.remark;
        	        	project.push(record.data);
        	        }
        	        var custbps = [];
        		    var store4 =this.getViewModel().getStore('custBPS');
        	        for (var i = 0; i < store4.getCount(); i++) {
        	        	var record = Ext.create('BPSPortal.model.CustBPSInfo');  	
        	        	record.data.id = store4.getAt(i).data.id;
        		         if(store4.getAt(i).data.isshare=='是' || store4.getAt(i).data.isshare=='1'){
        			    	record.data.isshare=1;
        			    }else{
        			    	record.data.isshare=0;
        			    }
        			    record.data.custtype =store4.getAt(i).data.custtype;
        			    record.data.custcategory1 =store4.getAt(i).data.custcategory1;
        			    record.data.custcategory2 =store4.getAt(i).data.custcategory2;
        			    record.data.custcategory3 = store4.getAt(i).data.custcategory3;
        			    record.data.dealersales = store4.getAt(i).data.dealersales;
        			    record.data.bpssales =store4.getAt(i).data.bpssales;
        			    record.data.bpsfae = store4.getAt(i).data.bpsfae;
        			    custbps.push(record.data);
        	        }
        	        var param = {records:customer.data, address:address,contact:contact,project:project,custbps:custbps,prodid:prodid}; 
        	        
//        	        var params={customername:customer.data.custname,customerid:customer.data.id}
//		   	 	    customerControl.IsCustName(params,function(result){
//		   	 	    	console.log(result.success);
//	      	        	if(result.success){
//	      	        		var content='相同客户名已存在，是否继续操作？';
//                            Ext.Msg.confirm("提示", content, function (btn) {
//                                if (btn == "yes") {
//                                    alert(1111);	
//                                }
//                            });
//	      	        	}else{
//	      	        		alert(222);
//	      	        	}
//	      	        });
        	        
        	        Ext.MessageBox.wait("正在执行操作......", "提示");
        	        customerControl.createCustomers(param,function(result){
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
          	onCheckboxClick:function(checkbox, rowIndex, checked, eOpts){
         		 var store = this.getViewModel().getStore("address");
         		 var record= this.getViewModel().getStore("address").getAt(rowIndex);
         		for (var i = 0; i < store.getCount(); i++) {
        		     var rec = 	store.getAt(i);
        		     if(rec.get('id')!=record.data.id){
        		    	rec.set('isdefault',false);
        		     }
                }
         	}
            	
            	
});
