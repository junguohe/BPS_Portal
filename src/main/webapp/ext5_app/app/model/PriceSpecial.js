
Ext.define('BPSPortal.model.PriceSpecial', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'spid',
		   identifier: 'negative',
           fields: [
               {
                   name:'spid'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			    	
			        read: priceSpecialControl.findList,

			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});