
Ext.define('BPSPortal.model.BPSInfo', {
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
			        read: adduserscontrol.readUserBPSs
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});