
Ext.define('BPSPortal.view.price.pricepolicy.PricePolicy', {
    extend: 'Ext.form.Panel',
    alias: 'widget.pricepolicy',

    requires: [
        'BPSPortal.view.price.pricepolicy.PricePolicyViewModel',
        'BPSPortal.view.price.pricepolicy.PricePolicyViewController',
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
        type: 'pricepolicy'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    controller : 'pricepolicy',
    title: '价格政策表',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'form',
           // layout: 'column',
            itemId:'uploadForm',
            height: 200,
            bodyPadding: 10,
            title: '',
            api:{
            	submit : pricestrategydetailcontroller.uploadAttachment
            },
            items: [
                {
                    xtype: 'container',
                    padding: '0 0 10 0 ',
                    layout: 'table',
                    items: [
                        {
                        	 xtype: 'combobox',
                             fieldLabel: '价格政策编码',
                             emptyText: "请选择",
                             displayField: 'code',
                             valueField: 'sid',
                             name: 'code',
                             bind: {
                                 store: '{pricecode}'

                             }
                        },
                        {
                            xtype: 'button',
                            margin: '0 0 0 50',
                            text: '下载',
                            listeners:{
                            	click:'onDownloadClick'
                            }
                        },
                        {
                        	xtype : 'fileuploadfield',
                        	padding:'0 0 0 50',
                            buttonConfig : {
                                iconCls: 'common_icon_export_excel'
                            },
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
                    padding: '0 0 10 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '政策编码',
                            reference:'codes',
                            name:'codes'
                        },
                        {
						    xtype: 'datefield',
						    fieldLabel: '发布时间',
						    padding: '0 0 0 10',
						    format: 'Y-m-d',
						    reference:'publicdates',
						    name:'publicdate'
						},
						{
							 padding: '0 0 0 10',
                            xtype: 'combobox',
                            fieldLabel: '状态',
                            readOnly:true,
                            name:'status'
                        }
                    ]
                },
                {
                    xtype: 'container',
                    padding: '0 0 10 0',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                         {
                            xtype: 'datefield',
                            fieldLabel: '生效时间',
                            format: 'Y-m-d',
                            name:'validfrom'
                        },
                        {
                            xtype: 'datefield',
                            padding: '0 0 0 10',
                            fieldLabel: '失效时间',
                            format: 'Y-m-d',
                            name:'validto'
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
                            text: '  调价试算',
                            hidden:true,
                            iconCls:'common_icon_jisuan',
                            listeners:{
                            	click:'onTrialClick'
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'common-icon-add',
                            text: '  新增',
                            listeners:{
                            	click:'onAddClick'
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
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'gridpanel',
            height: 350,
            title: '',
            itemId:'pricegrid',
            bind:{
            	store: '{price}'
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
                    dataIndex: 'code',
                    hidden:true,
                    text: '  政策编码',
                    editor: {
                        xtype: 'textfield',
                        name: 'code'
                       }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'sid',
                    hidden:true
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'errormsg',
                    text: 'ErrorMsg'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'ispublic',
                    text: '公开',
                    editor: {
                  	  editable:false,
	                      xtype: 'combobox',
	                      bind: {
	                         store: '{noOrYes}'
	                      },
	                      displayField: 'code',
					      valueField: 'value',
					      name:'ispublic'
                    },
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
  					xtype : 'gridcolumn',
					dataIndex : 'materialname',
					width:200,
					text : '产品名称',
					editor : {
						xtype: 'materialpickerss',
                        displayField: 'materialname',
    				    valueField: 'materialname',
    				    name:'materialname',
    				    listeners:{
    						select: function (val, record, eOpts) {
    							var value=record.data.id;
    							if(value != null&&value !=''&&value !=-1&&value !=undefined){
    								var grid=Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
    								var records = grid.getSelectionModel().getSelection()[0];
    								var code =record.data.materialcode;
    									records.set('materialcode',code);
									var assembly =record.data.assembly;
										records.set('assembly',assembly);
									var prodname =record.data.prodname;
										records.set('prodname',prodname);
    						    }
    					    },
    					    change:'getLastPrice'
    				    }
					}
				}, 
				{
					xtype : 'gridcolumn',
					dataIndex : 'materialcode',
					text : '  产品编号',
					 editor: {
						    xtype: 'combobox',
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
                    dataIndex: 'prodname',
                    text: '  产品线',
                    editor: {
                        xtype: 'textfield',
                           name: 'prodname'
                       }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'lifecycle',
                    text: '  生命周期',
                    editor: {
                        xtype: 'textfield',
                           name: 'lifecycle'
                       }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'ismain',
                    hidden:true,
                    text: '主推',
                    editor: {
                  	  editable:false,
                      xtype: 'combobox',
                      bind: {
                         store: '{noOrYes}'
                      },
                      displayField: 'code',
				      valueField: 'value',
				      name:'ismain'
                  },
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
                    dataIndex: 'assembly',
                    text: '封装',
                    editor: {
                        xtype: 'textfield',
                        name: 'assembly'
                    }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'eol',
                    text: 'EOL',
                    editor: {
                        xtype: 'datefield',
                        format: 'Y-m-d',
                        name: 'eol'
                    },
                   renderer: function (val) {
                        var dt = new Date(val);
                        var update_date = Ext.Date.format(dt, 'Y-m-d')
                        if (val ==  null ||val ==  undefined) {
                            return "";
                        } else{
                        	return update_date;
                        }         
                   }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'listprice',
                    align:'right',
                    width:150,
                    format:'0,000.0000', 
                    text: '市场价格(未税)',
                    editor: {
                    	xtype : 'numberfield',
						decimalPrecision:4,
						allowDecimals:true,
                        name: 'listprice',
                        listeners: {
                            blur:function(value){
                         	   var count=value.getValue();
                         	   if((count!=null&&count!='') || count==0){
                         		   var grid=Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
                             	   var record = grid.getSelectionModel().getSelection()[0];
                             	   record.set('listpriceinc',count * 1.130);
                             	   
                             	   
                             	  var dealerpriceinc=record.data.dealerpriceinc;
                             	  var listpriceinc=record.data.listpriceinc;
                            	   if(dealerpriceinc!=null&&dealerpriceinc!=''&&dealerpriceinc!=undefined){
                            		   
                            		   if(dealerpriceinc==0){
                            			   record.set('dealerprofit',0);
                            		   }else{
                            			   var num = listpriceinc/dealerpriceinc;
                                		   var num1=num-1;
                            			   record.set('dealerprofit',num1);
                            		   }
                            		  
                            	   }
                         	   }
                         	  
                            }
                        }
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'listpriceinc',
                    width:150,
                    align:'right',
                    format:'0,000.0000', 
                    text: '  市场价格（含税）',
                    editor: {
                    	xtype : 'numberfield',
						decimalPrecision:4,
						allowDecimals:true,
                        name: 'listpriceinc',
                        listeners: {
                            blur:function(value){
                         	   var count=value.getValue();
                         	   if((count!=null&&count!='') || count==0){
                         		   var grid=Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
                             	   var record = grid.getSelectionModel().getSelection()[0];
                             	   record.set('listprice',count / 1.130);
                             	   var dealerpriceinc=record.data.dealerpriceinc;
                             	   if(dealerpriceinc!=null&&dealerpriceinc!=''&&dealerpriceinc!=undefined){
                             		   if(dealerpriceinc==0){
                             			  record.set('dealerprofit',0);
                             		   }else{
                             			  var num = count/dealerpriceinc;
                                		  var num1=num-1;
                             			  record.set('dealerprofit',num1);
                             		   }
                             		   
                             		  
                             	   }
                             	   
                             	   
                         	   }
                         	  
                            }
                        }
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerprice',
                    align:'right',
                    format:'0,000.0000', 
                    width:180,
                    text: '经销商价格(未税)',
                    editor: {
                    	xtype : 'numberfield',
						decimalPrecision:4,
						allowDecimals:true,
                        name: 'dealerprice',
                            listeners: {
                                blur:function(value){
                             	   var count=value.getValue();
                             	   if((count!=null&&count!='') || count==0){
                             		   var grid=Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
                                 	   var record = grid.getSelectionModel().getSelection()[0];
                                 	   record.set('dealerpriceinc',count * 1.130);
                                 	   
                                 	   
                                 	  var dealerpriceinc=record.data.dealerpriceinc;
                                 	  var listpriceinc=record.data.listpriceinc;
                                 	  var lastprice=record.data.lastprice;
                                 	  if(dealerpriceinc==0){
                                 		 record.set('dealerprofit',0);
                                 	  }else{
                                 		 var num = listpriceinc/dealerpriceinc;
  	                        		     var num1=num-1;
  	                        		     record.set('dealerprofit',num1);
                                 	  }
                                 	  
	                        		  
	                        		   
	                        		   if(lastprice!=null&&lastprice!=''&&lastprice!=undefined){
	                        			   if(dealerpriceinc!=null&&dealerpriceinc!=''&&dealerpriceinc!=undefined){
	                        				   if(dealerpriceinc==0){
	                        					   record.set('reduceper',0);
	                        				   }else{
	                        					   var num2=lastprice-dealerpriceinc;
			                        			   var num3=num2/dealerpriceinc;
			                        			   record.set('reduceper',num3);
	                        				   }
		                        			   
	                        			   }
	                        		   }
                                		   
                                		  
                                		  
                                	   
                             	   }
                             	  
                                }
                            }
                       }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerpriceinc',
                    width:180,
                    format:'0,000.0000', 
                    align:'right',
                    text: '  经销商价格（含税）',
                    editor: {
                    	xtype : 'numberfield',
						decimalPrecision:4,
						allowDecimals:true,
                        name: 'dealerpriceinc',
                        listeners: {
                            blur:function(value){
                         	   var count=value.getValue();
                         	   var grid=Ext.ComponentQuery.query("viewport pricepolicy grid")[0];
                        	   var record = grid.getSelectionModel().getSelection()[0];
                         	   if((count!=null&&count!='') || count==0){
                             	   record.set('dealerprice',count / 1.130);
                             	   
                             	   
                             	   var listpriceinc=record.data.listpriceinc;
                             	   var dealerprice=record.data.dealerprice;
                             	   if(listpriceinc!=null&&listpriceinc!=''&&listpriceinc!=undefined){
                             		   if(count==0){
                             			  record.set('dealerprofit',0);
                             		   }else{
                             			  var num = listpriceinc/count;
                                 		  var num1=num-1;
                                 		  record.set('dealerprofit',num1);
                             		   }
                             		 
                             		 var lastprice=record.data.lastprice;
                             		 if(lastprice!=null&&lastprice!=''&&lastprice!=undefined){
                             			 if(count==0){
                             				record.set('reduceper',0); 
                             			 }else{
                              				var num2=lastprice-count;
                              				var num3=num2/count;
                              				record.set('reduceper',num3);
                             			 }
                          		     }
                             		  
                             	   }
                             	   
                             	   
                         	   }
                            }
                            }
                       }
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealerprofit',
                    text: '  经销商利润率',
                    width:150,
                    align:'right',
                    renderer: function(value){
                 	  var result='';
                    	 if(value!=null){
                    		 var num=Ext.util.Format.round(value, 3);
                    		 var s = Math.floor(num*100);
                    		 result=s;
                    	 }else{
                    		 result=0;
                    	 }
                          return result+'%' ;
                      }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'lastprice',
                    align:'right',
                    format:'0,000.000', 
                    text: '上期价格'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'reduceper',
                    align:'right',
                    text: '  降价幅度',
                     renderer: function(value){
                      	  var result='';
                        	 if(value!=null){
                        		 var num=Ext.util.Format.round(value, 3);
                        		 var s = Math.floor(num*100);
                        		 result=s;
                        	 }else{
                        		 result=0;
                        	 }
                              return result+'%' ;
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
                    dataIndex: 'custsp',
                    width:150,
                    format:'0,000.0000', 
                    align:'right',
                    text: '客户起始特价',
                    editor: {
                    	xtype : 'numberfield',
						decimalPrecision:4,
						allowDecimals:true,
                        name: 'custsp'
                       }
                },
                {
                    xtype: 'widgetcolumn',
                    width:150,
                    text: '操作',
                    widget: {
                        xtype: 'button',
                        style: {
                        	background: 'white',
                        	border: 0
                        },
                        iconCls:'common-icon-delete',
                        text: '<font color ="black">删除</font>',
                        listeners: {
                            click: 'onDeleteClick'
                        }
                        	
                    }
                }
            ]
        }
    ]

});