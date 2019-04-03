
Ext.define('BPSPortal.view.customer.reportapprove.ReportApproveViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.reportapprove',
	
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
	    	   province: {
	               model: 'BPSPortal.model.Region'
	           },
	           city: {
	               model: 'BPSPortal.model.City'
	           },
	    	    approveresult:{
	    	    		autoLoad: true,
		        	   model: 'BPSPortal.model.Dictionary',
	 	               proxy: {
	 	                   type: 'direct',
	 	                   api: {
	 	                	  read: directoryController.findList,
	 	                   },
	 	               extraParams:{
	  	                   	module:'custreg',
	  	                   	functions:'approveresult'
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
  	                   	module:'custreg',
  	                   	functions:'regstatus'
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
	           address: {
	               model: 'BPSPortal.model.Address',
	               listeners:{
 	            	  load:'onAddressLoad'
 	              }
	               
	           },
	           customer: {
	               model: 'BPSPortal.model.Customer'
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
	           dealer: {
	               model: 'BPSPortal.model.DealerInfo'
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
	           custreg: {
	       	               model: 'BPSPortal.model.CustRegInfo',
	       	               autoLoad: true,
	       	               proxy: {
			                   type: 'direct',
			                   api: {
			                	   read: custRegController.findList
			                   },
			                   extraParams:{
			                	   approveresult:'0'
		       	               },
			                   reader: {
			                       totalProperty: 'total',
			                       rootProperty: 'records'
			                   }
			               },
	       	               listeners:{
	       	            	  load:'onCustRegLoad'
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