
Ext.define('BPSPortal.model.Dictionary', {
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
                   }
                   
               ],
               proxy: {
                   type: 'direct',
                   api: {
                     read: directoryController.findList,
//                       create: sysUserContro.createUser
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});