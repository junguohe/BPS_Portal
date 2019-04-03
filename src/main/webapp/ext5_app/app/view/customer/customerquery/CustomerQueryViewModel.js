

Ext.define('BPSPortal.view.customer.customerquery.CustomerQueryViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.customerquery',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   custtype:{
	        	   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'customer',
  	                   	functions:'custtype'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	    		   
	    	   },

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
	    	   isactive:{
	    		   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'all',
  	                   	functions:'active'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	    	   },
	    	   province: {
	               model: 'BPSPortal.model.Region'
	           },
	           city: {
	               model: 'BPSPortal.model.City'
	           },
	           material: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.MaterialInfo'
	           },
	           address: {
	               model: 'BPSPortal.model.Address',
	               listeners:{
    	            	  load:'onCustRegLoad'
    	              }
	           },
	           customer: {
	               model: 'BPSPortal.model.Customer',
	               listeners:{
    	            	  load:'onCustomerLoad'
 	               }
//	               sorters:[
//	                        {property:"dealername" , direction:"DESC"},
//	                        {property:"prodname" , direction:"ASC"},
//	                        {property:"custcode" , direction:"ASC"}        
//	                    ],
	           },
	           contact: {
	               model: 'BPSPortal.model.ContactInfo'
	           },
	           project: {
	               model: 'BPSPortal.model.ProjectInfo'
	           },
	           custBPS: {
	               model: 'BPSPortal.model.CustBPSInfo'
	           },
	           custRegin:{
	        	   model:'BPSPortal.model.CustRegInfo'
	           },
	           dealer: {
	               model: 'BPSPortal.model.DealerInfo'
	           },
	           isshare:{
	        	   model: 'BPSPortal.model.Dictionary',
	 	               autoLoad: true,
	 	               proxy: {
	 	                   type: 'direct',
	 	                   api: {
	 	                	  read: directoryController.findList,
	 	                   },
	 	               extraParams:{
	  	                   	module:'custBPS',
	  	                   	functions:'isshre'
	  	                  },
	 	                   reader: {
	 	                       totalProperty: 'total',
	 	                       rootProperty: 'records'
	 	                   }
	 	               }
	           },
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
	           regstatus:{
	        	   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
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
	           area:{
	        	   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'customer',
  	                   	functions:'area'
  	                  },
 	                   reader: {
 	                       totalProperty: 'total',
 	                       rootProperty: 'records'
 	                   }
 	               }
	           },
	           parenetcompany: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.ParentCustomer'
	           },
	           customers:{
	        	   model: 'BPSPortal.model.Customer',
		               autoLoad: true,
		               proxy: {
		                   type: 'direct',
		                   api: {
		                	   read: customerControl.readcustDTO
		                   },
		                   extraParams:{
	       	                   	isparent:'1'
	       	               },
		                   reader: {
		                       totalProperty: 'total',
		                       rootProperty: 'records'
		                   }
		               }
	           },
	           dealers:{
	        	   model: 'BPSPortal.model.DealerInfos',
 	               autoLoad: true,
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
	           addresstype: {
	        	   model: 'BPSPortal.model.Dictionary',
  	               autoLoad: true,
  	               proxy: {
  	                   type: 'direct',
  	                   api: {
  	                	  read: directoryController.findList,
  	                   },
  	               extraParams:{
   	                   	module:'address',
   	                   	functions:'addtype'
   	                  },
  	                   reader: {
  	                       totalProperty: 'total',
  	                       rootProperty: 'records'
  	                   }
  	               }
	           }
	       }

});