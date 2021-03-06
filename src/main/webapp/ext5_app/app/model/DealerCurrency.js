
Ext.define('BPSPortal.model.DealerCurrency', {
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
                       name:'checked',type:'bool'
                   }
                   
               ],
               proxy: {
                   type: 'direct',
                   api: {
                         read: dealercurrencycontrol.findList
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});