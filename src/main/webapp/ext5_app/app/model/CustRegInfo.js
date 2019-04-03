//报备信息
Ext.define('BPSPortal.model.CustRegInfo', {
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
                       read: custRegController.findList,
                       create: custRegController.createCustReg
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});