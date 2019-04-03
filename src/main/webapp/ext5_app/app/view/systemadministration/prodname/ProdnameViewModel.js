
Ext.define('BPSPortal.view.systemadministration.prodname.ProdnameViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.prodname',
	requires: [
	           'Ext.data.Store'
	       ],
    stores: {
    	prodname: {
	       model: 'BPSPortal.model.ProdLines',
	       pageSize:15,
	       autoLoad: true
	   },
	   noOrYes:{
		   model: 'BPSPortal.model.Dictionary',
            autoLoad: true,
            proxy: {
                type: 'direct',
                api: {
             	  read: directoryController.findList,
                },
            extraParams:{
                 	module:'all',
                 	functions:'noOrYes'
                },
                reader: {
                    totalProperty: 'total',
                    rootProperty: 'records'
                }
            }
	   }
    }
});