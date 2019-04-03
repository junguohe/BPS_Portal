
Ext.define('BPSPortal.view.returngoods.monthoperation.MonthOperationViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.monthoperation',
 
    	requires: [
    	           'Ext.data.Store'
    	       ],

    	       stores: {
    	    	   year:{
    	    		   model: 'BPSPortal.model.Dictionary',
     	               autoLoad: true,
     	               proxy: {
     	                   type: 'direct',
     	                   api: {
     	                	  read: directoryController.findList,
     	                   },
     	               extraParams:{
      	                   	module:'upload',
      	                   	functions:'year'
      	                  },
     	                   reader: {
     	                       totalProperty: 'total',
     	                       rootProperty: 'records'
     	                   }
     	               }
    	    	   },
    	    	   month:{
    	    		   model: 'BPSPortal.model.Dictionary',
     	               autoLoad: true,
     	               proxy: {
     	                   type: 'direct',
     	                   api: {
     	                	  read: directoryController.findList,
     	                   },
     	               extraParams:{
      	                   	module:'upload',
      	                   	functions:'month'
      	                  },
     	                   reader: {
     	                       totalProperty: 'total',
     	                       rootProperty: 'records'
     	                   }
     	               }
    	    	   },
    	    	   monthstore:{
    	    		   model: 'BPSPortal.model.BPSMonthly',
     	               autoLoad: true,
     	               proxy: {
     	                   type: 'direct',
     	                   api: {
     	                	  read: bpsmonthlycontrol.readcustDTOs,
     	                   },
     	                   reader: {
     	                       totalProperty: 'total',
     	                       rootProperty: 'records'
     	                   }
     	               }
    	    	   }
    	          
    	       }
});