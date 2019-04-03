
Ext.define('BPSPortal.view.returngoods.salesupload.SalesUpload', {
    extend: 'Ext.form.Panel',
    alias: 'widget.salesupload',

    requires: [
        'BPSPortal.view.returngoods.salesupload.SalesUploadViewModel',
        'BPSPortal.view.returngoods.salesupload.SalesUploadViewController',
        'Ext.form.Panel',
        'Ext.form.Label',
        'Ext.button.Button',
        'Ext.form.field.File',
        'Ext.form.field.Date',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'salesupload'
    },
    listeners : {
		afterrender : 'onAfterRender'
	},
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    
    controller : 'salesupload',
    title: '销售数据上传',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'form',
            itemId:'uploadForm',
            height: 150,
            bodyPadding: 10,
            timeout:300,
            api:{
            	submit : uploadresalecontrol.uploadUploadReSale
            },
            items: [
                    {
                        xtype: 'container',
                        padding: '0 0 10 0 ',
                        layout: 'table',
                        items: [
                            {
                                xtype: 'button', 
                                text: '下     载',
                                listeners:{
                                	click:'onDownloadClick'
                                }
                            }
                        ]
                    },
                    
                    {
                        xtype: 'container',
                        padding: '0 0 10 0 ',
                        layout: 'table',
                        items: [
							{
	                            xtype: 'combobox',
	                            hidden:true,
	                            fieldLabel: '会计周期',
	                            displayField: 'code',
	                            valueField: 'value',
	                            reference:'periods',
	                            name: 'period',
	                            bind: {
	                                store: '{period}'
	                            }
							},
							{
	                               xtype:'label',
	                           	   text:'会计周期'
	                        },
	                        {
	                            xtype: 'combobox',
	                            padding: '0 0 0 30 ',
	                            fieldLabel: '年',
	                            width: 100,
	                            labelWidth: 30,
	                            displayField: 'code',
	                            valueField: 'value',
	                            reference:'years',
	                            name: 'year',
	                            bind: {
	                                store: '{year}'
	                            }
							},
							{
	                            xtype: 'combobox',
	                            width: 100,
	                            labelWidth: 30,
	                            fieldLabel: '月',
	                            displayField: 'code',
	                            valueField: 'value',
	                            reference:'months',
	                            name: 'month',
	                            bind: {
	                                store: '{month}'
	                            }
							},
                            {
                            	xtype : 'fileuploadfield',
//                                buttonConfig : {
//                                    iconCls: 'common_icon_export_excel'
//                                },
                            	padding: '0 0 0 20 ',
                                buttonOnly : true,
                                hideLabel : true,
                                buttonText : 'Upload',
                                name : 'fileUpload',
                                itemId : 'attachmentUploadBut',
                                listeners : {
                                    change : 'onUploadClick'
                                }
                            }
                        ]
                    },
                    {
                        xtype: 'container',
                        padding: '0 0 10 0 ',
                        layout: 'table',
                        items: [
                             {
	                            xtype: 'combobox',
	                            fieldLabel: '批次号',
	                            emptyText: "请选择",
	                            width: 350,
	                            labelWidth: 100,
	                            displayField: 'taskseq',
	                            valueField: 'id',
	                            name: 'taskseq',
	                            bind: {
	                                store: '{uploadtask}'
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
                             iconCls: 'common-icon-serach',
                             text: '查询',
                             itemId: 'searchBtn',
                             listeners: {
                                 click: 'onSearchClick'
                             }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common-icon-add',
                            text: '  新增',
                            hidden:true,
                            listeners:{
                            	click:'onAddClick'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common-icon-delete',
                            text: '  删除',
                            hidden:true,
                            listeners:{
                            	click:'onDeleteClick'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls:'common-icon-user-sure',
                            text: '验证',
                            listeners:{
                            	click:'onSaveClick'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls:'common_icon_save',
                            text: '提交',
                            listeners:{
                            	click:'onSaveClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '删除',
                            iconCls:'common-icon-delete',
                            listeners:{
                            	click:'onDeleteClick'
                            }
                        },
                        {
                            xtype: 'button',
                            text: '导出',
                            hidden:true,
                            iconCls:'common-icon-down',
                            listeners:{
                            	click:'onExportClick'
                            }
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'gridpanel',
            height: 300,
            title: '',
            reference:'uploadresalequerygrid',
            bind:{
            	store: '{uploadresale}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            plugins: [
                      {
                          ptype: 'cellediting',
                          clicksToEdit:1
                      }
                    ],
            listeners: {
                beforeedit: function (editor, context, eOpts) {
                    var sel = context.record;
                    if (context.field == 'materialcode') {
                    	var materialname=sel.data.materialname;
                    	var store = this.up('form').getViewModel().getStore('material');
                        var paras = { materialname: materialname };
                        Ext.apply(store.proxy.extraParams, paras);
                        store.load();   	
                    }
                }
            },
            columns: [
				{
				    xtype: 'gridcolumn',
				    dataIndex: 'errormsg',
				    width:200,
				    text: 'ErrorMsg'
				},
				{
				    xtype: 'gridcolumn',
				    dataIndex: 'id',
				    hidden:true,
				    text: 'id'
				},
				{
                    xtype: 'gridcolumn',
                    hidden:true,
                    dataIndex: 'tid',
                    text: 'tid'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'region',
                    text: '区域'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealername',
                    text: '经销商'
                  },

				{
					xtype : 'gridcolumn',
					dataIndex : 'seqno',
					width:200,
					text : '  序列号',
                    editor: {
                        xtype: 'textfield',
                        name: 'seqno'
                    }

				}, 
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custname',
                    width:200,
                    text: '订货单位',
                    editor: {
                        xtype: 'textfield',
                        name: 'custname'
                    }
                },
                {
					xtype : 'gridcolumn',
					dataIndex : 'materialname',
					width:200,
					text : '产品名称',
					editor : {
						xtype: 'materialpickers',
                        displayField: 'materialname',
    				    valueField: 'materialname',
    				    name:'materialname',
    				    listeners:{
    						select: function (val, record, eOpts) {
    						//	console.log(materialid);
    							var value=record.data.id;
    							if(value != null&&value !=''&&value !=-1&&value !=undefined){
    								var grid=Ext.ComponentQuery.query("viewport salesupload grid")[0];
    								var records = grid.getSelectionModel().getSelection()[0];
    								var code =record.data.materialcode;
    									records.set('materialcode',code);
    						    }
    					    }
    				    }
					}
				}, 
				{
					xtype : 'gridcolumn',
					dataIndex : 'materialcode',
					width:150,
					text : ' 产品编码',
					   editor: {
						    xtype: 'combobox',
						    editable:false,
						    displayField: 'materialcode',
						    valueField: 'materialcode',
						    name: 'materialcode',
						    bind: {
						        store: '{material}'
						    }
	                    }
				}, 
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'batchno',
                    hidden:true,
                    text: '批次号',
                    editor: {
                        xtype: 'textfield',
                        name: 'batchno'
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'qty',
                    format:'0,000', 
                    align:'right',
                    text: ' 出货数量',
                    editor:{
                    	xtype : 'numberfield',
						minValue:0,
						name:'qty'
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'paymenttype',
                    width:200,
                    text: '付款方式',
                    editor: {
                        xtype: 'textfield',
                        name: 'paymenttype'
                    }
                },
 
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'deliverydate',
                    text: '交货日期',
                    editor: {
                        xtype: 'datefield',
                        format: 'Y-m-d',
                        name: 'deliverydate'
                    },
                   renderer: function (val) {
                        var dt = new Date(val);
                        var update_date = Ext.Date.format(dt, 'Y-m-d')
                        if (val == "") {
                            return "";
                        }          
                        return update_date;
                   
                   }
                },
            	{
					xtype : 'gridcolumn',
					dataIndex : 'currency',
					text : '币种',
					editor : {
                      	  editable:false,
		                      xtype: 'combobox',
		                      bind: {
		                         store: '{currencytype}'
		                      },
		                      displayField: 'code',
						      valueField: 'value',
						      name:'currency'
					}
				},
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'unitprice',
                    format:'0,000.000', 
                    align:'right',
                    text: ' 单价',
                    editor:{
                    	xtype : 'numberfield',
						decimalPrecision:3,  
						allowDecimals:true,
						minValue:0,
						name:'unitprice'
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'istax',
                    text: '含税否',
                    editor: {
					    xtype: 'combobox',
					    editable:false,
					    displayField: 'code',
					    valueField: 'value',
					    name: 'istax',
					    bind: {
					        store: '{noOrYes}'
					    }
                    },
                    renderer : function(value) {
                    	var result='';
                    	if(value=='1'){
                    		result='是'
                    	}else{
                    		result='否';
                    	}
                    	return result;
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'contractamount',
                    format:'0,000.000',
                    align:'right',
                    text: '未税合同额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'isspl',
                    text: '  是否特价',
                    editor: {
					    xtype: 'combobox',
					    editable:false,
					    displayField: 'code',
					    valueField: 'value',
					    name: 'isspl',
					    bind: {
					        store: '{noOrYes}'
					    }
                    },
                     renderer : function(value) {
                     	var result='';
                     	if(value=='1'){
                     		result='是'
                     	}else{
                     		result='否';
                     	}
                     	return result;
                     }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerstdcost',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    text: '  经销商标准成本价（未税）',
                    editor: {
                    	xtype : 'numberfield',
						decimalPrecision:4,  
						allowDecimals:true,
                        name: 'dealerstdcost'
                     },
                     renderer: function (value, metaData, record) {
                    	    var updated = record.get('errormsg');
                    	    var text='|经销商标准成本价不匹配';
                            if (updated.indexOf(text)!=-1){ 
                            	return '<span style="color:' + "red" + ';">' + Ext.util.Format.number(value,'0,000.0000')  + '</span>';
                            }else {
                            	return  Ext.util.Format.number(value,'0,000.0000');
                           }
                    	    
                    	}

                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealersplcost',
                    text: '  经销商特价成本价（未税）',
                    width:200,
                    format:'0,000.0000',
                    align:'right',
                    editor: {
                    	xtype : 'numberfield',
						decimalPrecision:4,  
						allowDecimals:true,
                        name: 'dealersplcost'
                     },
                     renderer: function (value, metaData, record) {
                 	    var updated = record.get('errormsg');
                 	    var text='|经销商特价成本价不匹配';
                       if (updated.indexOf(text)!=-1){ 
                       	return '<span style="color:' + "red" + ';">' + Ext.util.Format.number(value,'0,000.0000')  + '</span>';
                       }else {
                       	return  Ext.util.Format.number(value,'0,000.0000');
                      }
                 	}
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerstdpo',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    text: '经销商标准采购额（未税）'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerstdact',
                    format:'0,000.0000',
                    align:'right',
                    hidden:true,
                    width:200,
                    text: '经销商实际采购额（未税）'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'rebateamount',
                    format:'0,000.00',
                    hidden:true,
                    align:'right',
                    width:200,
                    text: '  返货金额（未税）'

                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'bpsstdcost',
                    format:'0,000.0000',
                    align:'right',
                    hidden:true,
                    width:200,
                    text: '  BPS标准成本价（未税）'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'bpssplcost',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    hidden:true,
                    text: '  BPS特价成本价（未税）'
//                     }
                },
                {
                    xtype: 'numbercolumn',
                    format:'0,000.00',
                    dataIndex: 'rebatediff',
                    text: '  返货差异（未税）',
                    hidden:true,
                    width:200,
                    align:'right'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'remark',
                    width:150,
                    text: '备注',
                    hidden:true,
                    editor: {
                        xtype: 'textfield',
                        name: 'remark'
                     }
                }
            ]
        }
    ]

});