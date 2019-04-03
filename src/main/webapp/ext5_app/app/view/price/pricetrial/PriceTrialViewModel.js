
Ext.define('BPSPortal.view.price.pricetrial.PriceTrialViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.pricetrial',
	
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
	    	   trial:{
	    		   model: 'BPSPortal.model.Trial',
	    		   pageSize:15,
	    		   proxy: {
	   			    type: 'direct',
	   			    api: {
	   			        read: pricestrategydetailcontroller.pricediff
	   			    },
	   			    reader: {
	   			        type: 'json',
	   			        rootProperty: 'records',
	   			        totalProperty: 'total'
	   			    }
	   			}
	    	   },
	           dealer: {
	               model: 'BPSPortal.model.DealerInfos'
	           },
	           material: {
	               model: 'BPSPortal.model.MaterialInfo'
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
	           pricetrialdetail:{
	    		   model: 'BPSPortal.model.Trial',
	    		   proxy: {
	   			    type: 'direct',
	   			    api: {
	   			        read: pricestrategydetailcontroller.readerPriceDiffInfo
	   			    },
	   			    reader: {
	   			        type: 'json',
	   			        rootProperty: 'records',
	   			        totalProperty: 'total'
	   			    }
	   			}
	    	   },

	       }

});