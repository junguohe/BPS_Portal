
Ext.define('BPSPortal.view.customer.customerupdate.UpdateStatusWinViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.updatestatus',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   cust: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.Customers',
	               listeners:{
 	            	  load:'onCustLoad'
 	              }
	           },
	    	   regstatus:{
	        	   model: 'BPSPortal.model.Dictionary',
 	              // autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'custreg',
  	                   	functions:'regstatus'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	           }
	       }


});