
Ext.define('BPSPortal.model.PriceStrategys', {
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
			    	
			        read: pricestrategydetailcontroller.readPriceStrategy
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});