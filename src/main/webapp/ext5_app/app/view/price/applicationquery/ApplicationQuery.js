

Ext.define('BPSPortal.view.price.applicationquery.ApplicationQuery', {
    extend: 'Ext.form.Panel',
    alias: 'widget.applicationquery',

    requires: [
        'BPSPortal.view.price.applicationquery.ApplicationQueryViewModel',
        'BPSPortal.view.price.applicationquery.ApplicationQueryViewController',
        'Ext.form.Panel',
        'Ext.form.field.Date',
        'Ext.form.field.Checkbox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.view.Table',
        'Ext.grid.column.Widget'
    ],

    viewModel: {
        type: 'applicationquery'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
    	  afterrender: 'onAfterRenderW'
      },
    controller : 'applicationquery',
    title: '特价申请查询',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'form',
            height: 230,
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
                            fieldLabel: '单据编号',
                            name:'billno'
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: '审批状态',
                            emptyText: "请选择",
                            padding: '0 0 0 50',
                            displayField: 'code',
                            valueField: 'value',
                            name: 'billstatus',
                            bind: {
                                store: '{billstatus}'

                            }

                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '申请人',
                            name:'applicator'
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
                            fieldLabel: '客户编号',
                            name:'custcode'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '  客户简称',
                            name:'custname'
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
                            xtype: 'datefield',
                            fieldLabel: '  价格生效  时间',
                            format : 'Y-m-d',
                            name:'activedate'
                        },
                        {
                            xtype: 'datefield',
                            fieldLabel: '申请时间自',
                            padding: '0 0 0 50',
							format : 'Y-m-d',
                            name:'startdate'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 50',
                            fieldLabel: '至',
							format : 'Y-m-d',
                            name:'enddate'
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
            viewConfig:{
            	enableTextSelection:true
            },
            listeners:{
            	itemdblclick:'onItemDbClick'
            },
            reference:'specialprice',
            bind:{
            	store:'{price}'
            },
            columns: [
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'billstatus',
                    text: '<br/>审批状态',
                    renderer:function(value){
                    	var result='';
                    	if(value==3){
                    		result='审批驳回'
                    	}else if(value==2){
                    		result='审批通过'
                    	}else if(value==4){
                    		result='待处理'
                    	}else{
                    		result='已提交'
                    	}
                    	return result;
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'applytype',
                    text: '<br/>申请类型'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custname',
                    width:200,
                    text: '<br/>客户名称'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'materialname',
                    text: '<br/>产品名称'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'applypriceinc1',
                    align:'right',
                    text: '客户期望价格<br/>（含税）'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'applyprice1',
                    align:'right',
                    text: '  客户期望价格<br/>（未税）'
                },
                {
                    xtype: 'gridcolumn',
                    align:'right',
                    width:130,
                    dataIndex: 'sugcustsplinc',
                    text: '建议客户出货价<br/>（含税）',
                    renderer : function(v,m){  
                        m.css='x-grid-back-color';  
                        return v;  
                    } 
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'sugdealersplinc',
                    align:'right',
                    width:130,
                    text: '建议经销商成本价<br/>（含税）',
                    renderer : function(v,m){  
                        m.css='x-grid-back-color';  
                        return v;  
                    } 
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'sugdealerprofit',
                    align:'right',  
                    text: '<br/>经销商利润率',
                    renderer : function(value,m){
                   	 m.css='x-grid-back-color'; 
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
                    align:'right',
                    width:130,
                    dataIndex: 'sugcustspl',
                    text: '建议客户出货价<br/>（未税）',
                    renderer : function(v,m){  
                        m.css='x-grid-back-color';  
                        return v;  
                    } 
                  	  
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'sugdealerspl',
                    width:130,
                    align:'right',
                    text: '建议经销商成本价<br/>（未税）',
                    renderer : function(v,m){  
                        m.css='x-grid-back-color';  
                        return v;  
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'applydate',
                    text: '  申请时间',
                    width:150,
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
                    xtype: 'gridcolumn',
                    dataIndex: 'approvedate',
                    text: '<br/>生效时间',
                    width:150,
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
                    xtype: 'gridcolumn',
                    dataIndex: 'remark',
                    width:200,
                    text: '<br/>审批意见'
                },
                
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'isnotrebate',
                    text: '一次性',
                    renderer:function(value){
                    	var result='';
                    	if(value==0){
                    		result='是'
                    	}else{
                    		result='否'
                    	}
                    	return result;
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'materialcode',
                    hidden:true,
                    text: '<br/>产品编号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'projname',
                    text: '<br/>项目名称'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'projstatus',
                    text: '<br/>项目状态'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'volyear',
                    text: '<br/>预计年用量'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'compmaterial',
                    text: '<br/>竞争对手型号'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'comppriceinc',
                    align:'right',
                    text: '竞争对手价格<br/>（含税）'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'currency',
                    text: '<br/>币种'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'approvedate',
                    text: '  批复时间',
                    hidden:true,
                    width:150,
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
                    xtype: 'gridcolumn',
                    dataIndex: 'remark',
                    hidden:true,
                    text: '  申请说明'
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