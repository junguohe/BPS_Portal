

Ext.define('BPSPortal.view.price.pricequery.PriceQuery', {
    extend: 'Ext.form.Panel',
    alias: 'widget.pricequery',

    requires: [
        'BPSPortal.view.price.pricequery.PriceQueryViewModel',
        'BPSPortal.view.price.pricequery.PriceQueryViewController',
        'Ext.form.Panel',
        'Ext.form.field.Checkbox',
        'Ext.form.field.Date',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.view.Table',
        'Ext.grid.column.Widget'
    ],

    viewModel: {
        type: 'pricequery'
    },
    listeners: {
    	  afterrender: 'onAfterRenderW'
      },
    controller: 'pricequery',
    title: '价格查询',
    closable: true,
    scrollable: true,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    items: [
        {
            xtype: 'form',
            height: 250,
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
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '产品编码',
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
                            fieldLabel: '生效时间自',
                            format: 'Y-m-d',
                            value:Ext.util.Format.date(new Date(), "Y-m-") + "01",
                            name:'startdate'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 50',
                            fieldLabel: '至',
                            format: 'Y-m-d',
                            value:Ext.util.Format.date(new Date(new Date(new Date().getUTCFullYear(), new Date().getMonth() + 1, 1) - 86400000), "Y-m-d"),
                            name:'enddate'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '经销商',
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
						    fieldLabel: '发布',
						    emptyText: "全部",
						    displayField: 'code',
						    valueField: 'value',
						    name: 'isRelease',
						    bind: {
						        store: '{noOrYes}'
						    }
						},
                        {
						    xtype: 'combobox',
                            padding: '0 0 0 50',
						    fieldLabel: '特价',
						    editable:false,
						    displayField: 'code',
						    valueField: 'value',
						    name: 'isSpecial',
						    value:'0',
						    bind: {
						        store: '{noOrYes}'
						    }, 
						    listeners:{
        						select: function (val, record, eOpts) {
        							if(record.data.code=='是'){
        								 var isnotrebate = this.up('form').down('combobox[name=isnotrebate]');
        								 isnotrebate.setHidden(false);
                                        var istax = this.up('form').down('combobox[name=istax]');
                                        istax.setHidden(false);
        							}else{
        								 var isnotrebate = this.up('form').down('combobox[name=isnotrebate]');
        								 isnotrebate.setHidden(true);

                                        var istax = this.up('form').down('combobox[name=istax]');
                                        istax.setHidden(true);
        							}
        					    }
        				    }
						},
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '客户名称/编号',
                            name:'name'
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
						    fieldLabel: '主推',
						    emptyText: "全部",
						    hidden:true,
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
						    xtype: 'combobox',
						    fieldLabel: '一次性',
						    emptyText: "全部",
						    hidden:true,
						    displayField: 'code',
						    valueField: 'value',
						    name: 'isnotrebate',
						    bind: {
						        store: '{noOrYes}'
						
						    }
						
						},
                        {
                            xtype: 'combobox',
                            fieldLabel: '是否含税',
                            hidden:true,
                            padding: '0 0 0 50',
                            displayField: 'code',
                            valueField: 'value',
                            name: 'istax',
                            bind: {
                                store: '{noOrYes}'

                            }
                        },
						{
						    xtype: 'combobox',
						    fieldLabel: '生效',
						    emptyText: "全部",
						    padding: '0 0 0 50',
						    hidden:true,
						    displayField: 'code',
						    valueField: 'value',
						    name: 'ispublic',
						    bind: {
						        store: '{noOrYes}'
						
						    }
						
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
                            iconCls: 'common-icon-serach',
                            itemId: 'searchBtn',
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
            minHeight: 450,
            title: '查询结果（标准价）',
            hidden:false,
            reference:'priceGrid',
            bind:{
            	store: '{price}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            columns: [
					{
					    xtype: 'gridcolumn',
					    dataIndex: 'validfrom',
					    text: '<div style="text-align:center"><br/>生效日期</div>'
					},
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'materialcode',
                          text: '<div style="text-align:center"><br/>产品编码</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'materialname',
                          text: '<div style="text-align:center"><br/>产品名称</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'prodname',
                          width:80,
                          text: '<div style="text-align:center"><br/>产品线</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'currency',
                          width:80,
                          text: '<div style="text-align:center"><br/>币种</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'dealerpriceinc',
                          align:'right',
                          text: '<div style="text-align:center"><br/>经销商<br/>含税价</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'dealerprice',
                          align:'right',
                         // width:150,
                          text: '<div style="text-align:center"><br/>经销商<br/>不含税价</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'lifecycle',
                          hidden:true,
                          text: '<div style="text-align:center"><br/>生命周期</div>'
                      },
                      
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'ismain',
                          hidden:true,
                          text: '<div style="text-align:center"><br/>主推</div>',
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
                      },                   
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'listpriceinc',
                          align:'right',
                          text: '<div style="text-align:center"><br/>市场<br/>含税价</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          align:'right',
                          dataIndex: 'listprice',
                          text: '<div style="text-align:center"><br/>市场<br/>不含税价</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          align:'right',
                          dataIndex: '',
                          text: '<div style="text-align:center"><br/>客户<br/>起始特价</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'lastprice',
                          align:'right',
                          text: '<div style="text-align:center"><br/>上期经销商<br/>含税成本价</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'reduceper',
                          text: '<div style="text-align:center"><br/>降价<br/> 幅度</div>',
                          align:'right',
                          width:100,
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
                          xtype: 'numbercolumn',
                          dataIndex: 'dealerprofit',
                          text: '<div style="text-align:center"><br/>经销商<br/>利润率</div>',
                          align:'right',
                          width:100,
                          renderer: function(value){
                        	 var result='';
                        	 if(value!=null){
                        		 var s =( value*100).toFixed(1);
                        		 result=s;
                        	 }
                              return result+'%' ; 
                         }
                      },

                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'assembly',
                          text: '<div style="text-align:center"><br/>封装形式</div>'
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'ispublic',
                          width:80,
                          text: '<div style="text-align:center"><br/>发布</div>',
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
                      },
                      {
                          xtype: 'gridcolumn',
                          dataIndex: 'code',
                          text: '<div style="text-align:center"><br/>价格政策码</div>'
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
                                hidden:true,
                                text: '导出',
                                iconCls:'common-icon-down',
                                listeners:{
                                	click:'onDownloadClick'
                                }
                            }
                        ]
                    }
                 ]
        },
        {
            xtype: 'gridpanel',
            title: '查询结果（特价）',
            minHeight: 450,
            hidden:true,
            reference:'specialpriceGrid',
            bind:{
            	store: '{specialprice}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            columns:[{
                xtype: 'gridcolumn',
                dataIndex: 'applytype',
                text: '特价类型'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'dealername',
                text: '经销商'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'custname',
                text: '客户'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'materialcode',
                text: '产品编码'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'materialname',
                text: '产品名称'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'istax',
                text: '是否含税',
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
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'isspl',
                text: '特价提货',
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
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'activedate',
                text: '生效日期'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'sugdealersplinc',
                text: '经销商含税价',
                renderer : function(v,m){  
                    m.css='x-grid-back-color';  
                    return v;  
                } 
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'sugdealerspl',
                text: '经销商未税价',
                renderer : function(v,m){  
                    m.css='x-grid-back-color';  
                    return v;  
                } 
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'sugdealerprofit',
                text: '经销商利润率',
                //format:'0,000.0',
                renderer : function(value,m){
                  	 m.css='x-grid-back-color'; 
                	 var result='';
                	 if(value!=null){
                		 var s =( value*100).toFixed(1);
                		 result=s;
                	 }
                      return result+'%' ; 
                 }  
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'sugcustsplinc',
                align:'right',
                text: '客户含税价格',
                renderer : function(v,m){  
                    m.css='x-grid-back-color';  
                    return v;  
                } 
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'sugcustspl',
                align:'right',
                text: '客户不含税价格',
                renderer : function(v,m){  
                    m.css='x-grid-back-color';  
                    return v;  
                } 
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'assembly',
                text: '封装形式'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'isnotrebate',
                text: '一次性',
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
            },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'execqty',
                    text: '<br/>执行数量',
                    renderer:function(value){
                        if(value==null){
                            result= 0
                        }else{
                            result=value
                        }
                        return result;
                    }
                },
            {
                xtype: 'gridcolumn',
                dataIndex: 'currency',
                text: '币种'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'remark',
                text: '申请说明'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'approver',
                text: '审批人'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'approvedate',
                text: '审批时间'
            },
            {
                xtype: 'gridcolumn',
                dataIndex: 'auditremark',
                width:300,
                text: '批复意见'
            }
            
],
            dockedItems: [
                          {
         				    xtype: 'pagingtoolbar',
         				    dock: 'bottom',
         				    bind: {
         				        store: '{specialprice}'
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
                                },
                                {
                                    xtype: 'button',
                                    text: '失效',
                                    iconCls:'common-icon-del',
                                    listeners:{
                                    	click:'onInActiveClick'
                                    }
                                }
                            ]
                        }
                     ]
        }
    ]

});