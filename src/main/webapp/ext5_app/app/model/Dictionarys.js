
Ext.define('BPSPortal.model.Dictionarys', {
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
                   
               ],
               proxy: {
                   type: 'direct',
                   api: {
                     read: directoryController.findDictionary
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});