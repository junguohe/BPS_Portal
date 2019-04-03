
Ext.define('BPSPortal.view.user.UpdateWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.updatewin',

    requires: [
        'BPSPortal.view.user.UpdateWinViewModel',
        'BPSPortal.view.user.UpdateWinViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.view.Table',
        'Ext.selection.CheckboxModel',
        'Ext.grid.column.Widget'
    ],

    viewModel: {
        type: 'updatewin'
    },
    controller: 'updatewin',
    bodyPadding: 10,
    modal:true,
    closable: true,
    minHeight: 250,
    width: 420,
    title: '修改权限',
    listeners: {
    	  afterrender: 'onAfterRender'
      },
    items:[
           {
               xtype: 'gridpanel',
               reference:'roleGrid',
               bind:{
            	   store: '{rolestore}'
               },
               minHeight: 200,
               columns: [

					{
					    xtype: 'checkcolumn',
					    header: '启用？',
					    dataIndex: 'checked',
					    width: 180,
					    stopSelection: false
					},
                   {
                       xtype: 'gridcolumn',
                       dataIndex: 'rolecode',
                       width:200,
                       text: '角色'
                      
                   }
               ],
               dockedItems: [
                     {
                         xtype: 'toolbar',
                         dock: 'bottom',
                         items: [
							{
							    xtype: 'tbfill'
							},	
                             {
                                 xtype: 'button',
                                 text: '提交',
                                 iconCls: 'common_icon_save',
                                 listeners: {
                                     click: 'onSubmitClick'
                                 }
                             }
                         ]
                     }

                 ]
           }
       ]


});