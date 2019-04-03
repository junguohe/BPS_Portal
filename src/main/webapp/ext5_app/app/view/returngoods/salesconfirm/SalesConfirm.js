
Ext.define('BPSPortal.view.returngoods.salesconfirm.SalesConfirm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.salesconfirm',

    requires: [
        'BPSPortal.view.returngoods.salesconfirm.SalesConfirmViewModel',
        'BPSPortal.view.returngoods.salesconfirm.SalesConfirmViewController',
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
        type: 'salesconfirm'
    },
    listeners: {
    	  afterrender: 'onAfterRenderW'
      },
    controller: 'salesconfirm',
    title: '经销商销售接口数据确认',
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
						    xtype: 'combobox',
						    hidden:true,
						    fieldLabel: '会计周期',
						    displayField: 'code',
						    valueField: 'value',
						    reference:'periods',
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
                            xtype: 'textfield',
                            fieldLabel: '经销商',
                            name:'dealername'
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
						    fieldLabel: '确认状态',
						   // emptyText: "请选择",
						    value:0,
						    editable:false,
						    displayField: 'code',
						    valueField: 'value',
						    name: 'taskconfirm',
						    bind: {
						        store: '{taskconfirm}'
						
						    }
						
						},
                        {
                            xtype: 'textfield',
                            fieldLabel: '备注',
                            name:'remark'
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
                            text: '查询',
                            itemId: 'searchBtn',
                            iconCls: 'common-icon-serach',
                            listeners: {
                                click: 'onSearchClick'
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'gridpanel',
            height: 400,
            title: '查询结果',
            reference:'taskGrid',
            bind:{
            	store: '{taskinfo}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            plugins: [
                      {
                          ptype: 'cellediting',
                          clicksToEdit:1
                      }
                    ],
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealercode',
                    text: '经销商编码'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealername',
                    text: '经销商名称'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'period',
                    text: '会计周期'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'taskseq',
                    width:200,
                    text: '  上传序列号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'createdate',
                    text: '销售数据上传时间',
                    width:200,
                    renderer: function (val) {
                    	var result="";
                    	if (val != 'null'&& val!=null) {
                            result=val; 
                        }else{
                        	result="";
                        }              
                        return result;
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'taskconfirm',
                    text: '  确认状态',
                    renderer: function(value){
				        var result="";
				        if(value=="1"){
				            result="已确认";
				        }else if(value=="2"){
				        	result="未通过";
				        }else if(value=="9"){
				        	result="草稿";
				        }else{
				        	result="待确认";
				        }
				        return result;
				    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'updator',
                    text: '审批人'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'updatedate',
                    text: '审批时间',
                    width:200,
                    renderer: function (val) {
                    	var result="";
                    	if (val != 'null'&& val!=null) {
                            result=val; 
                        }else{
                        	result="";
                        }              
                        return result;
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'remark',
                    width:200,
                    text: '备注',
                    editor: {
                        xtype: 'textfield',
                        name: 'remark'
                     }
                },
                {
                    xtype: 'widgetcolumn',
                    text: '操作',
                    widget: {
                        xtype: 'button',
                        style: {
                        	background: 'white',
                        	border: 0
                        },
                        iconCls:'common-icon-user-sure',
                        text: '<font color ="black">保存备注</font>',
                        listeners: {
                            click: 'onSaveRemarkClick'
                        }
                    }
                }
            ],
			
            dockedItems: [
                 {
				    xtype: 'pagingtoolbar',
				    dock: 'bottom',
				    bind: {
				        store: '{taskinfo}'
				    },
				    displayInfo: true
				},
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            iconCls: 'common-icon-show',
                            text: '验证',
                            listeners: {
                                click: 'onValidateClick'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common-icon-show',
                            text: '明细',
                            listeners: {
                                click: 'onDetailClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '驳回',
                            iconCls:'common-icon-delete',
                            listeners:{
                            	click:'onRejectClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '删除',
                            hidden:true,
                            iconCls:'common-icon-delete',
                            listeners:{
                            	click:'onDeleteClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '导出',
                            iconCls:'common-icon-down',
                            listeners:{
                            	click:'onExportClicks'
                            }
                        }
                    ]
                }
            ]
        }
    ]

});