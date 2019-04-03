
Ext.define('BPSPortal.model.PriceStrategy', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'sid',
		   identifier: 'negative',
           fields: [
               {
                   name:'sid'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			    	
			        read: pricestrategydetailcontroller.readPriceDTO
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});