
Ext.define('BPSPortal.model.PriceCode', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'id',
		   identifier: 'negative',
           fields: [
               {
                   name:'sid'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			        read: pricestrategycontrol.findPriceCodes
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});