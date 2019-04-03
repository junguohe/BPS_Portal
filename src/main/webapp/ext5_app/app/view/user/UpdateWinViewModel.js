
Ext.define('BPSPortal.view.user.UpdateWinViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.updatewin',
	
	requires: [
	           'Ext.data.Store'
	       ],

	       stores: {
	    	   rolestore:{
	    		   model:'BPSPortal.model.BPSRole',
	               proxy: {
	   			    type: 'direct',
	   			    api: {
	   			        read: bpsrolecontrol.findList
	   			    },
	   			    reader: {
	   			        type: 'json',
	   			        rootProperty: 'records',
	   			        totalProperty: 'total'
	   			    }
	   			}
	    	   }
	       }


});