
Ext.define('BPSPortal.model.CustomerBatch', {
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
                       name:'seq'
                   },
                   {
                       name:'dealerName'
                   },
                   {
                       name:'customerCode'
                   },
                   {
                       name:'prodCode'
                   },
                   {
                       name:'type'
                   },
                   {
                       name:'regDate'
                   }
                   ,
                   {
                       name:'regDate'
                   }
               ]
});