//客户管理信息
Ext.define('BPSPortal.model.CustBPSInfo', {
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
                     read: custbpscontrol.readcustDTO,
//                   create: sysUserContro.createUser
               },
               reader: {
                   type: 'json',
                   rootProperty: 'records',
                   totalProperty: 'total'
               }
           }
   
});