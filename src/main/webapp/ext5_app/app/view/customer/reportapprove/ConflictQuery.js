

Ext.define('BPSPortal.view.customer.reportapprove.ConflictQuery', {
    extend: 'Ext.form.Panel',
    alias: 'widget.conflictQuery',

    requires: [
        'BPSPortal.view.customer.reportapprove.ReportApproveViewModel',
        'BPSPortal.view.customer.reportapprove.ReportApproveViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.grid.Panel',
        'Ext.grid.column.Number',
        'Ext.grid.column.Date',
        'Ext.grid.column.Boolean',
        'Ext.view.Table',
        'Ext.tab.Panel',
        'Ext.tab.Tab'
    ],

    viewModel: {
        type: 'reportapprove'
    },
    controller: 'reportapprove',
    height: 1700,
    scrollable: true,
    width: 997,
    title: '冲突查询',
    closable: true,
    items: [
        {
            xtype: 'gridpanel',
            minHeight:'200',
            title: '冲突查询',
            viewConfig:{
            	enableTextSelection:true
            },
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'String'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'number',
                    text: 'Number'
                },
                {
                    xtype: 'datecolumn',
                    dataIndex: 'date',
                    text: 'Date'
                },
                {
                    xtype: 'booleancolumn',
                    dataIndex: 'bool',
                    text: 'Boolean'
                }
            ]
        },
        {
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
                            text: '取消',
                            listeners: {
                                click: 'onCancelClick'
                            }
                        }
                    ]
                }
            ]
        }
    ]

});