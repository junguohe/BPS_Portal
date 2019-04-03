
Ext.define('BPSPortal.model.DealarV', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'id',
		   identifier: 'negative',
           fields: [
               {
                   name:'id'
               },
               {
            	   name:'per_name'
               },
               {
            	   name:'empid'
               },
               {
            	   name:'fempgroup'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			        read: dealarvcontroller.getAll
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});