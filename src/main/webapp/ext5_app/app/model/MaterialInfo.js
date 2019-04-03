
Ext.define('BPSPortal.model.MaterialInfo', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'id',
		   identifier: 'negative',
           fields: [
               {
                   name:'id'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			        read: materialInfoControl.getAllList
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});