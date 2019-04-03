
Ext.define('BPSPortal.model.Customer', {
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
			        read: customerControl.readcustDTO,
			        create: customerControl.createCustomer
			    },
			    reader: {
			        type: 'json',
			        rootProperty: 'records',
			        totalProperty: 'total'
			    }
			}
});