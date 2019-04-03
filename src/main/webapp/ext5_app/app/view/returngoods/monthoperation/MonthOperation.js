
Ext.define('BPSPortal.view.returngoods.monthoperation.MonthOperation', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monthoperation',

    requires: [
        'BPSPortal.view.returngoods.monthoperation.MonthOperationViewModel',
        'BPSPortal.view.returngoods.monthoperation.MonthOperationViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'monthoperation'
    },
    controller: 'monthoperation',
    listeners : {
		afterrender : 'onAfterRender'
	},
    title: '月结操作',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'form',
            height: 168,
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
						    xtype: 'combobox',
						    hidden:true,
						    fieldLabel: '会计周期',
						    displayField: 'code',
						    valueField: 'value',
						    name: 'period',
						    bind: {
						        store: '{period}'
						    }
						},
						{
						       xtype:'label',
						   	   text:'会计周期'
						},
						{
						    xtype: 'combobox',
						    padding: '0 0 0 30 ',
						    fieldLabel: '年',
						    width: 100,
						    labelWidth: 30,
						    displayField: 'code',
						    valueField: 'value',
						    name: 'year',
						    bind: {
						        store: '{year}'
						    }
						},
						{
						    xtype: 'combobox',
						    width: 100,
						    labelWidth: 30,
						    fieldLabel: '月',
						    displayField: 'code',
						    valueField: 'value',
						    name: 'month',
						    bind: {
						        store: '{month}'
						    }
						},
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
                            fieldLabel: '操作人',
                            name:'creator',
                            readOnly:true,
                            reference:'creators'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 50',
                            format: 'Y-m-d',
                            value:new Date(),
                        	readOnly:true,
                            fieldLabel: '操作时间'
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
                            text: 'Close',
                            listeners: {
                                click: 'onClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: 'Open',
                            listeners: {
                                click: 'onClick'
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'gridpanel',
            title: '月结历史',
            bind:{
            	store: '{monthstore}'
            },
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'period',
                    text: '会计周期'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'updator',
                    text: '操作人'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'active',
                    text: '状态',
                    renderer: function (val) {
                    	var result="";
                    	if (val==1) {
                            result='生效'; 
                        }else{
                        	result="关闭";
                        }              
                        return result;
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'updatedate',
                    text: '操作时间',
                    renderer: function (val) {
                    	var result="";
                    	if (val != 'null'&& val!=null) {
                            result=val; 
                        }else{
                        	result="";
                        }              
                        return result;
                    }
                }
            ],
            dockedItems: [
            				{
            				    xtype: 'pagingtoolbar',
            				    dock: 'bottom',
            				    bind: {
            				        store: '{monthstore}'
            				    },
            				    displayInfo: true
            				}
            	        ]
        }
    ]

});