
Ext.define('BPSPortal.view.price.pricequery.PriceQueryViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.pricequery',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	    prodline: {
		               model: 'BPSPortal.model.ProdLine',
		               autoLoad: true,
		               proxy: {
		                   type: 'direct',
		                   api: {
		                	   read: productlinecontroller.getAllProdLine,
		                   },
		                   reader: {
		                       totalProperty: 'total',
		                       rootProperty: 'records'
		                   }
		               }
		           },
		           specialprice:{
		        	   model: 'BPSPortal.model.PriceStrategyss',
		        	   pageSize:10
		           },
		           price:{
		        	   model: 'BPSPortal.model.PriceQuery',
		        	   pageSize:10
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