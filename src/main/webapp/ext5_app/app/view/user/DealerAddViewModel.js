
Ext.define('BPSPortal.view.user.DealerAddViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.dealeradd',
	requires: [
	           'Ext.data.Store'
	       ],
    stores: {
    	dealername:{
    		model: 'BPSPortal.model.DealarV'
    	},
 	   currencytype:{
		   model: 'BPSPortal.model.DealerCurrency'
	   },
    	addDealer: {
	       model: 'BPSPortal.model.AddDealer',
	       pageSize: 15,
	       autoLoad: true
	   },
	   prodlinetype: {
           model: 'BPSPortal.model.DealerProdLine'
       },
       dealerregnum:{
    	   model: 'BPSPortal.model.DealerRegNum'
       }
    }
});