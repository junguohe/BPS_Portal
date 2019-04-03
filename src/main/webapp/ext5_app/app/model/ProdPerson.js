
Ext.define('BPSPortal.model.ProdPerson', {
    extend: 'Ext.data.Model',
   
    requires: [
               'Ext.data.field.Field'
           ],
           idProperty: 'id',
		   identifier: 'negative',
           fields: [
               {
                   name:'id'
               },
               {
            	   name:'area_id'
               },
               {
            	   name:'area'
               },
               {
            	   name:'mgr_id'
               },
               {
            	   name:'mgr'
               },
               {
            	   name:'fae_id'
               },
               {
            	   name:'faemgr'
               }
           ]
   
});