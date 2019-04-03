
Ext.define('BPSPortal.view.customer.customerupdate.UpdateStatusWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.updatestatus',

    requires: [
        'BPSPortal.view.customer.customerupdate.UpdateStatusWinViewModel',
        'BPSPortal.view.customer.customerupdate.UpdateStatusWinViewController',
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
        type: 'updatestatus'
    },
    controller: 'updatestatus',
    bodyPadding: 10,
    modal:true,
    closable: true,
    height: 400,
    width: 800,
    title: '',
    listeners: {
  	  afterrender: 'onAfterRender'
    },
    items:[
           {
                xtype: 'gridpanel',
                reference: 'grids',
                minHeight:400,
                bind: {
                    store: '{cust}'
                },
                viewConfig:{
                	enableTextSelection:true
                },
                 columns: [
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'id',
                        hidden:true,
                        text: 'Id'

                    },
                    {
                        xtype: 'gridcolumn',
                        width:150,
                        dataIndex: 'dealername',
                        text: '经销商'
                    },
                    {
                        xtype: 'gridcolumn',
                        width:230,
                        dataIndex: 'custname',
                        text: '客户全称'
                    },
                    
                    {
                        xtype: 'gridcolumn',
                        width:200,
                        dataIndex: 'taxno',
                        text: '税号'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'custcode',
                        text: '客户编号'
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
                                  text: '确定',
                                  listeners: {
                                      click: 'onConfirmClick'
                                  }
                              }
                          ]
                      }
                  ]

               
            }
        ]


});