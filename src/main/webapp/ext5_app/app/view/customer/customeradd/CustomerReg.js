
Ext.define('BPSPortal.view.customer.customeradd.CustomerReg', {
    extend: 'Ext.form.Panel',
    alias: 'widget.customerreg',

    requires: [
        'BPSPortal.view.customer.customeradd.CustomerAddViewModel',
        'BPSPortal.view.customer.customeradd.CustomerAddViewController',
        'Ext.form.field.ComboBox',
        'Ext.tab.Panel',
        'Ext.tab.Tab',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill'
    ],

    viewModel: {
        type: 'customeradd'
    },
    controller: 'customeradd',
    title: '客户报备',
    closable: true,
    scrollable: true,
    listeners: {
  	  afterrender: 'onAfterRender'
    },
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
                    fieldLabel: '客户编号',
                    readOnly:true,
                    name:'custcode',
                    bind:{
                    	value:'{data.custcode}'
                    }
                },
                {
                    xtype: 'textfield',
                    padding: '0 0 0 50',
                    fieldLabel: '<font color ="red">* </font>客户全称',
                    readOnly:true,
                    name:'custname',
                    bind:{
                    	value:'{data.custname}'
                    }
                },
                {
				    xtype: 'combobox',
				    fieldLabel: '<font color ="red">* </font>产品线',
                    padding: '0 0 0 50',
				    emptyText: "请选择",
				    readOnly:true,
				    displayField: 'prodname',
				    editable:false,
				    valueField: 'prodid',
				    name: 'prodid',
				    bind: {
				        store: '{prodline}',
				        value:'{data.prodid}'
				
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
				    fieldLabel: 'custid',
				    name: 'custid',
				    reference: 'custid',
				    hidden: true,
                    bind:{
                    	value:'{data.custid}'
                    }
				},
				{
				    xtype: 'textfield',
				    fieldLabel: 'cpid',
				    name: 'cpid',
				    reference: 'cpid',
				    hidden: true,
                    bind:{
                    	value:'{data.cpid}'
                    }
				},
				{
				    xtype: 'combobox',
				    fieldLabel: '<font color ="red">* </font>区域',
				    editable:false,
				    emptyText: "请选择",
				    readOnly:true,
				    displayField: 'code',
				    valueField: 'value',
				    name: 'region',
				    bind: {
				        store: '{area}',
				        value:'{data.region}'
				
				    }
				
				},
				{
                    xtype: 'textfield',
                    fieldLabel: '税号',
				    padding: '0 0 0 50',
                    readOnly:true,
                    name:'taxno',
				    bind: {
				        value:'{data.taxno}'
				
				    }
                },
                {
				    xtype: 'customerpickers',
				    padding: '0 0 0 50',
				    fieldLabel: '母公司',
				    readOnly:true,
				    displayField: 'custname',
				    valueField: 'id',
				    name: 'parenetcompany',
				    bind: {
				        value: '{data.parenetcompany}'
				
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
                    xtype: 'checkboxgroup',
                    flex: 1,
                    width: 400,
                    hidden:true,
                    fieldLabel: '',
                    items: [
						{
						    xtype: 'checkboxfield',
						    boxLabel: '是否母公司',
						    name:'isparent',
						    inputValue: true,
						    bind: {
						        value:'{data.isparent}'
						
						    }
						}
                    ]
                }
            ]
        },
        {
            xtype: 'tabpanel',
            activeTab: 0,
            minHeight:500,
            padding:'10 0 0 0 ',
            items: [
                {
                    xtype: 'panel',
                    title: '地址',
                    items: [
                        {
                            xtype: 'gridpanel',
                            itemId: 'mygridpanel1',
                            reference: 'addressGrid',
                            bind:{
                                store: '{address}'
                            },
                            title: '',
                            plugins: [
                                 {
                                     ptype: 'cellediting',
                                     clicksToEdit:1
                                 }
                               ],
                            columns: [        
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'prodname',
                                    text: '产品线'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'addtype',
                                    text: '<font color ="red">* </font>地址类型',
                                    editor: {
	                                      editable:false,
	          		                      xtype: 'combobox',
	          		                      bind: {
	          		                         store: '{addtype}'
	          		                      },
	          		                      displayField: 'code',
	          						      valueField: 'value',
	          						      name:'addtype'
                                  }
                                },
//                                {
//                                    xtype: 'gridcolumn',
//                                    dataIndex: 'province',
//                                    text: '省',
//                                    editor: {
//                                    	 forceSelection:true,
//	                                      editable:false,
//	          		                      xtype: 'combobox',
//	          		                      bind: {
//	          		                         store: '{province}'
//	          		                      },
//	          		                      displayField: 'city',
//	          						      valueField: 'city',
//	          						      name:'province',
//	          						      listeners: {
//	                                           change: 'onValueChange'
//	                                         }
//                                  }
//                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'province',
                                    width:100,
                                    text: '<font color ="red">* </font>省',
                                    editor: {
                                        xtype: 'citypicker',
                                        displayField: 'city',
                    				    valueField: 'city',
                    				    name:'province',
                    				    listeners: {
                                           change: 'onValueChange'
                                         }
                                   }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 61,
                                    width:100,
                                    dataIndex: 'city',
                                    text: '<font color ="red">* </font>市',
                                    editor: {
                                    	forceSelection:true,
	                                      editable:false,
	          		                      xtype: 'combobox',
	          		                      displayField: 'city',
	          						      valueField: 'city',
	          						      name:'city',
	          						      bind: {
	          		                         store: '{city}'
	          		                      }

                                   }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'address',
                                    width:250,
                                    text: '<font color ="red">* </font>详细地址',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'address'
                                    }
                                },
                                {
                                    xtype: 'checkcolumn',
                                    header: '主要',
                                    dataIndex: 'isdefault',
                                    width: 50,
                                    stopSelection: false,
                                    listeners: {
                                        checkchange: 'onCheckboxClick'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 250,
                                    dataIndex: 'remark',
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
                                        iconCls:'common-icon-delete',
                                        text: '<font color ="black">删除</font>',
                                        listeners: {
                                            click: 'onDelAddressClick'
                                        }
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
                                            text: '新增',
                                            iconCls: 'common-icon-add',
                                            listeners: {
                                                click: 'onAddAddressClick'
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: '联系人',
                    items: [
                        {
                            xtype: 'gridpanel',
                            title: '',
                            reference: 'contactGrid',
                            bind:{
                                store: '{contact}'
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
                                    dataIndex: 'prodname',
                                    text: '产品线'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'title',
                                    text: '<font color ="red">* </font>职位',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'title'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'name',
                                    text: '<font color ="red">* </font>姓名',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'name'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'mobile',
                                    width:120,
                                    text: '手机',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'mobile'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'telno',
                                    width:120,
                                    text: '<font color ="red">* </font>固定电话',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'telno'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'email',
                                    width:150,
                                    text: '邮箱',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'email'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'im',
                                    width:120,
                                    text: '即时通讯',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'im'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'remark',
                                    text: '备注',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'remark'
                                    }
                                },
//                                {
//                                    xtype: 'gridcolumn',
//                                    dataIndex: 'bool',
//                                    text: '名片',
//                                    editor: {
//                                        xtype: 'textfield',
//                                        allowBlank: false,
//                                        name: 'remark'
//                                    }
//                                },
                                {
                                    xtype: 'widgetcolumn',
                                    text: '操作',
                                    widget: {
                                        xtype: 'button',
                                        style: {
                                        	background: 'white',
                                        	border: 0
                                        },
                                        iconCls:'common-icon-delete',
                                        text: '<font color ="black">删除</font>',
                                       // text: '删除',
                                        listeners: {
                                            click: 'onDelContactClick'
                                        }
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
                                            text: '新增',
                                            iconCls: 'common-icon-add',
                                            listeners: {
                                                click: 'onAddContactClick'
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: '项目',
                    items: [
                        {
                            xtype: 'gridpanel',
                            title: '',
                            reference: 'projectGrid',
                            bind:{
                                store: '{project}'
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
                                    dataIndex: 'prodname',
                                    text: '产品线'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'projname',
                                    text: '项目名称',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'projname'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'projtype',
                                    hidden:true,
                                    text: '项目类型',
                                    editor: {
	                                      editable:false,
	          		                      xtype: 'combobox',
	          		                      bind: {
	          		                         store: '{projtype}'
	          		                      },
	          		                      displayField: 'code',
	          						      valueField: 'value',
	          						      name:'projtype'
                                  }
                                },

                                {
									xtype : 'gridcolumn',
									dataIndex : 'materialid',
									width:200,
									text : '料号',
									editor : {
										xtype: 'materialpickers',
                                        displayField: 'materialname',
                    				    valueField: 'id',
                    				    name:'materialid'

									},
									renderer : function(value) {
										if (value != null&&value !=''&&value !=-1&&value !=undefined) {
											var gridStore = this.up('form').getViewModel().getStore('material');
											if(gridStore.findRecord('id',value)==null){
								            	return "" ;
											}else{
												return gridStore.findRecord('id',value).data.materialname;
											}
										}else{
											return "";
										}
									}
                                },

                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'compname',
                                    text: '竞争对手',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'compname'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'compprod',
                                    text: '  竞争对手料号',
                                    width:190,
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'compprod'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'shipvol',
                                    text: '预计出货量',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'shipvol'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'massproddate',
                                    text: '预计量产时间',
                                    renderer: function (val) {
                                        var dt = new Date(val);
                                        var regstartdate = Ext.Date.format(dt, 'Y-m-d')
                                        if (val == ""||val==null) {
                                            return "";
                                        }               
                                        return regstartdate;
                                    },
                                    editor: {
                                        xtype: 'datefield',
                                        format: 'Y-m-d',
                                        name: 'massproddate'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'remark',
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
                                        iconCls:'common-icon-delete',
                                        text: '<font color ="black">删除</font>',
                                        listeners: {
                                            click: 'onDelProjectClick'
                                        }
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
                                            text: '新增',
                                            iconCls: 'common-icon-add',
                                            listeners: {
                                                click: 'onAddProjectClick'
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: '客户管理',
                    items: [
                        {
                            xtype: 'gridpanel',
                            title: '',
                            reference:'custBPSGrid',
                            minHeight:175,
                            bind:{
                            	store: '{custBPS}'
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
                                    dataIndex: 'prodname',
                                    text: '产品线'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'isshare',
                                    text: '共营',
                                    editor: {
                                    	  editable:false,
	          		                      xtype: 'combobox',
	          		                      bind: {
	          		                         store: '{isshare}'
	          		                      },
	          		                      displayField: 'code',
	          						      valueField: 'value',
	          						      name:'isshare'
                                    },
                                    renderer:function(value){
                                        if (value != null) {
                                        	if(value==1){
                                        		return '是'
                                        	}else{
                                        		return '否'
                                        	}
                                      }
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'custtype',
                                    text: '客户分类',
                                    width:120,
                                    editor: {
                                    	forceSelection:true,
                                    	xtype: 'combobox',
	          		                      	editable:false,
	          		                      	bind: {
	          		                      		store: '{custtype}'
	          		                      	},
	          		                      	displayField: 'value',
	          		                      	valueField: 'code',
	          		                      	name:'custtype'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'custcategory1',
                                    text: '客户类别1',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'custcategory1'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'custcategory2',
                                    text: '客户类别2',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'custcategory2'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'custcategory3',
                                    text: '客户类别3',
                                    editor: {
                                        xtype: 'textfield',
                                        name: 'custcategory3'
                                    }
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'bpssales',
                                    text: '销售',
                                    editor: {
                                    	xtype: 'personsalespicker',
	          		                      	displayField: 'per_name',
	          		                      	valueField: 'empid',
	          		                      	name:'bpssales'
                                    },
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
                                    editor: {
                                    		xtype: 'personpicker',
                                    		displayField: 'per_name',
                                    		valueField: 'empid',
                                    		name:'bpsfae'
                                       
                                    },
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
                                    dataIndex: 'dealersales',
                                    text: '经销商销售',
                                    editor: {
                                    	 xtype: 'textfield',
                                         name: 'dealersales'
                                    }
//                                    renderer:function(value){
//                                        if (value != null) {
//                                        var gridStore = this.up('form').getViewModel().getStore('dealers');
//                                      	
//                                        return gridStore.findRecord('dealerid',value).data.dealername;
//                                      }
//                                    }
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
                                        iconCls:'common-icon-delete',
                                        text: '<font color ="black">删除</font>',
                                        listeners: {
                                            click: 'onDelCustBPSClick'
                                        }
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
                                            text: '新增',
                                            iconCls: 'common-icon-add',
                                           listeners: {
                                                     click: 'onAddCustBPSClick'
                                                 }
                                        }
                                    ]
                                }
                           ]
                        }
                    ]
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
                    text: '提交',
                    iconCls:'common_icon_save',
                    listeners: {
                        click: 'onRegSaveClick'
                    }
                }
//                {
//                    xtype: 'button',
//                    text: '保存',
//                    listeners: {
//                        click: 'onUpdateStatusClick'
//                    }
//                }
            ]
        }
    ]
    

});