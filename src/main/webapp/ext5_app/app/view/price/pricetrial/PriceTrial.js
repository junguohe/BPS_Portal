Ext.define('BPSPortal.view.price.pricetrial.PriceTrial', {
    extend: 'Ext.form.Panel',
    alias: 'widget.pricetrial',

    requires: [
        'BPSPortal.view.price.pricetrial.PriceTrialViewModel',
        'BPSPortal.view.price.pricetrial.PriceTrialViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Checkbox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'pricetrial'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
  	  afterrender: 'onAfterRender'
    },
    controller: 'pricetrial',
    title: '调价试算',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'panel',
            height: 200,
            bodyPadding: 10,
            title: '试算条件',
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
                            fieldLabel: '经销商',
                            emptyText: "全部",
                            displayField: 'dealername',
                            valueField: 'dealername',
                            name: 'dealername',
                            bind: {
                                store: '{dealer}'

                            }

                        },
                        {
						       xtype:'label',
						       padding: '0 0 0 50',
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
						    reference:'years',
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
						    reference:'months',
						    name: 'month',
						    bind: {
						        store: '{month}'
						    }
						},
                        {
                        	padding: '0 0 0 50',
                            xtype: 'textfield',
                            hidden:true,
                            fieldLabel: '会计期间'
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
                            fieldLabel: '价格策略A',
                            name:'pricea'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '价格策略B',
                            name:'priceb'
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
                            text: '计算',
                            iconCls:'common_icon_jisuan',
                            listeners: {
                                click: 'onTrailClick'
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'gridpanel',
            title: '试算结果',
            viewConfig:{
            	enableTextSelection:true
            },
            reference:'trialGrid',
            bind:{
            	store:'{trial}'
            },
          //  renderTo: document.body,
            features: [{
                ftype: 'summary'
            }],
            minHeight:300,
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealername',
                    text: '经销商'
                },

                {
                    xtype: 'numbercolumn',
                    dataIndex: 'diff',
                    format:'000,000',
                    align:'right',
                    text: '价差'
                    	,
                    summaryType: 'sum',
                    summaryRenderer : function(value){
                    	return  '合计 :' + value;  
                    }  
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            text: '导出',
                            iconCls:'common-icon-down',
                            listeners:{
                            	click:'onDownloadClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '明细',
                            iconCls:'common_icon_jisuan',
                            listeners:{
                            	click:'onDetailClick'
                            }
                        }
                    ]
                }
            ]
        }
    ]

});