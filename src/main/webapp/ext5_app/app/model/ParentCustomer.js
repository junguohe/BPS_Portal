
Ext.define('BPSPortal.model.ParentCustomer', {
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
                   }
                   
               ],
			proxy: {
			    type: 'direct',
			    api: {
			        read: customerControl.readerParentCustomer
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
});