
Ext.define('BPSPortal.view.customer.customeradd.CustomerAddViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.customeradd',
    
    onAfterRender:function(){
    	var me=this;
    	if(me.getViewModel().get("data")==null){
    		
    		return;
    	}
    	var data = me.getViewModel().get("data").data;
    	
    	if(data.custid==-1){
    		 var record = new BPSPortal.model.Customer({custid:-1,custname:data.custname});
    	     var name = me.lookupReference('custnames');
    	     name.setValue(data.custname);
    		 
    	}else{
    		 var record = new BPSPortal.model.Customer({custid:data.custid,custname:data.custname,
    			 custcode:data.custcode,prodid:data.prodid,region:data.region,taxno:data.taxno,parenetcompany:data.parenetcompany,
    			 isparent:data.isparent});
    	
    	var cpid=data.cpid;
    	var creator=data.regcreator;
    	var status=data.regstatus;
    	var isbps=data.isbps;
    	if(status==2&&isbps!=1){
    		return;
    	}
    	var store1 = me.getViewModel().getStore("address");
	        var store2 = me.getViewModel().getStore("contact");
	        var store3 = me.getViewModel().getStore("project");
	        var store4 = me.getViewModel().getStore("custBPS");
	        
	        if (cpid == "-1" || cpid == "" || cpid == undefined) {
	        	store1.removeAll();
	        	store2.removeAll();
	        	store3.removeAll();
	        	store4.removeAll();
	            return;
	        }
	        var paras = { cpid: cpid};
	        Ext.apply(store1.proxy.extraParams, paras);
	        store1.load({
                callback: function () {
                    //加载完后 将 store的参数清空
                	store1.proxy.extraParams = {};
                }
                });
	        //,creator:creator
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
	        
	        var paras = { cpid: cpid};
	        Ext.apply(store4.proxy.extraParams, paras);
	        store4.load({
                callback: function () {
                    //加载完后 将 store的参数清空
                	store4.proxy.extraParams = {};
                }	
                });
    	}
    },
    
    //添加地址
    onAddAddressClick:function(button){
        var grid = this.lookupReference('addressGrid');
    	var name = button.up('form').down('combobox[name=prodid]').getRawValue();
    	if(name==''||name==null){
    		Ext.Msg.alert('提示', '请选择产品线');
        	return;
    	}
        var store=grid.getStore();
        var count = (store.getCount() > 0) ? store.getCount() : 0; 
        if(count==0){
        	var record = new BPSPortal.model.Address({isdefault:true,prodname:name});
        }else{
        	var record = new BPSPortal.model.Address({prodname:name});
        }
        store.insert(count, record); 
        grid.getSelectionModel().select(record);
    },
    onDelAddressClick:function(button){
    	var grid = this.lookupReference('addressGrid');
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
            	if(id<0){

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

                            	 store.remove(record);
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
        });
	},
    //添加项目
	onAddProjectClick:function(button){
            var grid = this.lookupReference('projectGrid');
            var store=grid.getStore();
            var name = button.up('form').down('combobox[name=prodid]').getRawValue();
        	if(name==''||name==null){
        		Ext.Msg.alert('提示', '请选择产品线');
            	return;
        	}
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
            	if(id<0){

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
	
	//保存信息
	onSaveClick:function(button){
		   
		var me =this;
		var form = button.up('form');
	    var obj = this.getView().getForm().getValues();
	    if(obj.prodid==""||obj.prodid==null){
        	Ext.Msg.alert('提示', '请选择产品线');
        	return;
        }
        if(obj.custname==""||obj.custname==null){
        	Ext.Msg.alert('提示', '请填写客户全称');
        	return;
        }
        if(obj.region==""||obj.region==null){
        	Ext.Msg.alert('提示', '请选择区域');
        	return;
        }	
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
	    
	    var custid = button.up('form').down('textfield[name=custid]').getValue();
	    if(custid==null||custid==''){
	    	custid='-1';
	    }
	    customer.data.id = custid;
	    customer.data.custname = obj.custname;
	    customer.data.custcode = obj.custcode;
	    customer.data.region = obj.region;
	    customer.data.taxno = obj.taxno;
	    customer.data.active = 1;
	    customer.data.creator = obj.creator;
	    customer.data.createdate = obj.createdate;
	    customer.data.updator = obj.updator;
	    customer.data.updatedate = obj.updatedate;
	    
	    var prodid=obj.prodid;
	    
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
        Ext.MessageBox.wait("正在执行操作......", "提示");
        customerControl.createCustomer(param,function(result){
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
     //报备
    onRegSaveClick:function(button){
	  var me=this;
		var form = button.up('form');
	    var obj = me.getView().getForm().getValues();
	    var custname = button.up('form').down('textfield[name=custname]').getValue(); 
	    var custid = button.up('form').down('textfield[name=custid]').getValue();  		
	    var prodid=obj.prodid;
        var address = [];
 		var store1 =me.getViewModel().getStore('address');
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
 		   var store2=me.getViewModel().getStore('contact');
 	        if(store2.getCount()<1){
 	        	Ext.Msg.alert('提示', '请添加至少一条联系人信息');
	            	return;
 	        }
 		    var store2 =me.getViewModel().getStore('contact');
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
 	        	if((store2.getAt(i).data.mobile==''||store2.getAt(i).data.mobile==null) && (store2.getAt(i).data.telno ==''||store2.getAt(i).data.telno==null)){
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
 		    var store3 =me.getViewModel().getStore('project');
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
 		    var store4 =me.getViewModel().getStore('custBPS');
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
			var param = {custid:custid,prodid:prodid, address:address,contact:contact,project:project,custbps:custbps}; 
			Ext.MessageBox.wait("正在执行操作......", "提示");
			custRegController.createCustReg(param,function(result){ 
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
        })

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
		  
	onValueChange:function(field, newValue, oldValue, eOpts){
            	  var city = this.getViewModel().getStore("city");
                  if (oldValue != null) {
                	  var grid=this.lookupReference('addressGrid');
                	  city.removeAll();
                  }
                  var paras = { name: newValue };
                  Ext.apply(city.proxy.extraParams, paras);
                 // city.load()
                  city.load({
                      callback: function () {
                          //加载完后 将 store的参数清空
                    	  city.proxy.extraParams = {};
                      }
                      });

              },
    //保存报备信息为草稿状态
    onUpdateStatusClick:function(button){

       		   
          		var me =this;
          		var form = button.up('form');
      		    var obj = this.getView().getForm().getValues();
      		    if(obj.prodid==""||obj.prodid==null){
      	        	Ext.Msg.alert('提示', '请选择产品线');
  	            	return;
      	        }
      	        if(obj.custname==""||obj.custname==null){
      	        	Ext.Msg.alert('提示', '请填写客户全称');
  	            	return;
      	        }
      	        if(obj.region==""||obj.region==null){
      	        	Ext.Msg.alert('提示', '请选择区域');
  	            	return;
      	        }	
      		    var customer = Ext.create('BPSPortal.model.Customer');
      		    if(obj.parenetcompany==''||obj.parenetcompany==undefined||obj.parenetcompany==null){
      		    	customer.data.isparent = 1
      		    	customer.data.parenetcompany=null;
      		    }else{
      		    	customer.data.isparent = 0;
      		    	customer.data.parenetcompany=obj.parenetcompany;
      		    }
      		    var custid = button.up('form').down('textfield[name=custid]').getValue();
      		    if(custid==null||custid==''){
      		    	custid='-1';
      		    }
      		    customer.data.id = custid;
      		    customer.data.custname = obj.custname;
      		    customer.data.custcode = obj.custcode;
      		    //customer.data.parenetcompany = obj.parenetcompany;
      		    customer.data.region = obj.region;
      		    customer.data.taxno = obj.taxno;
      		    customer.data.active = 1;
      		    customer.data.creator = obj.creator;
      		    customer.data.createdate = obj.createdate;
      		    customer.data.updator = obj.updator;
      		    customer.data.updatedate = obj.updatedate;
  			    
  			    var prodid=obj.prodid;
  			    
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
           	        	Ext.Msg.alert('提示', '系人职位不能为空');
       	            	return;
           	        }
       	        	if(store2.getAt(i).data.name==''||store2.getAt(i).data.name==null){
           	        	Ext.Msg.alert('提示', '系人姓名不能为空');
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
      	        customerControl.createCustomer(param,function(result){
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
    //修改客户报备信息
    onUpdateClick:function(button){
       		   
          		var me =this;
          		var form = button.up('form');
      		    var obj = this.getView().getForm().getValues();
      	        if(obj.region==""||obj.region==null){
      	        	Ext.Msg.alert('提示', '请选择区域');
  	            	return;
      	        }	
      		    var customer = Ext.create('BPSPortal.model.Customer');
      		    var custname= button.up('form').down('textfield[name=custname]').getValue();
      		    var parenetcompanys = button.up('form').down('customerpickers[name=parenetcompany]').getValue();//id
      		    var parenetcompanyss = button.up('form').down('customerpickers[name=parenetcompany]').getRawValue();//value
//	      	    console.log(parenetcompanys+'id');
//	      	    console.log(parenetcompanyss+'value');
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
      		    var custid = button.up('form').down('textfield[name=custid]').getValue();
      		    customer.data.id = custid;
      		    customer.data.custname = obj.custname;
      		    customer.data.custcode = obj.custcode;
      		    customer.data.region = obj.region;
      		    customer.data.taxno = obj.taxno;
      		    customer.data.active = 1;
      		    customer.data.creator = obj.creator;
      		    customer.data.createdate = obj.createdate;
      		    customer.data.updator = obj.updator;
      		    customer.data.updatedate = obj.updatedate;
  			    var prodid=obj.prodid;
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
           	        	Ext.Msg.alert('提示', '系人职位不能为空');
       	            	return;
           	        }
       	        	if(store2.getAt(i).data.name==''||store2.getAt(i).data.name==null){
           	        	Ext.Msg.alert('提示', '系人姓名不能为空');
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
	   	 	        
	   	 	        var params={customername:customer.data.custname,customerid:customer.data.id}
		   	 	    customerControl.IsCustName(params,function(result){
		   	 	    	console.log(result.success);
	      	        	if(result.success){
	      	        		var content='相同客户名已存在，是否继续操作？';
                            Ext.Msg.confirm("提示", content, function (btn) {
                                if (btn == "yes") {
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
                                }
                            });
	      	        	}else{
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
	      	        	}
	      	        });
	   	 	        
	
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
