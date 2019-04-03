

Ext.define('BPSPortal.view.customer.customerupdate.UpdateStatusWinViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.updatestatus',
    
    
    onConfirmClick:function(button){
    	
    	   button.up("window").close();
    },
    onAfterRender:function(){
    	var me=this;
    	var custname=me.getView().custname;
    	var taxno=me.getView().taxno;
    	var ids=me.getView().ids;
    	
    	  var grid = me.lookupReference('grids'),
              store = me.getViewModel().getStore("cust");
    	    var paras = { custname: custname, taxno:taxno,id:ids};
	        Ext.apply(store.proxy.extraParams, paras);
	        store.load();
    	  
 
    },
    
  	onCustLoad:function(store,records,successful,eOpts){
   		if(store.getCount()<0){
   			return;
   		}
		for (var i = 0; i < store.getCount(); i++) {
		     var rec = 	store.getAt(i);
		     var isbps=store.getAt(i).data.isbps;
		     var regstatus =store.getAt(i).data.regstatus;
		     if(isbps==1&&regstatus!=2){
		    	 rec.set('dealername',"上海晶丰");
		    }else{
		    	 rec.set('isdefault',"");
		    }
      }
	}
});
