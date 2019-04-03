
Ext.define('BPSPortal.view.systemadministration.directory.DirectoryViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.directory',
    
    	 onSearchClick: function (btn){
    	        var grid = Ext.ComponentQuery.query("viewport directory grid")[0];
    	        var value = btn.up('form').down('textfield[name=value]').getValue();
    	        var code = btn.up('form').down('textfield[name=code]').getValue();
    	        var store=grid.getStore();
    	        /*传name到后台查询*/
    	        var extraParams = {code:code,value:value};
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
    	    	var grid = Ext.ComponentQuery.query("viewport directory grid")[0];
    	        var store=grid.getStore();
    	    	var me =this;
    	    	var form = Ext.ComponentQuery.query("viewport directory form")[1];
    	    	var obj = form.getValues();
    			var id=obj.id;
    			if(id==''){
    				id=-1;
    			}
    			var module=obj.module;
    			var functions=obj.functions;
    			var value=obj.value;
    			var code=obj.code;
    			var remark=obj.remark;
    			var active=obj.active;
    			var param = {id:id,module:module,functions:functions,value:value,code:code,remark:remark,active:active}; 
    			console.log(param);
    			 if(form.isValid()){
    				 Ext.MessageBox.wait("正在执行操作......", "提示");
    				 directoryController.updateOrsave(param,function(result){
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
    	         var editDealerPanel = this.lookupReference('editPanel');
    	         editDealerPanel.setCollapsed(false);
    	         var grid = Ext.ComponentQuery.query("viewport directory grid")[0];
    	         var store = grid.getStore();
    	         var rec = new BPSPortal.model.Dictionarys({
    	             id: -1,active:1
    	         });
    	         store.insert(0, rec);
    	         grid.getSelectionModel().select(rec);
    	    }
    	    
});
