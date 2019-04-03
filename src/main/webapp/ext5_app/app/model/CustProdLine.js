
Ext.define('BPSPortal.model.CustProdLine', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'cpid',
           identifier: 'negative',
           fields: [
               {
                   name:'cpid'
               },
               {
            	   name:'custname'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			        read: pricespecialdetailcontrol.readerCust
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});