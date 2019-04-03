

Ext.define('BPSPortal.view.price.applicationapproval.ApplicationApprovalViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.applicationapproval',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   price:{
	    		   model:'BPSPortal.model.PriceSpecial' ,
	    		   autoLoad: true,
	    		    proxy: {
	 	                   type: 'direct',
	 	                   api: {
	 	                	  read: priceSpecialControl.findList
	 	                   },
	 	                   extraParams:{
	 	            	   	billstatus:'1'
	  	                  },
	 	                   reader: {
	 	                       totalProperty: 'total',
	 	                       rootProperty: 'records'
	 	                   }
	 	               }
	    		   
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