
Ext.define('BPSPortal.view.user.BPSAddViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.bpsadd',
	requires: [
	           'Ext.data.Store'
	       ],
    stores: {
    	bpsinfo: {
 	       model: 'BPSPortal.model.BPSInfo',
 	       pageSize: 15,
 	       autoLoad: true
 	   }
     }
});