
Ext.define('BPSPortal.view.customer.customerupdate.CustomerUpdate', {
    extend: 'Ext.form.Panel',
    alias: 'widget.customerupdate',

    requires: [
        'BPSPortal.view.customer.customerupdate.CustomerUpdateViewModel',
        'BPSPortal.view.customer.customerupdate.CustomerUpdateViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.view.Table',
        'Ext.selection.CheckboxModel',
        'Ext.grid.column.Widget'
    ],

    viewModel: {
        type: 'customerupdate'
    },
    listeners: {
    	  afterrender: 'onAfterRenderW'
    },
    controller: 'customerupdate',
    scrollable: true,
    scrollable: true,
    width: 997,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    title: '批量修改客户信息',
    closable: true,
    items: [
        {
            xtype: 'form',
            height: 300,
            //layout: 'column',
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
                            fieldLabel: '经销商',
                            name:'dealername'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '客户',
                            name:'name'
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: '产品线',
                            padding: '0 0 0 50',
                            editable:false,
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
                    padding: '20 0 0 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'datefield',
                            fieldLabel: '  Resales统计  时间自'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 50',
                            fieldLabel: '至'
                        },
                        {
                            xtype: 'combobox',
                            editable:false,
                            fieldLabel: '客户状态',
                            padding: '0 0 0 50',
                            emptyText: "请选择",
                            displayField: 'code',
                            valueField: 'value',
                            name: 'regstatus',
                            bind: {
                                store: '{regstatus}'

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
                            xtype: 'textfield',
                            fieldLabel: '销量'
                        },
                        {
                            xtype: 'combobox',
                            padding: '0 0 0 50',
                            fieldLabel: '--------'
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'BPS 销售',
                            padding: '0 0 0 50',
                            name:'bpssales'
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
                            fieldLabel: '销售额'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '--------'
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
                            xtype: 'datefield',
                            fieldLabel: '最晚报备日',
                            format: 'Y-m-d',
                            reference:'data_time_f',
                            name:'bestlatertime'
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
            minHeight: 500,
            title: '查询结果',
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
                    text: '经销商'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'prodname',
                    text: '产品线'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custcode',
                    width:80,
                    text: '客户编码'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custname',
                    width:230,
                    text: '客户名称'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custregStatus',
                    hidden:true,
                    text: 'custregStatus'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bpssales',
                    text: '销售',
                    renderer : function(value) {
						if (value != null&&value !=''&&value !=-1&&value !=undefined) {
							var gridStore = this.up('form').getViewModel().getStore('person');
							if(gridStore.findRecord('empid',value)==null){
				            	return "" ;
							}else{
								return gridStore.findRecord('empid',value,0, false, false, true).data.per_name;
							}
						}else{
							return "";
						}
					}
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'bpsfae',
                    text: 'FAE',
                    renderer : function(value) {
						if (value != null&&value !=''&&value !=-1&&value !=undefined) {
							var gridStore = this.up('form').getViewModel().getStore('person');
							if(gridStore.findRecord('empid',value)==null){
				            	return "" ;
							}else{
								return gridStore.findRecord('empid',value,0, false, false, true).data.per_name;
							}
						}else{
							return "";
						}
					}
                },
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'regstartdate',
                    width:150,
                    text: '报备生效日期',
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
                    xtype: 'widgetcolumn',
                    width:150,
                    text: '操作',
                    widget: {
                        xtype: 'button',
                        style: {
                        	background: 'white',
                        	border: 0
                        },
                        iconCls:'common_icon_update',
                        text: '<font color ="black">报备状态修改</font>',
                        listeners: {
                            click: 'onUpdateStatue'
                        }
                        	
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
                            text: '批量转移',
                            iconCls: 'common_icon_update',
                            listeners: {
                                click: 'onTransferClick'
                            }
                            
                        },
                        {
                            xtype: 'button',
                            text: '批量Open',
                            iconCls: 'common_icon_update',
                            listeners: {
                                click: 'onCancelClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '延长报备生效日期',
                            iconCls: 'common_icon_update',
                            listeners: {
                                click: 'onUploadRegDateClick'
                            }
                        }
                    ]
                }
            ],
            selModel: {
                selType: 'checkboxmodel'
            }
        }
    ]

});