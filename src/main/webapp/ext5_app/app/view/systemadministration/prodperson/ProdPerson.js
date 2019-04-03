
Ext.define('BPSPortal.view.systemadministration.prodperson.ProdPerson', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.prodperson',

    requires: [
        'BPSPortal.view.systemadministration.prodperson.ProdPersonViewModel',
        'BPSPortal.view.systemadministration.prodperson.ProdPersonViewController', 
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
        type: 'prodperson'
    },
    controller: 'prodperson',
    title: '区域经理信息',
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
                                fieldLabel: 'area',
                                name: 'area'
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'mgr',
                                name: 'mgr'
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
                reference: 'ProdPersonGrid',
                viewConfig: {
                    enableTextSelection: true
                },
                bind:{
                	store:'{prodperson}'
                },
                columns: [
					{
					    xtype: 'gridcolumn',
					    dataIndex: 'id',
					    hidden:true,
					    text: 'id'
					   
					}, 
					{
					    xtype: 'gridcolumn',
					    dataIndex: 'area_id',
					    text: 'area_id'
					   
					},     
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'area',
                        text: 'area'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'mgr_id',
                        text: 'mgr_id'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'mgr',
                        text: 'mgr'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'fae_id',
                        text: 'fae_id'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'faemgr',
                        text: 'faemgr'
                       
                    },
                    {
                    	 xtype: 'gridcolumn',
                         dataIndex: 'area_directory_id',
                         hidden:true,
                         text: 'area_directory_id'
                    }
                ],
                dockedItems: [
                    {
                        xtype: 'pagingtoolbar',
                        dock: 'bottom',
                        bind: {
    				        store: '{prodperson}'
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
                            },
                            {
                                xtype: 'button',
                                text: '删除',
                                iconCls: 'common-icon-del',
                                listeners: {
                                    click: 'onDelClick'
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
					            fieldLabel: 'id',
					            name: 'id',
					            bind: {
							        value: '{ProdPersonGrid.selection.id}'
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
					            allowBlank:false,
					            fieldLabel: 'area_id',
					            name: 'area_id',
					            bind: {
							        value: '{ProdPersonGrid.selection.area_id}'
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
	                            fieldLabel: 'area',
	                            name: 'area',
	                            allowBlank:false,
	                            displayField: 'code',
							    valueField: 'code',
	                            bind: {
	                            	store:'{area}',
							        value: '{ProdPersonGrid.selection.area}'
							    },
		                        listeners:{
		    						select: function (val, record, eOpts) {
		    							var value=record.data.id;
		    							if(value != null&&value !=''&&value !=-1&&value !=undefined){
		    								var name=this.up('form').down('textfield[name=area_directory_id]');
		    								name.setValue(value);
		    						    }
		    					    }
		    				    }
	                        }
                        ]
                    },
                    {
                        xtype: 'fieldcontainer',
                        layout: 'column',
                        padding: '20 0 0 0',
                        hidden:true,
                        items: [
	                        {
	                            xtype: 'textfield',
	                            fieldLabel: 'area_directory_id',
	                            name: 'area_directory_id',
	                            bind: {
							        value: '{ProdPersonGrid.selection.area_directory_id}'
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
    					            fieldLabel: 'mgr_id',
    					            displayField: 's',
    							    valueField: 'empid',
    							    allowBlank:false,
    					            name: 'mgr_id',
    					            bind: {
    					            	store: '{mgrstore}',
    							        value: '{ProdPersonGrid.selection.mgr_id}'
    							    },
    							    listeners:{
			    						select: function (val, record, eOpts) {
			    							var value=record.data.per_name;
			    							if(value != null&&value !=''&&value !=-1&&value !=undefined){
			    								var name=this.up('form').down('textfield[name=mgr]');
			    								name.setValue(value);
			    						    }
			    					    }
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
    					            fieldLabel: 'mgr',
    					            readOnly:true,
    					            name: 'mgr',
    					            bind: {
    							        value: '{ProdPersonGrid.selection.mgr}'
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
		                            fieldLabel: 'fae_id',
		                            name: 'fae_id',
		                            displayField: 's',
		                            allowBlank:false,
    							    valueField: 'empid',
    	                            bind: {
    	                            	store:'{mgrstore}',
    							        value: '{ProdPersonGrid.selection.fae_id}'
    							    },
			                        listeners:{
			    						select: function (val, record, eOpts) {
			    							var value=record.data.per_name;
			    							if(value != null&&value !=''&&value !=-1&&value !=undefined){
			    								var name=this.up('form').down('textfield[name=faemgr]');
			    								name.setValue(value);
			    						    }
			    					    }
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
	                            fieldLabel: 'faemgr',
	                            name: 'faemgr',
	                            readOnly:true,
	                            bind: {
							        value: '{ProdPersonGrid.selection.faemgr}'
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