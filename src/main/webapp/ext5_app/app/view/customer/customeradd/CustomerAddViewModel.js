
Ext.define('BPSPortal.view.customer.customeradd.CustomerAddViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.customeradd',
	requires: [
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
	    	   material: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.MaterialInfo'
	           },
	           parenetcompany: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.ParentCustomer'
	           },
	           address: {
	               model: 'BPSPortal.model.Address',
	               listeners:{
    	            	  load:'onCustRegLoad'
    	              }
	           },
	           province: {
	               model: 'BPSPortal.model.Region'
	           },
	           city: {
	               model: 'BPSPortal.model.City'
	           },
	           custBPS: {
	               model: 'BPSPortal.model.CustBPSInfo'
	           },
	           custRegin:{
	        	   model:'BPSPortal.model.CustRegInfo'
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
	           contact: {
	               model: 'BPSPortal.model.ContactInfo'
	           },
	           project: {
	               model: 'BPSPortal.model.ProjectInfo'
	           },
	           customer:{
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
	           addtype:{
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
	           }
	       }

});