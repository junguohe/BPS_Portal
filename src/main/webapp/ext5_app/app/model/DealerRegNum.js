
Ext.define('BPSPortal.model.DealerRegNum', {
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
                       name:'dealerid'
                   },
                   {
                	   name:'prodid'
                   },
                   {
                	   name:'regmax',type:'int'
                   },
                   {
                	   name:'active'
                   }
                   
               ],
               proxy: {
                   type: 'direct',
                   api: {
                         read: dealerprodlinecontrol.findDealerRegList
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});