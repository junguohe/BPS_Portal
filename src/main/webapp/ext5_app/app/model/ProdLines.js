
Ext.define('BPSPortal.model.ProdLines', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'prodid',
		   identifier: 'negative',
           fields: [
               {
                   name:'prodid'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			        read: productlinecontroller.findByName
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});