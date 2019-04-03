
Ext.define('BPSPortal.view.systemadministration.directory.Directory', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.directory',

    requires: [
        'BPSPortal.view.systemadministration.directory.DirectoryViewModel',
        'BPSPortal.view.systemadministration.directory.DirectoryViewController', 
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
        type: 'directory'
    },
    controller: 'directory',
    title: '字典表信息',
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
							    fieldLabel: 'code',
							    name: 'code'
							},
							{
							    xtype: 'textfield',
							    fieldLabel: 'value',
							    name: 'value'
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
                reference: 'directoryGrid',
                viewConfig: {
                    enableTextSelection: true
                },
                bind:{
                	store:'{dictionarys}'
                },
                columns: [
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'active',
                          hidden:true,
                          text: 'active'
                         
                      },
                      
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'id',  
                          hidden:true,
                          text: 'id'
                         
                      },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'module',
                        text: '模块'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'function',
                        text: 'function'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'code',
                        text: 'code'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'value',
                        text: 'value'
                    }

                ],
                dockedItems: [
                    {
                        xtype: 'pagingtoolbar',
                        dock: 'bottom',
                        bind: {
    				        store: '{dictionarys}'
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
                reference: 'editPanel',
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
    					            fieldLabel: 'id',
    					            name: 'id',
    					            bind: {
    							        value: '{directoryGrid.selection.id}'
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
        	                            fieldLabel: 'function',
        	                            name: 'functions',
        	                            allowBlank:false,
        	                            bind: {
        							        value: '{directoryGrid.selection.function}'
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
        	                            fieldLabel: 'module',
        	                            name: 'module',
        	                            allowBlank:false,
        	                            bind: {
        							        value: '{directoryGrid.selection.module}'
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
    		                            fieldLabel: 'value',
    		                            name: 'value',
    		                            allowBlank:false,
        	                            bind: {
        							        value: '{directoryGrid.selection.value}'
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
    		                            fieldLabel: 'code',
    		                            name: 'code',
    		                            allowBlank:false,
        	                            bind: {
        							        value: '{directoryGrid.selection.code}'
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
    							        value: '{directoryGrid.selection.remark}'
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
    							        value: '{directoryGrid.selection.active}'
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