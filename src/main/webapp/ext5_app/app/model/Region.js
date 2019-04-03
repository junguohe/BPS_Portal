
Ext.define('BPSPortal.model.Region', {
    extend: 'Ext.data.Model',
    requires: [
			'Ext.data.field.Field',
			'Ext.data.proxy.Ajax',
			'Ext.data.writer.Json',
			'Ext.data.reader.Json'
           ],
           idProperty: 'id',
		   identifier: 'negative',
           fields: [
               {
                   name:'id'
               },
               {
                   name:'city'
               }
           ],
           proxy: {
			    type: 'direct',
			    api: {
			        read: regioninfocontrol.findpro
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
   
});