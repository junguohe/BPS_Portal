
Ext.define('BPSPortal.view.systemadministration.directory.DirectoryViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.directory',
	requires: [
	           'Ext.data.Store'
	       ],
    stores: {
    	dictionarys: {
	       model: 'BPSPortal.model.Dictionarys',
	       pageSize: 15,
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