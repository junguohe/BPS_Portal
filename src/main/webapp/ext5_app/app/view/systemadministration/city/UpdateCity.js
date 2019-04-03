
Ext.define('BPSPortal.view.systemadministration.city.UpdateCity', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.city',

    requires: [
        'BPSPortal.view.systemadministration.city.UpdateCityViewModel',
        'BPSPortal.view.systemadministration.city.UpdateCityViewController', 
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
        type: 'city'
    },
    controller: 'city',
    title: '省市信息',
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
                                fieldLabel: '省',
                                name: 'province',
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: '市',
                                name: 'city',
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
                reference: 'CityGrid',
                viewConfig: {
                    enableTextSelection: true
                },
                bind:{
                	store:'{region}'
                },
                listeners: {
                    itemclick: 'onItemDblClick'
                },
                columns: [
					{
					    xtype: 'gridcolumn',
					    dataIndex: 'provinceid',
					    width:150,
					    hidden:true,
					    text: '省id'
					   
					},
					{
					    xtype: 'gridcolumn',
					    dataIndex: 'id',
					    width:150,
					    hidden:true,
					    text: '市id'
					   
					},
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'province',
                        width:150,
                        text: '省'
                       
                    },
                    {
                        xtype: 'gridcolumn',
                        dataIndex: 'city',
                        width:150,
                        text: '市',
                        renderer:function(value){
                        	if(value=='null'){
                        		return '';
                        	}else{
                        		return value;
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
                                text: '新增省',
                                iconCls: 'common-icon-add',
                                listeners: {
                                    click: function(){

                            	        Ext.create("Ext.window.Window", {
                            	        	 requires: [
													'BPSPortal.view.systemadministration.city.UpdateCityViewModel',
													'BPSPortal.view.systemadministration.city.UpdateCityViewController', 
                            	        	        ],
                            	        	        viewModel: {
                            	        	            type: 'city'
                            	        	        },
                            	        	        controller: 'city',
                            	        	title:'省',
                            	        	modal:true,
                            	        	height: 200,
                            	            width: 400,
                            	            items: [
                                                    {
                                                        xtype: 'fieldcontainer',
                                                        layout: 'column',
                                                        padding: '40 20 20 20',
                                                        fieldLabel: '',
                                                        items: [
                                	                        {
                                	                            xtype: 'textfield',
                                	                            fieldLabel: '省',
                                	                            name: 'province',
                                	                            reference:'province'
                                	                        }
                                                        ]
                                                    }
                                                ],
                                                dockedItems:[
                                                             {
                                                                 xtype: 'toolbar',
                                                                 dock: 'bottom',
                                                                 items: [
                                                                     {
                                                                         xtype: 'button',
                                                                         text: '确认',
                                                                         listeners: {
                                                                             click: 'onProvinceClick'
                                                                         }
                                                                     }
                                                                 ]
                                                             }
                                                         ]
                            	          }).show();
                            	    
                                    }
                                }
                            },
                            {
                                xtype: 'button',
                                text: '新增市',
                                iconCls: 'common-icon-add',
                                listeners: {
                                    click:function(){

                            	    	Ext.create("Ext.window.Window", {
                            	    		 requires: [
    													'BPSPortal.view.systemadministration.city.UpdateCityViewModel',
    													'BPSPortal.view.systemadministration.city.UpdateCityViewController', 
                                	        	        ],
                                	        	        viewModel: {
                                	        	            type: 'city'
                                	        	        },
                                	        	        controller: 'city',
                            	        	title:'市',
                            	        	modal:true,
                            	        	height: 250,
                            	            width: 400,
                            	            items: [
                                                    {
                                                        xtype: 'fieldcontainer',
                                                        layout: 'column',
                                                        padding: '40 20 20 20',
                                                        fieldLabel: '',
                                                        items: [
                                	                        {
                                	                        	xtype: 'citypicker',
                        	                                    displayField: 'city',
                        	                  				    valueField: 'id',
                        	                  				    fieldLabel: '省',
                        	                  				    name:'province',
                        	                  				    reference:'provinceid',
                        	                  				    allowBlank:false
                                	                        }
                                                        ]
                                                    },
                                                    {
                                                        xtype: 'fieldcontainer',
                                                        layout: 'column',
                                                        padding: '20 20 20 20',
                                                        fieldLabel: '',
                                                        items: [
                                	                        {
                                	                            xtype: 'textfield',
                                	                            fieldLabel: '市',
                                	                            name: 'city',
                                	                            reference:'city',
                                	                        }
                                                        ]
                                                    }
                                                ],
                                                dockedItems:[
                                                             {
                                                                 xtype: 'toolbar',
                                                                 dock: 'bottom',
                                                                 items: [
                                                                     {
                                                                         xtype: 'button',
                                                                         text: '确认',
                                                                         listeners: {
                                                                             click: 'onCityClick'
                                                                         }
                                                                     }
                                                                 ]
                                                             }
                                                         ]
                            	          }).show();
                            	    
                                    }
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
                flex: 1,
                animCollapse: false,
                collapseDirection: 'left',
                collapsed: true,
                collapsible: true,
                titleCollapse: false,
                reference: 'editPanel',
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
    	                            fieldLabel: '省id',
    	                            hidden:true,
    	                            name: 'provinceid',
    	                            bind: {
    							        value: '{CityGrid.selection.provinceid}'
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
    	                            fieldLabel: '市id',
    	                            hidden:true,
    	                            name: 'id',
    	                            bind: {
    							        value: '{CityGrid.selection.id}'
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
//                                    {
//
//                                        xtype: 'citypicker',
//                                        displayField: 'city',
//                    				    valueField: 'city',
//                    				    fieldLabel: '省',
//                    				    name:'province',
//                    				    allowBlank:false,
//                    				    bind: {
//        							        value: '{CityGrid.selection.province}'
//        							    }
//                                   
//                                    }
    	                        {
    	                            xtype: 'textfield',
    	                            fieldLabel: '省',
    	                            name: 'province',
    	                            bind: {
    							        value: '{CityGrid.selection.province}',
    							        hidden: '{!CityGrid.selection.provinceid}'
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
		                            fieldLabel: '市',
		                            name: 'city',
		                            bind: {
    							        value: '{CityGrid.selection.city}',
    							        hidden: '{!CityGrid.selection.city}'
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