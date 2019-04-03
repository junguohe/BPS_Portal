
Ext.define('BPSPortal.model.PriceSpecialDetail', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'did',
		   identifier: 'negative',
           fields: [
               {
                   name:'did'
               },
               {
            	   name:'seqno'
               }
//               {
//            	   name:'comppriceinc'
//               },
//               {
//            	   name:'applypriceinc'
//               },
//               {
//            	   name:'applyprice'
//               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			    	
			        read: pricespecialdetailcontrol.findpriceList,

			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});