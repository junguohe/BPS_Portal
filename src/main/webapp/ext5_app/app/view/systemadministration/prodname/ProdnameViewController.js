
Ext.define('BPSPortal.view.systemadministration.prodname.ProdnameViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.prodname',
    
    	 onSearchClick: function (btn){
    	        var grid = Ext.ComponentQuery.query("viewport prodname grid")[0];
    	        var name = btn.up('form').down('textfield[name=prodname]').getValue();
    	        var code = btn.up('form').down('textfield[name=prodcode]').getValue();
    	        var store=grid.getStore();
    	        /*传name到后台查询*/
    	        var extraParams = {name:name,code:code};
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
    	    	var grid = Ext.ComponentQuery.query("viewport prodname grid")[0];
    	        var store=grid.getStore();
    	    	var me =this;
    	    	var form = Ext.ComponentQuery.query("viewport prodname form")[1];
    	    	var obj = form.getValues();
    			var prodid=obj.prodid;
    			if(prodid==''){
    				prodid=-1;
    			}
    			var prodname=obj.prodname;
    			var prodcode=obj.prodcode;
    			var remark=obj.remark;
    			var active=obj.active;
    			var param = {prodid:prodid,prodname:prodname,prodcode:prodcode,remark:remark,active:active}; 
    			 if(form.isValid()){
    				 Ext.MessageBox.wait("正在执行操作......", "提示");
    				 productlinecontroller.updateOrsave(param,function(result){
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
    	         var grid = Ext.ComponentQuery.query("viewport prodname grid")[0];
    	         var store = grid.getStore();
    	         var count = (store.getCount() > 0) ? store.getCount() : 0; 
    	         var rec = new BPSPortal.model.ProdLine({
    	        	 prodid: -(count+1),active:1
    	         });
    	         store.insert(0, rec);
    	         grid.getSelectionModel().select(rec);
    	    }
    	    
});
