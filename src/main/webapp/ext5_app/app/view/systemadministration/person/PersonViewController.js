
Ext.define('BPSPortal.view.systemadministration.person.PersonViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.person',
    
    	 onSearchClick: function (btn){
    	        var grid = Ext.ComponentQuery.query("viewport person grid")[0];
    	        var name = btn.up('form').down('textfield[name=name]').getValue();
    	        var fempgroup = btn.up('form').down('textfield[name=fempgroup]').getValue();
    	        var store=grid.getStore();
    	        /*传name到后台查询*/
    	        var extraParams = {name:name,fempgroup:fempgroup};
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
    	    	var grid = Ext.ComponentQuery.query("viewport person grid")[0];
    	        var store=grid.getStore();
    	    	var me =this;
    	    	var form = Ext.ComponentQuery.query("viewport person form")[1];
    	    	var obj = form.getValues();
    			var id=obj.id;
    			if(id==''){
    				id=-1;
    			}
    			var per_id=obj.per_id;
    			var per_name=obj.per_name;
    			var departmentid=obj.departmentid;
    			var departmentname=obj.departmentname;
    			var empid=obj.empid;
    			var mangerid=obj.mangerid;
    			var mangername=obj.mangername;
    			var account=obj.account;
    			var fhiredate=obj.fhiredate;
    			var fempgroup=obj.fempgroup;
    			var active=obj.active;
    			var peremail=obj.peremail;
    			var param = {id:id,per_id:per_id,per_name:per_name,departmentid:departmentid,
    					departmentname:departmentname,empid:empid,mangerid:mangerid,account:account,
    					fhiredate:fhiredate,fempgroup:fempgroup,active:active,peremail:peremail,mangername:mangername}; 
    			 if(form.isValid()){
    				 Ext.MessageBox.wait("正在执行操作......", "提示");
    				 personControl.updateOrsave(param,function(result){
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
    	    onAddClick:function(){
    	         var editprodPanel = this.lookupReference('editprodPanel');
    	         editprodPanel.setCollapsed(false);
    	         var grid = Ext.ComponentQuery.query("viewport person grid")[0];
    	         var store = grid.getStore();
    	         var count = (store.getCount() > 0) ? store.getCount() : 0; 
    	         var rec = new BPSPortal.model.Person({
    	        	 id: -(count+1),active:1
    	         });
    	         store.insert(0, rec);
    	         grid.getSelectionModel().select(rec);
    	    },
    	    onDelClick:function(){
        		var grid = this.lookupReference('PersonGrid');//Ext.ComponentQuery.query("viewport pricequery grid")[0];
        		var store=grid.getStore();
    			if(grid.getSelectionModel().getSelection().length<=0){
    				Ext.Msg.alert('提示', '请选择数据');
    				return;
    			}
    			var record=grid.getSelectionModel().getSelection()[0];
    			var id=record.data.id;
    		    var param = {id:id}; 
    			var content = '是否确认删除此数据?';
    			Ext.Msg.confirm('确认',content,function(btn){
    				if(btn=='yes'){
    	    		    personControl.DeletePerson(param,function(result){ 
    	    			    	if(result=='success'){
    	    			    		 Ext.toast({
    	    			                    title: 'Tips', html:'删除成功！', align: 't',
    	    			                    width: 240,
    	    			                    bodyPadding: 10
    	    			                });
    	    			    		 store.load();
    	    			    	}else{
    	         	        		 Ext.toast({
    	        		                    title: 'Tips', html:result, align: 't',
    	        		                    width: 240,
    	        		                    bodyPadding: 10
    	        		                });
    	        	        	}
    	    		       	});
    				}
    			})
    	    }
    	    
});
