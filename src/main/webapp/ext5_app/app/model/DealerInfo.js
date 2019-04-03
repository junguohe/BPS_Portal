
Ext.define('BPSPortal.model.DealerInfo', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'id',
		   identifier: 'negative',
           fields: [
               {
                   name:'id'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			        read: dealerControl.findList
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});