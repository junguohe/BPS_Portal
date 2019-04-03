
Ext.define('BPSPortal.view.customer.customerquery.CustomerQueryViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.customerquery',
    //view
    onBackClick:function(button){

	        this.fireViewEvent('viewObject', this.getView(), 'customerquery', '');
	    
 	 },
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
    onAfterRender:function(){
    	
    	var data = this.getViewModel().get("data").data;
    	var custid=data.custid;
    	var cpid=data.cpid;
    	var creator=data.regcreator;
    	var dealerid=data.dealerid;
    	var status=data.regstatus;
    	var isbps=data.isbps;
    	if(status==2&&isbps==0){
    		return;
    	}
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
	        	store5.removeAll();
	            return;
	        }
	        // ,creator:creator
	        var paras = { cpid: cpid};
	        Ext.apply(store1.proxy.extraParams, paras);
	        store1.load({
                callback: function () {
                    //加载完后 将 store的参数清空
                	store1.proxy.extraParams = {};
                	
                }
            });
	        
	        var paras = { cpid: cpid };
	        Ext.apply(store2.proxy.extraParams, paras);
	        store2.load({
                callback: function () {
                    //加载完后 将 store的参数清空
                	store2.proxy.extraParams = {};
                }
                });
	        
	        var paras = { cpid: cpid };
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

	        var paras = { cpid: cpid,dealerid:dealerid};
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
    	//保存地址
    	onSaveAddressClick:function(button){
    		var grid = this.lookupReference('addressGrid');
    		var store = grid.getStore();
    		var records = button.getWidgetRecord();
    		var cpid = this.lookupReference('cpid').getValue();
    		
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
    		
    		
		    var record = Ext.create('BPSPortal.model.Address');
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
            var grid = this.lookupReference('contactGrid');
            var store=grid.getStore();
            var name = button.up('form').down('combobox[name=prodid]').getRawValue();
        	if(name==''||name==null){
        		Ext.Msg.alert('提示', '请选择产品线');
            	return;
        	}
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

    		var cpid = this.lookupReference('cpid').getValue();
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
		    var record = Ext.create('BPSPortal.model.ContactInfo');
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
            		var cpid = this.lookupReference('cpid').getValue();
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
                    var grid = this.lookupReference('custBPSGrid');
                    var store=grid.getStore();
                    var name = button.up('form').down('combobox[name=prodid]').getRawValue();
                	if(name==''||name==null){
                		Ext.Msg.alert('提示', '请选择产品线');
                    	return;
                	}
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
            		var cpid = this.lookupReference('cpid').getValue();
        		    var record = Ext.create('BPSPortal.model.CustBPSInfo');
        		    	record.data.id = records.data.id;
        			    record.data.cpid = cpid;
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
    //搜索
    onSearchClick: function (btn){
        var roleGrid = Ext.ComponentQuery.query("viewport customerquery grid")[0];
        
        var name = btn.up('form').down('textfield[name=name]').getValue();
        
        var prodid = btn.up('form').down('combobox[name=prodid]').getValue();
        var regstatus = btn.up('form').down('combobox[name=regstatus]').getValue();
        var region = btn.up('form').down('combobox[name=region]').getValue();
        var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
        var isshare = btn.up('form').down('combobox[name=isshare]').getValue();
        var bpssales = btn.up('form').down('textfield[name=bpssales]').getValue();
        var bpsfae = btn.up('form').down('textfield[name=bpsfae]').getValue();
        var parenetcompany = btn.up('form').down('customerpickers[name=parenetcompany]').getValue();
        var telno = btn.up('form').down('textfield[name=telno]').getValue();
        var address = btn.up('form').down('textfield[name=address]').getValue();
        var custtype = btn.up('form').down('textfield[name=custtype]').getValue();
        var taxno = btn.up('form').down('textfield[name=taxno]').getValue();
        var roleStore = roleGrid.getStore(); 
        /*传name到后台查询*/
        var extraParams = {name:name,prodid:prodid,regstatus:regstatus,region:region,
        		dealername:dealername,isshare:isshare,bpssales:bpssales,
        		bpsfae:bpsfae,parenetcompany:parenetcompany,telno:telno,
        		bestlatertime:null,address:address,custtype:custtype,taxno:taxno};
        
        roleStore.on("beforeload", function (roleStore, operation, eOpts) {
					 Ext.apply(roleStore.proxy.extraParams, extraParams);
//					 msgTip = Ext.MessageBox.show({  
//		                   title:'提示',  
//		                   msg:'页面报表统计信息刷新中,请稍后......'  
//		                });  
					});
		roleStore.load({
            callback: function () {
                //加载完后 将 store的参数清空
            	roleStore.proxy.extraParams = {};
            	//msgTip.hide();    // 加载完成，关闭提示框  
            }
       });			
    },
   
    	onAddCusClice:function(button, e, eOpts){
    		 var grid = this.lookupReference('customerGrid');
    	        var record = new BPSPortal.model.Customer({ id: -1 });
    	        record.phantom = true;
    	        var vmData = {};
    	        vmData.data = record;
    	        vmData.id = record.data.id;

    	        this.fireViewEvent('viewObject', this.getView(), 'customeradd', vmData);
    		
    	},
    	
    	//报备客户
    	onRegCustClick:function(button){
    		var grid = Ext.ComponentQuery.query("viewport customerquery grid")[0];
			if(grid.getSelectionModel().getSelection().length<=0){
				Ext.Msg.alert('提示', '请选择数据');
				return;
			}
			var	records =grid.getSelectionModel().getSelection()[0];
        		if(records.data.regstatus==1){
        			 Ext.Msg.alert('提示', '客户已报备');
        	            return;
        		}
           		if(records.data.regstatus== 2 && records.data.isbps==1 &&records.data.active==1 &&records.data.custregStatus== 0){
       			 Ext.Msg.alert('提示', '客户正在申请');
       	            return;
        		}
     	        records.phantom = true;
     	        var vmData = {};
     	        vmData.data = records;
     	        vmData.id = records.data.custid;

     	        this.fireViewEvent('viewObject', this.getView(), 'customerreg', vmData);
        
    	},
    	
    	  onSeeCustClick:function(button){
    		  var grid = Ext.ComponentQuery.query("viewport customerquery grid")[0];
    		  if(grid.getSelectionModel().getSelection().length<=0){
 				Ext.Msg.alert('提示', '请选择数据');
 				return;
    		  }
    		  var	records =grid.getSelectionModel().getSelection()[0];
    		  records.phantom = true;
    		  var vmData = {};
  	       		vmData.data = records;
  	       		vmData.id = records.data.custid;

  	       		this.fireViewEvent('viewObject', this.getView(), 'customerview', vmData);
    	 },
    	 onUpdateCustClick:function(button)
    	 {
    		var grid = Ext.ComponentQuery.query("viewport customerquery grid")[0];
   		  	if(grid.getSelectionModel().getSelection().length<=0){
				Ext.Msg.alert('提示', '请选择数据');
				return;
   		 	}
   		  	var	records =grid.getSelectionModel().getSelection()[0];
    		
 	        records.phantom = true;
 	        var vmData = {};
 	        vmData.data = records;
 	        vmData.id = records.data.custid;
 	        this.fireViewEvent('viewObject', this.getView(), 'CustomerUpdates', vmData);
    		 
    	 },
    	 	onCustRegLoad:function(store,records,successful,eOpts){
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
		onCustomerLoad : function(store, records, successful, eOpts) {
			var content='没有数据，是否要新增';
			var name = this.lookupReference('custname').getValue();

			if(store.getCount() >0){
				for (var i = 0; i < store.getCount(); i++) {
					var rec = store.getAt(i);
					if(rec.get('isbps')== 1&&rec.get('active')==1&&rec.get('approvestatus')!=3){
						rec.set('dealername','上海晶丰');
					}
		      }
			}
			store.commitChanges();
			
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
      onCustbpsLoad:function(store,records,successful,eOpts){
	   		if(store.getCount()<0){
	   			return;
	   		}
		for (var i = 0; i < store.getCount(); i++) {
		     var rec = 	store.getAt(i);
		     var isdefault = rec.get('isshare');
		     if(isdefault==1){
		    	 rec.set('isshare','是');
		    }else{
		    	 rec.set('isshare','否');
		    }
      }
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
 	},
 	//下载
    onDownloadClick:function(btn){
    	 var name = btn.up('form').down('textfield[name=name]').getValue();
    	 if(name==''){
    		 name=null;
         }
         var prodid = btn.up('form').down('combobox[name=prodid]').getValue();
         if(prodid==''){
        	 prodid=null;
         }
         var regstatus = btn.up('form').down('combobox[name=regstatus]').getValue();
         if(regstatus==''){
        	 regstatus=null;
         }
         var region = btn.up('form').down('combobox[name=region]').getValue();
         if(region==''){
        	 region=null;
         }
         var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
         if(dealername==''){
        	 dealername=null;
         }
         var isshare = btn.up('form').down('combobox[name=isshare]').getValue();
         if(isshare==''){
        	 isshare=null;
         }
         var bpssales = btn.up('form').down('textfield[name=bpssales]').getValue();
         if(bpssales==''){
        	 bpssales=null;
         }
         var bpsfae = btn.up('form').down('textfield[name=bpsfae]').getValue();
         if(bpsfae==''){
        	 bpsfae=null;
         }
         var parenetcompany = btn.up('form').down('customerpickers[name=parenetcompany]').getValue();
         if(parenetcompany==''){
        	 parenetcompany=null;
         }
         var telno = btn.up('form').down('textfield[name=telno]').getValue();
         if(telno==''){
        	 telno=null;
         }
         var address = btn.up('form').down('textfield[name=address]').getValue();
         if(address==''){
        	 address=null;
         }
         var custtype = btn.up('form').down('textfield[name=custtype]').getValue();
         if(custtype==''){
        	 custtype=null;
         }
         var taxno = btn.up('form').down('textfield[name=taxno]').getValue();
         if(taxno==''){
        	 taxno=null;
         }
         var extraParams = {name:name,prodid:prodid,regstatus:regstatus,region:region,
        		 dealername:dealername,isshare:isshare,bpssales:bpssales,bpsfae:bpsfae,parenetcompany:parenetcompany,telno:telno,address:address,
        		 custtype:custtype,taxno:taxno
         };
         window.open("DowloadController/custdownload?name="+encodeURI(name)+"&prodid="+prodid+"&regstatus="+regstatus+
        		 "&region="+encodeURI(region)+"&dealername="+encodeURI(dealername)+"&isshare="+isshare+"&bpssales="+encodeURI(bpssales)+
        		 "&bpsfae="+encodeURI(bpsfae)+"&parenetcompany="+parenetcompany+"&telno="+telno+"&address="+encodeURI(address)+
        		 "&custtype="+custtype+"&taxno="+taxno );
         
    }
});
