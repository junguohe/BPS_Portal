
Ext.define('BPSPortal.view.systemadministration.city.UpdateCityViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.city',
    
    	 onSearchClick: function (btn){
    	        var grid = Ext.ComponentQuery.query("viewport city grid")[0];
    	        var province = btn.up('form').down('textfield[name=province]').getValue();
    	        var city = btn.up('form').down('textfield[name=city]').getValue();
    	        var store=grid.getStore();
    	        /*传name到后台查询*/
    	        var extraParams = {province:province,city:city};
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
    	    	var grid = Ext.ComponentQuery.query("viewport city grid")[0];
    	        var store=grid.getStore();
    	    	var me =this;
    	    	var form = Ext.ComponentQuery.query("viewport city form")[1];
    	    	var obj = form.getValues();
    			var id=obj.id;
    			var city=obj.city;
    			var province=obj.province;
    			var provinceid=obj.provinceid;
    			var param = {id:id,city:city,province:province,provinceid:provinceid}; 
    			 if(form.isValid()){
    				 Ext.MessageBox.wait("正在执行操作......", "提示");
    				 regioninfocontrol.updateOrSaveRegion(param,function(result){
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
    		
    			
    	    },
    	    //新增省
    	  //  onAddProvinceClick:function(){},
    	    onProvinceClick:function(btn){
    	    	var grid = Ext.ComponentQuery.query("viewport city grid")[0];
    	        var store=grid.getStore();
    	    	var province=this.lookupReference('province').getValue();
    	    	if(province==''){
    	    		Ext.Msg.alert('提示', '省不能为空');
    	        	return;
    	    	}
    			var param = {province:province}; 
   				 Ext.MessageBox.wait("正在执行操作......", "提示");
   				regioninfocontrol.saveProvince(param,function(result){
    			        	if(result.success){
    			        		Ext.MessageBox.hide();
    			        		 Ext.toast({
    			                    title: 'Tips', html: result.msg, align: 't',
    			                    width: 240,
    			                    bodyPadding: 10
    			                });
    			        		    btn.up('window').close();
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
    	    //新增市
    	  //  onAddCityClick:function(){},
		   	 onCityClick:function(btn){
		   		var grid = Ext.ComponentQuery.query("viewport city grid")[0];
    	        var store=grid.getStore();
		   		var provinceid=this.lookupReference('provinceid').getValue();
		   		var city=this.lookupReference('city').getValue();
		   		console.log(provinceid);
		   		if(provinceid==null){
		   			Ext.Msg.alert('提示', '省不能为空');
		        	return;
		   		}else{
		   			if(provinceid.length!=36){
			   			Ext.Msg.alert('提示', '请选择下拉列表中的省');
			        	return;
	    	    	}
		   		}
		   		if(city==''){
		   			Ext.Msg.alert('提示', '市不能为空');
		        	return;
    	    	}
		 		var param = {city:city,provinceid:provinceid}; 
   				 Ext.MessageBox.wait("正在执行操作......", "提示");
   				regioninfocontrol.saveCity(param,function(result){
    			        	if(result.success){
    			        		Ext.MessageBox.hide();
    			        		 Ext.toast({
    			                    title: 'Tips', html: result.msg, align: 't',
    			                    width: 240,
    			                    bodyPadding: 10
    			                });
    			        		    btn.up('window').close();
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
    	    onItemDblClick:function(){
    	    	var editToolingPanel = this.lookupReference('editPanel');
    	        editToolingPanel.setCollapsed(false);
    	    }
    	    
});
