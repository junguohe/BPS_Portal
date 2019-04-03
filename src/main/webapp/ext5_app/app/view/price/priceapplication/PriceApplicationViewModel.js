
Ext.define('BPSPortal.view.price.priceapplication.PriceApplicationViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.priceapplication',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   
	    	   project: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.ProjectInfo'
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
	           projtype:{
	    		   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'project',
  	                   	functions:'projtype'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	    	   },
	    	   material: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.MaterialInfo'
	           },
	    	   custprodline:{
	    		   autoLoad: true,
	    		   model:'BPSPortal.model.CustProdLine'
	    	   },
	    	   pricespecialdetailstore:{
	        	   model: 'BPSPortal.model.PriceSpecialDetail'
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
	           applytype: {
	        	   model: 'BPSPortal.model.Dictionary',
  	               autoLoad: true,
  	               proxy: {
  	                   type: 'direct',
  	                   api: {
  	                	  read: directoryController.findList,
  	                   },
  	               extraParams:{
   	                   	module:'price',
   	                   	functions:'applytype'
   	                  },
  	                   reader: {
  	                       totalProperty: 'total',
  	                       rootProperty: 'records'
  	                   }
  	               }
	           }
	          
	       }

});
