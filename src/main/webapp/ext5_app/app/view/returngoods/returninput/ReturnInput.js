/*
 * File: app/view/BPSPortal/view/returngoods/returninput/ReturnInput.js
 *
 * This file was generated by Sencha Architect version 3.2.0.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 5.1.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 5.1.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('BPSPortal.view.returngoods.returninput.ReturnInput', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.returninput',

    requires: [
        'BPSPortal.view.returngoods.returninput.ReturnInputViewModel',
        'BPSPortal.view.returngoods.returninput.ReturnInputViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.TextArea',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'returninput'
    },
    closable: true,
    scrollable: true,
    title: '返货明细录入',

    items: [
        {
            xtype: 'form',
            height: 400,
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
                            fieldLabel: '审批状态'
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
                            fieldLabel: '经销商'
                        },
                        {
                            xtype: 'combobox',
                            padding: '0 0 0 50',
                            fieldLabel: '会计周期'
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
                            xtype: 'combobox',
                            fieldLabel: '类型'
                        },
                        {
                            xtype: 'combobox',
                            padding: '0 0 0 50',
                            fieldLabel: '返利类型'
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
                            fieldLabel: '文件编号'
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
                            fieldLabel: '金额（未税）'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '金额（含税）'
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
                            fieldLabel: '产品'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '数量'
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
                            fieldLabel: '申请人'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '申请时间'
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