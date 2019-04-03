

Ext.define('BPSPortal.view.price.applicationquery.ApplicationQueryViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.applicationquery',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   price:{
	    		   model:'BPSPortal.model.PriceSpecialDetail',
	    		   pageSize:30,
	    		   sorters:[
		                      {property:"applydate" , direction:"DESC"}    
		                    ]
	    	   },
	    	   billstatus:{
	    		   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'price',
  	                   	functions:'specialtype'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	    	   }
	       }

});
