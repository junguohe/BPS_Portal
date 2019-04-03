
Ext.define('BPSPortal.model.PriceStrategyss', {
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
			    	
			        read: pricespecialdetailcontrol.readPriceSpeDTO
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});