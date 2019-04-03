
Ext.define('BPSPortal.model.UploadInventory', {
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
                       name:'dealername'
                   },
                   {
                       name:'materialname'
                   },
                   {
                       name:'materialcode'
                   },
                   {
                       name:'period'
                   },
                   {
                       name:'remark'
                   }
                   
               ],
               proxy: {
                   type: 'direct',
                   api: {
                         read: uploadinventorycontroller.findList
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});