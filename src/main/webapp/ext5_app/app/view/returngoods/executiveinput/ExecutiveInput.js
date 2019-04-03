
Ext.define('BPSPortal.view.returngoods.executiveinput.ExecutiveInput', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.executiveinput',

    requires: [
        'BPSPortal.view.returngoods.executiveinput.ExecutiveInputViewModel',
        'BPSPortal.view.returngoods.executiveinput.ExecutiveInputViewController', 
        'Ext.form.Panel',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button'
    ],

    viewModel: {
        type: 'executiveinput'
    },
    controller: 'executiveinput',
    title: '执行明细录入',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'form',
            height: 300,
            bodyPadding: 10,
            title: '',
            items: [
                {
                    xtype: 'container',
                    padding: '10 0 0 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '单据编号'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '经销商 '
                        }
                    ]
                },
                {
                    xtype: 'container',
                    padding: '10 0 0 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '返利金额'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '可执行金额'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    padding: '10 0 0 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '执行编码'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 50',
                            fieldLabel: '执行日期'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    padding: '10 0 0 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '执行（含税)'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '执行（未税)'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    padding: '10 0 0 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'textareafield',
                            flex: 1,
                            fieldLabel: '备注'
                        }
                    ]
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    border:false,
                    items: [
                        {
                            xtype: 'button',
                            text: '提交'
                        }
                    ]
                }
            ]
        }
    ]

});