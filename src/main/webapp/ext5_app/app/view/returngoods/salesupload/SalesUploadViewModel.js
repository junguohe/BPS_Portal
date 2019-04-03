
Ext.define('BPSPortal.view.returngoods.salesupload.SalesUploadViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.salesupload',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
//	    	   noOrYes:{
//	    		   model: 'BPSPortal.model.Dictionary',
// 	               autoLoad: true,
// 	               proxy: {
// 	                   type: 'direct',
// 	                   api: {
// 	                	  read: directoryController.findList,
// 	                   },
// 	               extraParams:{
//  	                   	module:'all',
//  	                   	functions:'noOrYes'
//  	                  },
// 	                   reader: {
// 	                       totalProperty: 'total',
// 	                       rootProperty: 'records'
// 	                   }
// 	               }
//	    	   },
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
//	    	   dealertaskconfirm:{
//	    		   model: 'BPSPortal.model.Dictionary',
// 	               autoLoad: true,
// 	               proxy: {
// 	                   type: 'direct',
// 	                   api: {
// 	                	  read: directoryController.findList,
// 	                   },
// 	               extraParams:{
//  	                   	module:'upload_taskinfo',
//  	                   	functions:'dealertaskconfirm'
//  	                  },
// 	                   reader: {
// 	                       totalProperty: 'total',
// 	                       rootProperty: 'records'
// 	                   }
// 	               }
//	    	   },
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
//	    	   period:{
//	        	   model: 'BPSPortal.model.Dictionary',
// 	               autoLoad: true,
// 	               proxy: {
// 	                   type: 'direct',
// 	                   api: {
// 	                	  read: directoryController.findList,
// 	                   },
// 	               extraParams:{
//  	                   	module:'upload_period',
//  	                   	functions:'period'
//  	                  },
// 	                   reader: {
// 	                       totalProperty: 'total',
// 	                       rootProperty: 'records'
// 	                   }
// 	               }
//	    	   },
	    	   material: {
	    		   autoLoad: true,
	               model: 'BPSPortal.model.MaterialActiveInfo'
	           },
	           uploadresale:{
	        	   pageSize:10000,
	               model: 'BPSPortal.model.UploadReSale',
	               proxy: {
	                   type: 'direct',
	                   api: {
	                         read: uploadresalecontrol.readerUploadResales
	                   },
	                   reader: {
	                       type: 'json',
	                       rootProperty: 'records',
	                       totalProperty: 'total'
	                   }
	               }
	    	   },
//	    	   uploadresalequery:{
//	        	   pageSize:25,
//	               model: 'BPSPortal.model.UploadReSale',
//	               proxy: {
//	                   type: 'direct',
//	                   api: {
//	                       //  read: uploadresalecontroller.findResaleInfo
//	                   },
//	                   reader: {
//	                       type: 'json',
//	                       rootProperty: 'records',
//	                       totalProperty: 'total'
//	                   }
//	               }
//	    	   },
	    	   uploadtask:{
	    		   pageSize:15,
	    		   model: 'BPSPortal.model.UploadTaskInfo' ,
	               proxy: {
	    			    type: 'direct',
	    			    api: {
	    			        read: UploadTaskInfoController.findTaskOwnerList
	    			    },
	    			    reader: {
	    			        type: 'json',
	    			        rootProperty: 'records',
	    			        totalProperty: 'total'
	    			    }
	    			}
	    	   },
//	    	   taskinfostore:{
//		               model: 'BPSPortal.model.UploadTask',
//		               proxy: {
//		   			    type: 'direct',
////		   			    api: {
////		   			        read: uploadtaskinfocontrol.findInfo
////		   			    },
//		   			    reader: {
//		   			        type: 'json',
//		   			        rootProperty: 'records',
//		   			        totalProperty: 'total'
//		   			    }
//		   			}
//	    	   },
//	    	   area:{
//	        	   model: 'BPSPortal.model.Dictionary',
// 	               autoLoad: true,
// 	               proxy: {
// 	                   type: 'direct',
// 	                   api: {
// 	                	  read: directoryController.findList,
// 	                   },
// 	               extraParams:{
//  	                   	module:'customer',
//  	                   	functions:'area'
//  	                  },
// 	                   reader: {
// 	                       totalProperty: 'total',
// 	                       rootProperty: 'records'
// 	                   }
// 	               }
//	           }
//	    	   
//	    	   
//	           
//	          
	       }

});
