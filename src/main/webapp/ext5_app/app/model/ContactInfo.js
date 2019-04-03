
Ext.define('BPSPortal.model.ContactInfo', {
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
                         read: contactControl.readcontactDTO,
//                       create: sysUserContro.createUser
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});