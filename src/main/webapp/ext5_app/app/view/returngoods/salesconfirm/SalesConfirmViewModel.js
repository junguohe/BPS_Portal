
Ext.define('BPSPortal.view.returngoods.salesconfirm.SalesConfirmViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.salesconfirm',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
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
	    	   period:{
	        	   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'upload_period',
  	                   	functions:'period'
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
	    	   taskconfirm: {
	        	   model: 'BPSPortal.model.Dictionary',
 	               autoLoad: true,
 	               proxy: {
 	                   type: 'direct',
 	                   api: {
 	                	  read: directoryController.findList,
 	                   },
 	               extraParams:{
  	                   	module:'upload_taskinfo',
  	                   	functions:'taskconfirm'
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
	    	   taskinfo:{
	    		   pageSize:15,
	    		   model: 'BPSPortal.model.UploadTaskInfo',
	               proxy: {
	    			    type: 'direct',
	    			    api: {
	    			        read: UploadTaskInfoController.findList
	    			    },
	    			    reader: {
	    			        type: 'json',
	    			        rootProperty: 'records',
	    			        totalProperty: 'total'
	    			    }
	    			}
	    	   },
	    	   uploadresale:{
	    		   pageSize:10000,
	    		   model: 'BPSPortal.model.UploadReSale'
	    	   }
	    	   
	    	   
	           
	          
	       }

});
