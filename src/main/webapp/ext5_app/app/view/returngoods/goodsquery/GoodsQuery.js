/*
 * File: app/view/DealerPortal/view/returngoods/goodsquery/GoodsQuery.js
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

Ext.define('BPSPortal.view.returngoods.goodsquery.GoodsQuery', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.goodsquery',

    requires: [
        'BPSPortal.view.returngoods.goodsquery.GoodsQueryViewModel',
        'BPSPortal.view.returngoods.goodsquery.GoodsQueryViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'goodsquery'
    },
    title: '返货查询',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'form',
            height: 201,
            bodyPadding: 10,
            title: '查询条件',
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
                            fieldLabel: '执行情况'
                        },
                        {
                            xtype: 'combobox',
                            padding: '0 0 0 50',
                            fieldLabel: '经销商确认状态（特价/调价）'
                        }
                    ]
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        {
                            xtype: 'button',
                            text: '查询'
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'gridpanel',
            height: 167,
            //width: 1029,
            title: '查询结果',
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: '经销商'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'number',
                    text: '年'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'date',
                    text: '月'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '  上月未返金额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '本月返货金额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '  本月可返货金额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '本月已返货金额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '本月未返货金额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '  本月未返金额（含税）'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '操作'
                }
            ],
            viewConfig: {
                height: 131
            }
        },
        {
            xtype: 'gridpanel',
            height: 182,
            //width: 1001,
            title: '返货明细信息',
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: '  单据编号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'number',
                    text: '日期'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'date',
                    text: '经销商'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '客户'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '返货类型'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '金额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '数量'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '  产品型号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '  产品名称'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '文件编号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '执行状态'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '备注'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '操作'
                }
            ],
            viewConfig: {
                width: 783
            }
        },
        {
            xtype: 'gridpanel',
            title: '执行明细',
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: '经销商'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'number',
                    text: '明细'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'date',
                    text: '含税'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '未税'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bool',
                    text: '执行日期'
                }
            ]
        }
    ]

});