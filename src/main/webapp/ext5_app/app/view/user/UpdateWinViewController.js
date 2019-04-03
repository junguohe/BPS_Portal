

Ext.define('BPSPortal.view.user.UpdateWinViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.updatewin',
    
    
    onAfterRender:function(){
    	var me=this;
    	var userid = me.getView().userid;
    	var store = me.getViewModel().getStore("rolestore");
    	var paras = { userid: userid };
	        Ext.apply(store.proxy.extraParams, paras);
	       store.load();
    	
    },
    onSubmitClick:function(btn){
    	var grid=this.lookupReference('roleGrid');
    	var store=grid.getStore();
    	var roleids=[];
    	var userid=this.getView().userid;
    	  for (var i = 0; i < store.getCount(); i++) {
    		  if(store.getAt(i).data.checked){
    			  roleids.push(store.getAt(i).data.roleid);
    		  }
    		   
    	  }
  		var param = {userid:userid,roleids:roleids}; 
  		bpsrolecontrol.SaveOrUpdate(param,function(result){
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
