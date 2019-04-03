
Ext.define('BPSPortal.model.ProjectInfo', {
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
                       name:'projname'
                   },
                   {
                       name:'projtype'
                   }
                   
               ],
               proxy: {
                   type: 'direct',
                   api: {
                	     read: custprojectinfoContro.readDTO
//                       create: sysUserContro.createUser
                   },
                   reader: {
                       type: 'json',
                       rootProperty: 'records',
                       totalProperty: 'total'
                   }
               }
});