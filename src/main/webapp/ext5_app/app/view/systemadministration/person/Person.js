
Ext.define('BPSPortal.view.systemadministration.person.Person', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.person',

    requires: [
        'BPSPortal.view.systemadministration.person.PersonViewModel',
        'BPSPortal.view.systemadministration.person.PersonViewController', 
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
        type: 'person'
    },
    controller: 'person',
    title: '销售&FAE信息',
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
                                fieldLabel: 'per_name',
                                name: 'name',
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'fempgroup',
                                name: 'fempgroup',
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
                reference: 'PersonGrid',
                viewConfig: {
                    enableTextSelection: true
                },
                bind:{
                	store:'{person}'
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
					    dataIndex: 'per_id',
					    text: 'per_id'
					   
					},     
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'per_name',
                        text: 'per_name'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'departmentid',
                        text: 'departmentid'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'departmentname',
                        text: 'departmentname'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'peremail',
                        text: 'peremail'
                    },
                   
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'empid',
                        text: 'empid'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'mangerid',
                        text: 'mangerid'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'mangername',
                        text: 'mangername'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'account',
                        text: 'account'
                    },
                    {
                        xtype: 'gridcolumn',
                        format: 'Y-m-d',
                        dataIndex: 'fhiredate',
                        text: 'fhiredate',
                        renderer: function (val) {
                            var dt = new Date(val);
                            var update_date = Ext.Date.format(dt, 'Y-m-d')
                            if (val == ""||val== undefined|| val==null) {
                                return "";
                            }          
                            return update_date;
                       }
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'fempgroup',
                        text: 'fempgroup'
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'active',
                        hidden:true,
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
    				        store: '{person}'
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
							        value: '{PersonGrid.selection.id}'
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
					            fieldLabel: 'per_id',
					            name: 'per_id',
					            allowBlank:false,
					            bind: {
							        value: '{PersonGrid.selection.per_id}'
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
    	                            fieldLabel: 'per_name',
    	                            name: 'per_name',
    	                            allowBlank:false,
    	                            bind: {
    							        value: '{PersonGrid.selection.per_name}'
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
    					            fieldLabel: 'peremail',
    					            name: 'peremail',
    					            regex: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+(,([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4}))*$/,
    			                    regexText: "请输入正确的格式:xxx.xxx@xxx.xxx!各个邮件之间用英文逗号隔开",
    					            bind: {
    							        value: '{PersonGrid.selection.peremail}'
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
    					            fieldLabel: 'departmentid',
    					            displayField:'s',
    					            valueField:'area_id',
    					            name: 'departmentid',
    					            allowBlank:false,
    					            bind: {
    					            	store:'{area}',
    							        value: '{PersonGrid.selection.departmentid}'
    							    },
    							    listeners:{
			    						select: function (val, record, eOpts) {
			    							var value=record.data.area;
			    							if(value != null&&value !=''&&value !=-1&&value !=undefined){
			    								var name=this.up('form').down('textfield[name=departmentname]');
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
		                            readOnly:true,
		                            fieldLabel: 'departmentname',
		                            name: 'departmentname',
    	                            bind: {
    							        value: '{PersonGrid.selection.departmentname}'
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
	                            fieldLabel: 'empid',
	                            name: 'empid',
	                            allowBlank:false,
	                            bind: {
							        value: '{PersonGrid.selection.empid}'
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
	                            fieldLabel: 'mangerId',
	                            displayField:'x',
	                            valueField:'empid',
	                            name: 'mangerid',
	                            allowBlank:false,
	                            bind: {
	                            	store: '{persons}',
							        value: '{PersonGrid.selection.mangerid}'
							    },
							    listeners:{
		    						select: function (val, record, eOpts) {
		    							var value=record.data.per_name;
		    							if(value != null&&value !=''&&value !=-1&&value !=undefined){
		    								var name=this.up('form').down('textfield[name=mangername]');
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
	                            fieldLabel: 'mangerName',
	                            readOnly:true,
	                            name: 'mangername',
	                            bind: {
							        value: '{PersonGrid.selection.mangername}'
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
	                            fieldLabel: 'account',
	                            name: 'account',
	                            bind: {
							        value: '{PersonGrid.selection.account}'
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
	                            xtype: 'datefield',
	                            fieldLabel: 'fhiredate',
	                            format: 'Y-m-d',
	                            name: 'fhiredate',
	                            bind: {
							        value: '{PersonGrid.selection.fhiredate}'
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
    							    fieldLabel: 'fempgroup',
    							    displayField: 'id',
    							    valueField: 'name',
    							    name: 'fempgroup',
    							   // allowBlank:false,
    							    bind: {
    							        store: '{salesfae}',
    							        value: '{PersonGrid.selection.fempgroup}'
    							    }
    							}
                        ]
                    },
                    {
                        xtype: 'fieldcontainer',
                        layout: 'column',
                        hidden:true,
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
							        value: '{PersonGrid.selection.active}'
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