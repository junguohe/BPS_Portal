
Ext.define('BPSPortal.view.systemadministration.prodperson.ProdPersonViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.prodperson',
    
    	 onSearchClick: function (btn){
    	        var grid = Ext.ComponentQuery.query("viewport prodperson grid")[0];
    	        var area = btn.up('form').down('textfield[name=area]').getValue();
    	        var mgr = btn.up('form').down('textfield[name=mgr]').getValue();
    	        var store=grid.getStore();
    	        /*传name到后台查询*/
    	        var extraParams = {area:area,mgr:mgr};
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
    	    	var grid = Ext.ComponentQuery.query("viewport prodperson grid")[0];
    	        var store=grid.getStore();
    	    	var me =this;
    	    	var form = Ext.ComponentQuery.query("viewport prodperson form")[1];
    	    	var obj = form.getValues();
    			var id=obj.id;
    			if(id==''){
    				id=-1;
    			}

    			var area_id=obj.area_id;
    			var area=obj.area;
    			var mgr_id=obj.mgr_id;
    			var mgr=obj.mgr;
    			var fae_id=obj.fae_id;
    			var faemgr=obj.faemgr;
    			var area_directory_id=obj.area_directory_id;
    			var param = {id:id,area_id:area_id,area:area,mgr_id:mgr_id,
    					mgr:mgr,fae_id:fae_id,faemgr:faemgr,area_directory_id:area_directory_id}; 
    			 if(form.isValid()){
    				 Ext.MessageBox.wait("正在执行操作......", "提示");
    				 prodpersonControl.updateOrsave(param,function(result){
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
    	         var grid = Ext.ComponentQuery.query("viewport prodperson grid")[0];
    	         var store = grid.getStore();
    	         var count = (store.getCount() > 0) ? store.getCount() : 0; 
    	         var rec = new BPSPortal.model.ProdLine({
    	        	 id: -(count+1),active:1
    	         });
    	         store.insert(0, rec);
    	         grid.getSelectionModel().select(rec);
    	    },
    	    
    	    onDelClick:function(){
        		var grid = this.lookupReference('ProdPersonGrid');//Ext.ComponentQuery.query("viewport pricequery grid")[0];
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
    					 prodpersonControl.DeleteProdPerson(param,function(result){ 
    	    			    	if(result=='success'){
    	    			    		 Ext.toast({
    	    			                    title: 'Tips', html: '删除成功！', align: 't',
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
    			})
    		   
        	
    	    }
    	    
});
