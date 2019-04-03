
Ext.define('BPSPortal.model.PriceStrategyDetail', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'id',
		   identifier: 'negative',
           fields: [
               {
                   name:'id'
               }
           ]
   
});