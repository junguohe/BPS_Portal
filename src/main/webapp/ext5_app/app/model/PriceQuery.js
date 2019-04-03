
Ext.define('BPSPortal.model.PriceQuery', {
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
			    	
			        read: pricestrategydetailcontroller.readPriceDTO
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});