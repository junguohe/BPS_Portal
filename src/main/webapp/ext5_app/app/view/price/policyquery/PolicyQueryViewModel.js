
Ext.define('BPSPortal.view.price.policyquery.PolicyQueryViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.policyquery',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   price:{
	        	   model: 'BPSPortal.model.PriceStrategys',
	        	   pageSize:10
	           },
	           status:{
	    		   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'price',
  	                   	functions:'status'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	    	   },
		    	   noOrYes:{
		    		   model: 'BPSPortal.model.Dictionary',
	 	               autoLoad: true,
	 	               proxy: {
	 	                   type: 'direct',
	 	                   api: {
	 	                	  read: directoryController.findList,
	 	                   },
	 	               extraParams:{
	  	                   	module:'all',
	  	                   	functions:'noOrYes'
	  	                  },
	 	                   reader: {
	 	                       totalProperty: 'total',
	 	                       rootProperty: 'records'
	 	                   }
	 	               }
		    	   }
	       }

});