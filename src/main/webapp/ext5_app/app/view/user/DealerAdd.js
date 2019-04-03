
Ext.define('BPSPortal.view.user.DealerAdd', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dealeradd',

    requires: [
        'BPSPortal.view.user.DealerAddViewModel',
        'BPSPortal.view.user.DealerAddViewController', 
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
        type: 'dealeradd'
    },
    controller: 'dealeradd',
    title: 'Dealer用户管理',
    closable: true,
    scrollable: true,
    layout: {
        type: 'hbox',
        align: 'stretch'
    },
    items: [{
        xtype: 'container',
        flex: 1,
        layout: {
            type: 'vbox',
            align: 'stretch'
        },
        items: [
            {
                xtype: 'form',
                bodyPadding: 10,
                reference: 'toolingSerachForm',
                title: '',
                items: [
                    {
                        xtype: 'fieldcontainer',
                        layout: 'column',
                        fieldLabel: '',
                        items: [
                            {
                                xtype: 'textfield',
                                fieldLabel: '用户名',
                                name: 'dealername',
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
                title:'查询结果',
                flex: 1,
                reference: 'DealerGrid',
                viewConfig: {
                    enableTextSelection: true
                },
                bind:{
                	store:'{addDealer}'
                },
                columns: [
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'username',
                        width:150,
                        text: '用户名'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'dealername',
                        width:150,
                        text: '经销商'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'ename',
                        text: '经销商缩写'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'email',
                        width:350,
                        text: 'Email'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'expiredate',
                        width:150,
                        text: '失效时间'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'telno',
                        width:150,
                        text: 'TelNo'
                    }
                ],
                dockedItems: [
                    {
                        xtype: 'pagingtoolbar',
                        dock: 'bottom',
                        bind: {
    				        store: '{addDealer}'
    				    },
                        displayInfo: true,
                        displayMsg: 'Displaying data {0} - {1} of {2}'
                    },
                    {
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [
                            {
                                xtype: 'button',
                                text: '新增',
                                hidden:true,
                                iconCls: 'common-icon-add',
                                listeners: {
                                    click: 'onAddClick'
                                }
                            },
                            {
                                xtype: 'button',
                                text: '修改',
                                iconCls: 'common_icon_update',
                                listeners: {
                                    click: 'onUpdateClick'
                                }
                            },
                            {
                                xtype: 'button',
                                text: '重置密码',
                                iconCls: 'common_icon_script',
                                listeners: {
                                    click: 'onFlushClick'
                                }
                            },
                            {
                                xtype: 'button',
                                text: '用户失效',
                                iconCls: 'person_inactive',
                                listeners: {
                                    click: 'onEndClick'
                                }
                            },
                            {
                                xtype: 'button',
                                text: '币种管理',
                                iconCls: 'common_icon_script',
                                listeners: {
                                    click: 'onCurrencyClick'
                                }
                            },
                            {
                                xtype: 'button',
                                text: '产品线管理',
                                iconCls: 'common_icon_script',
                                listeners: {
                                    click: 'onProdLineClick'
                                }
                            },
                            {
                                xtype: 'button',
                                text: '报备数管理',
                                iconCls: 'common_icon_script',
                                listeners: {
                                    click: 'onRegNumClick'
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
        flex: 1,
        items: [
            {
                xtype: 'form',
                title:'',
                layout: 'auto',
                border: false,
                reference: 'editDealerPanel',
                bodyPadding: 10,
                items: [
                        {
                            xtype: 'fieldcontainer',
                            layout: 'column',
                            padding: '20 0 0 0',
                            fieldLabel: '',
                            items: [
                                    {
										xtype: 'textfield',
										fieldLabel: '经销商',
										name: 'dealername'
                                    }
    							/*{
    	                            xtype: 'combobox',
    	                            fieldLabel: '经销商',
    	                            emptyText: "请选择",
    	                            allowBlank:false,
    	                            displayField: 'name',
    	                            valueField: 'id',
    	                            name: 'dealername',
    	                            bind: {
    	                                store: '{dealername}'
    	                            },
    	                            listeners:{
                						select: function (val, record, eOpts) {
                							var value=record.data;
                							console.log(value);
                							 var loginname = this.up('form').down('textfield[name=loginname]');
                							 	 loginname.setValue(record.data.name);
            							 	 var email = this.up('form').down('textfield[name=email]');
            							 	     email.setValue(record.data.femail);
            							 	 var dealercode = this.up('form').down('textfield[name=dealercode]');
            							 	     dealercode.setValue(record.data.id);
                							
                					    }
                				    }
    	                        }*/
                            ]
                        },
                        {
                            xtype: 'fieldcontainer',
                            layout: 'column',
                            padding: '20 0 0 0',
                            fieldLabel: '',
                            items: [
    	                        {
    	                            xtype: 'textfield',
    	                            fieldLabel: '经销商编码',
    	                            name: 'dealercode',
//    	                            readOnly:true
    	
    	                        }
                            ]
                        },
                        {
                            xtype: 'fieldcontainer',
                            layout: 'column',
                            padding: '20 0 0 0',
                            fieldLabel: '',
                            items: [
    	                        {
    	                            xtype: 'textfield',
    	                            fieldLabel: '经销商缩写',
    	                            name: 'ename',
//    	                            allowBlank:false
    	
    	                        }
                            ]
                        },
	                    {
	                        xtype: 'fieldcontainer',
	                        layout: 'column',
	                        padding: '20 0 0 0',
	                        fieldLabel: '',
	                        items: [
		                        {
		                            xtype: 'textfield',
		                            fieldLabel: 'LoginName',
		                            name: 'loginname',
//		                            allowBlank:false
		
		                        }
	                        ]
	                    },
                    {
                        xtype: 'fieldcontainer',
                        layout: 'column',
                        padding: '20 0 0 0',
                        fieldLabel: '',
                        items: [
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'TelNo',
	                            name: 'telno'
	                        }
                        ]
                    },
                    {
                        xtype: 'fieldcontainer',
                        layout: 'column',
                        padding: '20 0 0 0',
                        fieldLabel: '',
                        items: [
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'Email',
	                            name: 'email',
	                            allowBlank:false,
	                            regex: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+(,([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4}))*$/,
	                            regexText: "请输入正确的格式:xxx.xxx@xxx.xxx!各个邮件之间用英文逗号隔开"
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
                              click: 'onSaveClick'
                          }
                      }
                  ]
              }
          ]
    }
]
});