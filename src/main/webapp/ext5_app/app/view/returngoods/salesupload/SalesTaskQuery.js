
Ext.define('BPSPortal.view.returngoods.salesupload.SalesTaskQuery', {
    extend: 'Ext.form.Panel',
    alias: 'widget.salestaskquery',

    requires: [
        'BPSPortal.view.returngoods.salesupload.SalesUploadViewModel',
        'BPSPortal.view.returngoods.salesupload.SalesUploadViewController',
        'Ext.form.Panel',
        'Ext.form.Label',
        'Ext.button.Button',
        'Ext.form.field.File',
        'Ext.form.field.Date',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'salesupload'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
    	  afterrender: 'onAfterRender'
      },
    controller : 'salesupload',
    title: '销售数据查询',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'form',
            itemId:'uploadForm',
            height: 180,
            bodyPadding: 10,
            title: '查询条件',
            items: [
                    {
                        xtype: 'container',
                        padding: '0 0 10 0 ',
                        layout: 'table',
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
	                            xtype: 'combobox',
	                            fieldLabel: '批次号',
	                            emptyText: "请选择",
	                            width: 350,
	                            labelWidth: 100,
	                            displayField: 'taskseq',
	                            valueField: 'id',
	                            name: 'taskseq',
	                            bind: {
	                                store: '{uploadtask}'
	                            }
							}
                        ]
                    },
                    {
                        xtype: 'container',
                        padding: '0 0 10 0 ',
                        layout: 'table',
                        items: [
							{
	                            xtype: 'combobox',
	                            fieldLabel: '状态',
	                            emptyText: "请选择",
	                            displayField: 'code',
	                            valueField: 'value',
	                            name: 'taskconfirm',
	                            bind: {
	                                store: '{dealertaskconfirm}'
	                            }
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
                             iconCls: 'common-icon-serach',
                             text: '查询',
                             itemId: 'searchBtn',
                             listeners: {
                                 click: 'onSearchResaleClick'
                             }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common-icon-delete',
                            text: '  删除',
                            listeners:{
                            	click:'onDeleteClicks'
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
                            text: '导出',
                            iconCls:'common-icon-down',
                            listeners:{
                            	click:'onExportClicks'
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'gridpanel',
            height: 500,
            title: '',
            reference:'uploadresalequerygrid',
            bind:{
            	store: '{taskinfostore}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            columns: [

				{
				    xtype: 'gridcolumn',
				    dataIndex: 'id',
				    hidden:true,
				    text: 'id'
				},
				{
					xtype : 'gridcolumn',
					dataIndex : 'period',
					text : '会计周期'
				}, 
				{
					xtype : 'gridcolumn',
					dataIndex : 'taskseq',
					width:300,
					text : '批次号'
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
                    dataIndex: 'remark',
                    width:150,
                    text: '备注'
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
                }
            ],
            dockedItems: [
          				{
          				    xtype: 'pagingtoolbar',
          				    dock: 'bottom',
          				    bind: {
          				        store: '{taskinfostore}'
          				    },
          				    displayInfo: true
          				}
          	        ]
        }
    ]

});