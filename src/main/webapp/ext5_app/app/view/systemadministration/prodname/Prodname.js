
Ext.define('BPSPortal.view.systemadministration.prodname.Prodname', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.prodname',

    requires: [
        'BPSPortal.view.systemadministration.prodname.ProdnameViewModel',
        'BPSPortal.view.systemadministration.prodname.ProdnameViewController', 
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
        type: 'prodname'
    },
    controller: 'prodname',
    title: '产品线信息',
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
                                fieldLabel: '产品线名称',
                                name: 'prodname',
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: '产品线编码',
                                name: 'prodcode',
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
                reference: 'ProdGrid',
                viewConfig: {
                    enableTextSelection: true
                },
                bind:{
                	store:'{prodname}'
                },
                columns: [
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'prodname',
                        text: '产品线名称'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'prodcode',
                        text: '产品线编码'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'remark',
                        text: '备注'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'active',
                        text: 'Active',
                        renderer:function(value){
                        	var result='';
                        	if(value!=null){
                        		if(value==1){
                        			result='是';
                        		}else{
                        			result='否';
                        		}
                        		return result;
                        	}
                        }
                    }

                ],
                dockedItems: [
                    {
                        xtype: 'pagingtoolbar',
                        dock: 'bottom',
                        bind: {
    				        store: '{prodname}'
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
                                iconCls: 'common-icon-add',
                                listeners: {
                                    click: 'onAddClick'
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
                reference: 'editprodPanel',
                bodyPadding: 10,
                items: [
					{
					    xtype: 'fieldcontainer',
					    layout: 'column',
					    padding: '20 0 0 0',
					    fieldLabel: '',
					    hidden:true,
					    items: [
					        {
					            xtype: 'textfield',
					            fieldLabel: 'prodid',
					            name: 'prodid',
					            bind: {
							        value: '{ProdGrid.selection.prodid}'
							    }
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
    	                            fieldLabel: '产品线名称',
    	                            name: 'prodname',
    	                            allowBlank:false,
    	                            bind: {
    							        value: '{ProdGrid.selection.prodname}'
    							    }
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
		                            fieldLabel: '产品线编码',
		                            name: 'prodcode',
		                            allowBlank:false,
    	                            bind: {
    							        value: '{ProdGrid.selection.prodcode}'
    							    }
		
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
	                            fieldLabel: '备注',
	                            name: 'remark',
	                            bind: {
							        value: '{ProdGrid.selection.remark}'
							    }
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
							    xtype: 'combobox',
							    fieldLabel: 'Active',
							    displayField: 'code',
							    valueField: 'value',
							    name: 'active',
							    bind: {
							        store: '{noOrYes}',
							        value: '{ProdGrid.selection.active}'
							    }
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