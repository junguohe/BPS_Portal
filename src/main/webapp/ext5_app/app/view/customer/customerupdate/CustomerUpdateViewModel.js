Ext.define('BPSPortal.view.customer.customerupdate.CustomerUpdateViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.customerupdate',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   person: {
 	               autoLoad: true,
	               model: 'BPSPortal.model.Person',
	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	   read: personControl.getAll
 	                   },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	           },

	           prodline: {
	               model: 'BPSPortal.model.ProdLine',
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
	           regstatus:{
	        	   model: 'BPSPortal.model.Dictionary',
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'customer',
  	                   	functions:'status'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	           },
	           userbps:{
	        	   model: 'BPSPortal.model.UserBPS',
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	   read: auuserController.readuserbps
 	                   },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	           },
	           dealer:{
	        	   model: 'BPSPortal.model.DealerInfos',
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	   read: dealerControl.getAll
 	                   },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	           },
	           customers:{
	        	   model: 'BPSPortal.model.Customer'
	           },
	           customer:{
	        	   model: 'BPSPortal.model.Customer',
	        	   listeners:{
 	            	  load:'onCustomerLoad'
	               },
	              // autoLoad: true,
	               sorters:[
	                        {property:"regstatus" , direction:"ASC"} ,
	                        {property:"dealername" , direction:"DESC"},
	                        {property:"prodname" , direction:"ASC"},
	                        {property:"custcode" , direction:"ASC"}        
	                    ],
//	               sorters: { property: 'regstatus', direction: 'ASC'
//	            	   ,property: 'custcode', direction: 'ASC' },
	              // sorters: { property: 'custcode', direction: 'ASC' },
	              // groupField: 'custcode',
	        	  // remoteSort: true
	        	   pageSize: 10
	           }
	       }


});