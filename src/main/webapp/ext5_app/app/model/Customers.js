
Ext.define('BPSPortal.model.Customers', {
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
                       name:'CustCode'
                   },
                   {
                       name:'CustName'
                   },
                   {
                	   name:'isparent',type:'bool'
                   }
                   
               ],



			proxy: {
			    type: 'direct',
			    api: {
			        read: custRegController.readcustDTOs
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
});