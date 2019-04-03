
Ext.define('BPSPortal.view.systemadministration.city.UpdateCityViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.city',
    
	requires: [
	           'Ext.data.Store'
	       ],
    stores: {
    	region:{
    		model: 'BPSPortal.model.Regions',
    		autoLoad: true
    	}
    }
});