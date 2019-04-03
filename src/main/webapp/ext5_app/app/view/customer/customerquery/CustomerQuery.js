
Ext.define('BPSPortal.view.customer.customerquery.CustomerQuery', {
    extend: 'Ext.form.Panel',
    alias: 'widget.customerquery',

    requires: [
        'BPSPortal.view.customer.customerquery.CustomerQueryViewModel',
        'BPSPortal.view.customer.customerquery.CustomerQueryViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
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
        type: 'customerquery'
    },
    controller: 'customerquery',
    scrollable: true,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
  	  afterrender: 'onAfterRenderW'
    },
    title: '客户查询',
    closable: true,
    items: [
        {
            xtype: 'form',
            height: 270,
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
                            fieldLabel: '客户编号/名称',
                            name:'name',
                            reference:'custname'
                            	
                        },
                        {
						    xtype: 'combobox',
						    fieldLabel: '区域',
                            padding: '0 0 0 50',
						    emptyText: "请选择",
						    displayField: 'code',
						    valueField: 'value',
						    name: 'region',
						    bind: {
						        store: '{area}'
						
						    }
						
						},
						 {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '客户电话/手机',
                            name:'telno'
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
                            fieldLabel: '产品线',
                            emptyText: "请选择",
                            displayField: 'prodname',
                            valueField: 'prodid',
                            name: 'prodid',
                            bind: {
                                store: '{prodline}'

                            }

                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: '客户状态',
                            padding: '0 0 0 50',
                            emptyText: "请选择",
                            displayField: 'code',
                            valueField: 'value',
                            value:'1',
                            name: 'regstatus',
                            bind: {
                                store: '{regstatus}'

                            }

                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '经销商',
                            padding: '0 0 0 50',
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
                            fieldLabel: '共营',
                            emptyText: "请选择",
                            displayField: 'code',
                            valueField: 'value',
                            name: 'isshare',
                            bind: {
                                store: '{isshare}'

                            }

                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'BPS销售',
                            padding: '0 0 0 50',
                            name:'bpssales'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: 'BPS FAE',
                            name:'bpsfae'
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
							    xtype: 'customerpickers',
							    fieldLabel: '母公司',
							    displayField: 'custname',
							    valueField: 'id',
							    name: 'parenetcompany'
							
							},
            				 {
                                xtype: 'textfield',
                                padding: '0 0 0 50',
                                fieldLabel: '地址',
                                name:'address'
                            },
                            {
                            	xtype: 'combobox',
                            	fieldLabel: '客户分类',
                                padding: '0 0 0 50',
 		                      	bind: {
 		                      		store: '{custtype}'
 		                      	},
 		                      	displayField: 'value',
 		                      	valueField: 'code',
 		                      	name:'custtype'
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
						    fieldLabel: '税号',
						    name:'taxno'
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
            title: '查询结果',
            minHeight:500,
            reference:'customerGrid',
            bind:{
            	store: '{customer}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            columns: [
				{
				    xtype: 'gridcolumn',
				    dataIndex: 'regstatus',
				    text: '客户状态',
				    renderer: function(value){
				        var result="";
				        if(value=="1"){
				            result="报备客户";
				        }else{
				        	result="非报备客户";
				        }
				        return result;
				    }
				},
				{
                    xtype: 'gridcolumn',
                    dataIndex: 'dealername',
                    width:100,
                    text: '经销商 '
                },
                {
                    xtype: 'gridcolumn',
                     dataIndex: 'prodname',
                     width:100,
                    text: '产品线 '
                },
                {
                    xtype: 'gridcolumn',
                     dataIndex: 'region',
                     width:100,
                    text: '区域 '
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custcode',
                    width:100,
                    text: '客户编号 '
                },
                {
                    xtype: 'gridcolumn',
                    hidden:true,
                    dataIndex: 'dealerid',
                    text: 'dealerid '
                },
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custname',
                    width:230,
                    text: '客户名称 '
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'address',
                    width:350,
                    text: '客户地址 '
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'isbps',
                    hidden:true,
                    text: 'isbps'
                },
                {
                    xtype: 'gridcolumn',
                    width:150,
                    hidden:true,
                    dataIndex: 'approveremark',
                    text: '审批意见'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'regstartdate',
                    width:150,
                    text: '报备生效期 ',
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
				        store: '{customer}'
				    },
				    displayInfo: true
				},
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            text: '新增',
                            iconCls: 'common-icon-add',
                            listeners: {
                                click: 'onAddCusClice'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common-icon-show',
                            text: '明细',
                            listeners: {
                                click: 'onSeeCustClick'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common_icon_reg',
                            hidden:true,
                            text: '报备',
                            listeners: {
                                click: 'onRegCustClick'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common_icon_update',
                            text: '修改',
                            listeners: {
                                click: 'onUpdateCustClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '导出',
                            iconCls:'common-icon-down',
                            listeners:{
                            	click:'onDownloadClick'
                            }
                        }
                    ]
                }
            ]
        }
    ]

});