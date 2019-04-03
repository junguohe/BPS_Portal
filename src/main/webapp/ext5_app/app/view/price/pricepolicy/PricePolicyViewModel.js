
Ext.define('BPSPortal.view.price.pricepolicy.PricePolicyViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.pricepolicy',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   material: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.MaterialInfo'
	           },
	    	   price: {
	               model: 'BPSPortal.model.PriceStrategyDetail',
	               proxy: {
	            	   type: 'direct',
	                   api: {
	                	  read: pricestrategydetailcontroller.readUploadInfo,
	                   },
	                   reader: {
	                       totalProperty: 'total',
	                       rootProperty: 'records'
	                   }
 	               }
	           },
	     	   currencytype:{
	    		   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'price',
  	                   	functions:'currency'
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
	    	   },
	           pricecode:{
	        	   model: 'BPSPortal.model.PriceCode'
	        	   
	           }
	          
	       }

});
