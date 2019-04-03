
Ext.define('BPSPortal.view.price.applicationapproval.ApplicationApproval', {
    extend: 'Ext.form.Panel',
    alias: 'widget.applicationapproval',

    requires: [
        'BPSPortal.view.price.applicationapproval.ApplicationApprovalViewModel',
        'BPSPortal.view.price.applicationapproval.ApplicationApprovalViewController',
        'Ext.form.Panel',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.view.Table',
        'Ext.grid.column.Widget',
        'Ext.form.field.TextArea'
    ],

    viewModel: {
        type: 'applicationapproval'
    },
    listeners: {
  	  afterrender: 'onAfterRenderW'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    controller : 'applicationapproval',
    scrollable: true,
    title: '特价审批',
    closable: true,
    items: [
        {
            xtype: 'form',
            height: 200,
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
                            xtype: 'textfield',
                            fieldLabel: '经销商',
                            name:'dealername'
                        },
                        {
                            xtype: 'combobox',
                            fieldLabel: '审批状态',
                            emptyText: "请选择",
                            padding: '0 0 0 50',
                            displayField: 'code',
                            valueField: 'value',
                            value:'1',
                            name: 'billstatus',
                            bind: {
                                store: '{billstatus}'

                            }

                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '  单据编号',
                            padding: '0 0 0 50',
                            name:'billno'
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
                            fieldLabel: '客户编号',
                            name:'custcode'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '  客户名称',
                            name:'custname'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '产品名称',
                            name:'materialname'
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
                            fieldLabel: '  申请时间  自',
                            format: 'Y-m-d',
                            name:'startdate'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 50',
                            format: 'Y-m-d',
                            fieldLabel: '至',
                            name:'enddate'
                        },
                        {
                            xtype: 'textfield',
                            padding: '0 0 0 50',
                            fieldLabel: '区域',
                            name:'region'
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
            minHeight: 400,
            title: '查询结果',
            reference:'priceGrid',
            bind:{
            	store:'{price}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            listeners:{
            	itemdblclick:'onItemDbClick'
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
	                dataIndex: 'applydate',
	                text: '<br/>申请时间',
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
	                  dataIndex: 'dealername',
	                  text: '<br/>经销商'
	              },
	              {
	                  xtype: 'gridcolumn',
	                  width:200,
	                  dataIndex: 'custname',
	                  text: '<br/>客户简称'
	              },
	              {
	                  xtype: 'gridcolumn',
	                  dataIndex: 'materialname',
	                  text: '<br/>产品名称'
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
                    text: '<br/>年用量',
                    randerer:function(value){
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'applypriceinc',
                    align:'right',
                    width:120,
                    text: ' 客户期望价格<br/>（含税）'
                },
               
                {
                    xtype: 'gridcolumn',
                    align:'right',
                    dataIndex: 'applyprice',
                    width:120,
                    text: '客户期望价格<br/>（未税）'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'remark',
                    text: '<br/> 申请说明'
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
                    width:120,
                    text: '竞争对手价格<br/>（含税）'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'currencys',
                    text: '币种	'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'isnotrebate',
                    text: '<br/>一次性',
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
                    dataIndex: 'istax',
                    text: '<br/>是否含税',
                    renderer:function(value){
                        var result='';
                        if(value==1){
                            result='是'
                        }else{
                            result='否'
                        }
                        return result;
                    }

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
                            text: '审批',
                            iconCls: 'common_icon_approve',
                            listeners: {
                                click: 'onApproveClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '待处理',
                            iconCls: 'common_icon_approve',
                            listeners: {
                                click: 'onWaitClick'
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