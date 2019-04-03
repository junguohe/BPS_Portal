
Ext.define('BPSPortal.view.user.BPSAddViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.bpsadd',
        
   	 onSearchClick: function (btn){
   	        var grid = Ext.ComponentQuery.query("viewport bpsadd grid")[0];
   	        var name = btn.up('form').down('textfield[name=username]').getValue();
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
   	    alert(11);	
     },
   	 onUpdateClick:function(){
   		 var grid = Ext.ComponentQuery.query("viewport bpsadd grid")[0];
   		 if (grid.getSelectionModel().getSelection().length <= 0) {
    		Ext.Msg.alert('提示', '请选择要修改的数据');
        	return;
         }
   		 var records =grid.getSelectionModel().getSelection()[0];
		     records.phantom = true;
		    
	         var userid = records.data.userid;
	         Ext.create("BPSPortal.view.user.UpdateWin", {
	        	 userid:userid
	         }).show();
   	 }
   	    
});
