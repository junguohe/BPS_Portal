

Ext.define('BPSPortal.view.customer.reportapprove.ReportApprove', {
    extend: 'Ext.form.Panel',
    alias: 'widget.reportapprove',

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
    title: '报备审批',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
    	  afterrender: 'onAfterRenderW'
      },
    closable: true,
    items: [
        {
            xtype: 'form',
            height: 200,
            bodyPadding: 10,
           // layout: 'column',
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
                            fieldLabel: '客户编号/名称',
                            name:'name'
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '经销商',
                            padding: '0 0 0 50',
                            name:'dealername'
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: '产品线',
                            padding: '0 0 0 50',
                            emptyText: "请选择",
                            displayField: 'prodname',
                            valueField: 'prodid',
                            name: 'prodid',
                            bind: {
                                store: '{prodline}'

                            }

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
                            xtype: 'datefield',
                            fieldLabel: '申请时间自',
                            format: 'Y-m-d',
                            reference:'data_time_f',
                            name:'startdate'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 50',
                            fieldLabel: '至',
                            format: 'Y-m-d',
                            name:'enddate'
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: '报备结果',
                            padding: '0 0 0 50',
                            emptyText: "请选择",
                            displayField: 'code',
                            valueField: 'value',
						    value:'0',
                            name: 'approveresult',
                            bind: {
                                store: '{approveresult}'
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
            minHeight:'500',
            padding:'10 0 0 0 ',
            reference:'custregGrid',
            bind:{
            	store: '{custreg}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            title: '  报备列表',
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'ct',
                    text: '冲突'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'approveresult',
                    width:80,
                    text: '状态',
                    renderer: function(value){
	                    var result="";
	                    if(value=="3"){
	                        result="审批驳回";
	                        
	                    }else if(value=="1"){
	                        result="待处理";
	                        
	                    }else if(value=="2"){
	                    	result="审批通过";
                        
	                    }else {
	                        result="未处理";
	                    }
	                    return result;
	                }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealername',
                    text: '经销商'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'region',
                    text: '区域'
                },
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'prodname',
                    width:60,
                    text: '产品线'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'createdate',
                    width:150,
                    text: '报备时间'
                },
                {
                    xtype: 'gridcolumn',
                    hidden:true,
                    dataIndex: 'dealerid',
                    text: 'dealerid'
                },
                {
                    xtype: 'gridcolumn',
                    hidden:true,
                    dataIndex: 'prodid',
                    text: 'prodid'
                },
                {
                    xtype: 'gridcolumn',
                    hidden:true,
                    dataIndex: 'parenetcompany',
                    text: 'parenetcompany'
                },
                {
                    xtype: 'gridcolumn',
                    hidden:true,
                    dataIndex: 'region',
                    text: 'region'
                },
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custname',
                    width:230,
                    text: '客户名称'
                },
                
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'taxno',
                    width:150,
                    text: '税号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'address',
                    width:300,
                    text: '地址'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    hidden:true,
                    text: '可报备客户数'
                },
               
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'telno',
                    width:150,
                    text: '联系人电话'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'approveremark',
                    width:200,
                    text: '审批意见'
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
                        iconCls:'common-icon-serach',
                        text: '<font color ="black">冲突查询</font>',
                        listeners: {
                            click: 'onConfilctQueryClick'
                        }
                    }
                }
            ],
            selModel: {
                selType: 'checkboxmodel'
            },
            dockedItems: [
                          {
         				    xtype: 'pagingtoolbar',
         				    dock: 'bottom',
         				    bind: {
         				        store: '{custreg}'
         				    },
         				    displayInfo: true
         				},
                         {
                             xtype: 'toolbar',
                             dock: 'top',
                             items: [
                                 {
                                     xtype: 'button',
                                     text: '审批通过',
                                     iconCls: 'common_icon_approve',
                                     listeners: {
                                         click: 'onThroughClick'
                                     }
                                 },
                                 {
                                     xtype: 'button',
                                     text: '审批驳回',
                                     iconCls: 'common_icon_approve',
                                     listeners: {
                                         click: 'onRejectClick'
                                     }
                                 },
                                 {
                                     xtype: 'button',
                                     text: '数据修改',
                                     iconCls: 'common_icon_update',
                                     listeners: {
                                         click: 'onUpdateStatusClick'
                                     }
                                 },
                                 {
                                     xtype: 'button',
                                     text: '明细',
                                     iconCls: 'common-icon-show',
                                     listeners: {
                                         click: 'onDetailClick'
                                     }
                                 },
                                 {
                                     xtype: 'button',
                                     text: '待处理',
                                     iconCls: 'common_icon_approve',
                                     listeners: {
                                         click: 'onUpdateClick'
                                     }
                                 },
                             ]
                         }
                     ]
        }
    ]

});