
Ext.define('BPSPortal.view.price.policyquery.PolicyQuery', {
    extend: 'Ext.form.Panel',
    alias: 'widget.policyquery',

    requires: [
        'BPSPortal.view.price.policyquery.PolicyQueryViewModel',
        'BPSPortal.view.price.policyquery.PolicyQueryViewController',
        'Ext.form.Panel',
        'Ext.form.field.Checkbox',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'policyquery'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
  	  afterrender: 'onAfterRenderW'
    },
    controller: 'policyquery',
    title: '价格政策查询',
    closable: true,
    scrollable: true,
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
                            fieldLabel: ' 政策编码',
                            name:'code'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '版本',
                            name:'versionno'
                        },
                        {
                            xtype: 'combobox',
                            padding: '0 0 0 50',
                            fieldLabel: '状态',
                            emptyText: "请选择",
                            displayField: 'code',
                            valueField: 'value',
                            name: 'status',
                            bind: {
                                store: '{status}'

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
                            fieldLabel: '发布时间自',
                            format: 'Y-m-d',
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
						    padding: '0 0 0 50',
						    fieldLabel: '主推',
						    emptyText: "全部",
						    displayField: 'code',
						    valueField: 'value',
						    name: 'isMain',
						    bind: {
						        store: '{noOrYes}'
						
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
                            fieldLabel: '  产品编码',
                            name:'materialcode'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '产品名称',
                            name:'materialname'
                        },
                        {
                            xtype: 'checkboxfield',
                            name:'isAccuracy',
                            padding: '0 0 0 10',
                            fieldLabel: '',
                            boxLabel: '精确查询'
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
            reference:'policyGrid',
            bind:{
            	store:'{price}'
            },
            minHeight:500,
            viewConfig:{
            	enableTextSelection:true
            },
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'code',
                    text: '政策编码'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'materialcode',
                    text: '  产品编码'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'materialname',
                    text: '产品名称'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'lifecycle',
                    text: '  生命周期'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'assembly',
                    text: '封装'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'ispublic',
                    text: '发布',
                    renderer:function(value){
                    	var result='';
                    	if(value==1){
                    		result='是'
                    	}else{
                    		result='否'
                    	}
                    	return result;
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'ismain',
                    text: '主推',
                    renderer:function(value){
                    	var result='';
                    	if(value==0){
                    		result='否'
                    	}else{
                    		result='是'
                    	}
                    	return result;
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'publicdate',
                    text: '发布时间'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'validfrom',
                    text: '生效开始'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'validto',
                    text: '生效结束'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'listprice',
                    align:'right',
                    text: '市场价格'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'listpriceinc',
                    align:'right',
                    text: '市场价格（含税）'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealerprice',
                    align:'right',
                    text: '经销商价格'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealerpriceinc',
                    align:'right',
                    text: '经销商价格（含税）'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealerprofit',
                    align:'right',
                    text: '经销商利润率',
                    renderer: function(value){
                   	  var result='';
                   	 if(value!=null){
                   		 var s = Math.floor(value*100);
                   		 result=s;
                   	 }
                         return result+'%' ;
                     }
                },
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'lastprice',
                    align:'right',
                    text: '上期价格'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'reduceper',
                    align:'right',
                    text: '降价幅度',
                    renderer: function(value){
                     	  var result='';
                        	 if(value!=null){
                        		 var s = Math.floor(value*100);
                        		 result=s;
                        	 }
                              return result+'%'	 ;
                          }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'currency',
                    text: '币种'
                }
            ],
            dockedItems: [
                          {
         				    xtype: 'pagingtoolbar',
         				    dock: 'bottom',
         				    bind: {
         				        store: '{price}'
         				    },
         				    displayInfo: true
         				},
         				{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
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