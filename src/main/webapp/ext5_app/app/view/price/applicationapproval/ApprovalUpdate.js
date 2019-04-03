
Ext.define('BPSPortal.view.price.applicationapproval.ApprovalUpdate', {
    extend: 'Ext.form.Panel',
    alias: 'widget.approvalupdate',

    requires: [
        'BPSPortal.view.price.applicationapproval.ApprovalDetailViewModel',
        'BPSPortal.view.price.applicationapproval.ApprovalDetailViewController',
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
        type: 'approvaldetail'
    },
    controller : 'approvaldetail',
    listeners: {
    	  afterrender: 'onAfterRender'
      },
    scrollable: true,
    title: '修改',
    closable: true,
    items: [
        {
        	xtype:'fieldset',
        	height:350,
        	title:'申请信息',
        	items:[
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
                               readOnly:true,
                               fieldLabel: '单据编号',
                               width: 300,
                               labelWidth: 150,
                               name:'billno',
                               bind: {
           	                         value: '{data.billno}'
           	                }
                           },
                           {
                               xtype: 'textfield',
                               fieldLabel: 'spid',
                               hidden:true,
                               name:'spid',
                               bind: {
           	                         value: '{data.spid}'
           	                }
                           },
                           {
                               xtype: 'textfield',
                               fieldLabel: 'id',
                               hidden:true,
                               name:'id',
                               bind: {
           	                         value: '{data.spid}'
           	                }
                           },
                           {
                               xtype: 'textfield',
                               fieldLabel: 'did',
                               hidden:true,
                               name:'did',
                               bind: {
           	                         value: '{data.did}'
           	                }
                           },
                           {
                               xtype: 'combobox',
                               fieldLabel: '审批状态',
                               width: 300,
                               labelWidth: 150,
                               readOnly:true,
                               emptyText: "请选择",
                               padding: '0 0 0 50',
                               displayField: 'code',
                               valueField: 'value',
                               name: 'billstatus',
                               bind: {
                                   store: '{billstatus}',
                                   value:'{data.billstatus}'

                               }

                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               width: 300,
                               labelWidth: 150,
                               readOnly:true,
                               name:'dealername',
                               fieldLabel: '经销商',
                               bind: {
      	                         value: '{data.dealername}'
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
                               fieldLabel: '客户名称',
                               width: 300,
                               labelWidth: 150,
                               readOnly:true,
                               name:'custname',
                               bind: {
         	                         value: '{data.custname}'
         	                      }
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               readOnly:true,
                               fieldLabel: '区域',
                               name:'region',
                               width: 300,
                               labelWidth: 150,
                               bind: {
        	                         value: '{data.region}'
        	                      }
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               fieldLabel: '产品线' ,
                               readOnly:true,
                               width: 300,
                               labelWidth: 150,
                               name:'prodname',
                               bind: {
        	                         value: '{data.prodname}'
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
                               readOnly:true,
                               fieldLabel: '  产品编号',
                               width: 300,
                               labelWidth: 150,
                               name:'materialcode',
                               bind: {
         	                         value: '{data.materialcode}'
         	                      }
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               name:'materialname',
                               fieldLabel: '产品名称',
                               width: 300,
                               labelWidth: 150,
                               readOnly:true,
                               bind: {
         	                         value: '{data.materialname}'
         	                      }
                           },
                           {
                               xtype: 'combobox',
                               padding: '0 0 0 50',
                               name:'isnotrebate',
                               width: 300,
                               labelWidth: 150,
                               fieldLabel: '一次性',
                               readOnly:true,
      		                   displayField: 'code',
      						   valueField: 'value',
                               bind: {
         	                         value: '{data.isnotrebate}',
         	                         store:'{noOrYes}'
         	                   },
         	                  renderer:function(value){
                                    if (value != null) {
                                    	if(value==1){
                                    		return '是'
                                    	}else{
                                    		return '否'
                                    	}
                                  }
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
                               fieldLabel: '项目名称',
                               readOnly:true,
                               width: 300,
                               labelWidth: 150,
                               name:'projname',
                               bind: {
         	                         value: '{data.projname}'
         	                      }
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               fieldLabel: '项目状态',
                               width: 300,
                               labelWidth: 150,
                               name:'projstatus',
                               readOnly:true,
                               bind: {
         	                         value: '{data.projstatus}'
         	                      }
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               fieldLabel: '年用量',
                               width: 300,
                               labelWidth: 150,
                               readOnly:true,
                               name:'volyear',
                               bind: {
         	                         value: '{data.volyear}'
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
                               fieldLabel: '  竞争对手型号',
                               width: 300,
                               labelWidth: 150,
                               readOnly:true,
                               name:'compmaterial',
                               bind: {
         	                         value: '{data.compmaterial}'
         	                      }
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               width: 300,
                               labelWidth: 150,
                               fieldLabel: '  竞争对手价格（含税）',
                               readOnly:true,
                               name:'comppriceinc',
                               bind: {
         	                         value: '{data.comppriceinc}'
         	                      }
                           }, 
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               width: 300,
                               labelWidth: 150,
                               fieldLabel: '币种',
                               readOnly:true,
                               name:'currency',
                               bind: {
         	                         value: '{data.currencys}'
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
                               readOnly:true,
                               width: 300,
                               labelWidth: 150,
                               fieldLabel: '  客户期望价格（含税）',
                               name:'applypriceinc',
                               bind: {
         	                         value: '{data.applypriceinc}'
         	                      }
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               readOnly:true,
                               fieldLabel: '  客户期望价格（未税） ',
                               width: 300,
                               labelWidth: 150,
                               name:'applyprice',
                               bind: {
         	                         value: '{data.applyprice}'
         	                      }
                           },
                           {
                               xtype: 'combobox',
                               padding: '0 0 0 50',
                               name:'istax',
                               width: 300,
                               labelWidth: 200,
                               fieldLabel: '是否含税',
                               displayField: 'code',
                               valueField: 'value',
                               bind: {
                                   value: '{data.istax}',
                                   store:'{noOrYes}'
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
                               xtype: 'textareafield',
                               flex: 1,
                               width: 1473,
                               readOnly:true,
                               fieldLabel: '特价理由',
                               labelWidth: 150,
                               name:'remark',
                               bind: {
         	                         value: '{data.remark}'
         	                      }
                           }
                       ]
                   }
	        ]
        	
        },
        {
        	xtype:'fieldset',
        	height:200,
        	title:'最近一次特价信息',
        	items:[
        	          {
                          xtype: 'container',
                          padding: '10 0 0 0',
                          layout: {
                              type: 'hbox',
                              align: 'stretch'
                          },
                          items: [
                              {
                            	  xtype: 'numberfield',
                                  decimalPrecision:4,  //精确到小数点后4位 
                                  allowDecimals:true,
                                  fieldLabel: '  上次审批特价（含税）',
                                  width: 300,
                                  labelWidth: 150,
                                  readOnly:true,
                                  name:'lastsplinc',
                                  bind: {
          	                         value: '{data.lastsplinc}'
          	                      }
                              },
                              {
                            	  xtype: 'numberfield',
                                  decimalPrecision:4,  //精确到小数点后4位 
                                  width: 300,
                                  labelWidth: 150,
                                  allowDecimals:true,
                                  padding: '0 0 0 50',
                                  fieldLabel: '  上次审批特价（未税）',
                                  readOnly:true,
                                  name:'lastspl',
                                  bind: {
          	                         value: '{data.lastspl}'
          	                      }
                              },
                              {
                                  xtype: 'textfield',
                                  
                                  //xtype: 'datefield',
                                  padding: '0 0 0 50',
                                  fieldLabel: '上次审批执行时间',
                                  width: 300,
                                  labelWidth: 150,
                                //  format: 'Y-m-d',
                                  readOnly:true,
                                  name:'lastdate',
                                  bind: {
          	                         value: '{data.lastdate}'
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
                            	  xtype: 'numberfield',
                                  decimalPrecision:4,  //精确到小数点后4位 
                                  width: 300,
                                  labelWidth: 150,
                                  allowDecimals:true,
                                  fieldLabel: '上次执行价格',
                                  readOnly:true,
                                  name:'lastcustspl',
                                  bind: {
          	                         value: '{data.lastcustspl}'
          	                      }
                              },
                              {
                            	  xtype: 'numberfield',
                                  decimalPrecision:4,  //精确到小数点后4位 
                                  width: 300,
                                  labelWidth: 150,
                                  allowDecimals:true,
                                  padding: '0 0 0 50',
                                  fieldLabel: '上次实行价格(含税)',
                                  readOnly:true,
                                  name:'lastcustsplinc',
                                  bind: {
          	                         value: '{data.lastcustsplinc}'
          	                      }
                              },
                              {
                            	  xtype: 'numberfield',
                                  decimalPrecision:4,  //精确到小数点后4位 
                                  width: 300,
                                  labelWidth: 150,
                                  padding: '0 0 0 50',
                                  allowDecimals:true,
                                  fieldLabel: '  上次审批利润率',
                                  readOnly:true,
                                  name:'lastsugdealerprofit',
                                  bind: {
          	                         value: '{data.lastsugdealerprofit}'
          	                      }
                              },
                              {
                               xtype:'label',
                           	   text:'%'
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
                                  fieldLabel: '上次执行出货日期',
                                  width: 300,
                                  labelWidth: 150,
                                  format: 'Y-m-d',
                                  readOnly:true,
                                  name:'lastcustdate',
                                  bind: {
          	                         value: '{data.lastcustdate}'
          	                      }
                              },
                              {
                                  xtype: 'textfield',
                                  padding: '0 0 0 50',
                                  width: 300,
                                  labelWidth: 150,
                                  fieldLabel: '  上次执行 数量',
                                  readOnly:true,
                                  name:'lastquantity',
                                  bind: {
          	                         value: '{data.lastquantity}'
          	                      }
                              },
                              {
                                  xtype: 'textfield',
                                  padding: '0 0 0 50',
                                  fieldLabel: 'BPS销售',
                                  width: 300,
                                  labelWidth: 150,
                                  readOnly:true,
                                  name:'bpssales',
                                  bind: {
          	                         value: '{data.bpssales}'
          	                      }
                              }
                          ]
                      }
        	]
        	
        	
        },
        {
        	xtype:'fieldset',
        	height:300,
        	title:'审批信息',
        	items:[
                   {
                       xtype: 'container', 
                       padding: '10 0 0 0',
                       layout: {
                           type: 'hbox',
                           align: 'stretch'
                       },
                       items: [
							{
								xtype: 'numberfield',
                                decimalPrecision:4,  //精确到小数点后4位 
                                allowDecimals:true,
                                width: 350,
                                labelWidth: 200,
							    fieldLabel: '<font color ="red">* </font>建议客户 出货价格（含税）',
							    allowBlank:false,
							    name:'sugcustsplinc',
							    regex:/^\d+(\.\d+)?$/,
                                regexText:'请输入正确的格式',
                                bind: {
         	                         value: '{data.sugcustsplinc}'
         	                      },
                                   listeners:{
                                	   blur:function(value){
                                		   var sugcustsplinc = this.up('form').down('numberfield[name=sugcustsplinc]').getValue();
                                		   if(sugcustsplinc!=''&&sugcustsplinc!=null){
                                    		   var sugcustspl = this.up('form').down('numberfield[name=sugcustspl]');
                                    		   sugcustspl.setValue(sugcustsplinc / 1.13);
                                		   }
                                      	   var sugdealersplinc = this.up('form').down('numberfield[name=sugdealersplinc]').getValue();
                                      	   var sugdealerprofit = this.up('form').down('numberfield[name=sugdealerprofit]');
                                      	   if(sugcustsplinc!=null&&sugcustsplinc!=''&&sugcustsplinc!=0&&sugdealersplinc!=null&&sugdealersplinc!=''){
                                     		  var num=(sugcustsplinc-sugdealersplinc);
                                        	  var count=num/sugdealersplinc;
                                        	  sugdealerprofit.setValue(count*100);
                                     	  }
                                		   
                                	   }
                                   }
							},
                           {
							   xtype: 'numberfield',
	                           decimalPrecision:4,  //精确到小数点后4位 
	                           allowDecimals:true,
							   padding: '0 0 0 50',
							   width: 350,
                               labelWidth: 200,
                               allowBlank:false,
                               fieldLabel: '<font color ="red">* </font>建议客户  出货价格（未税）',
                               name:'sugcustspl',
                               regex:/^\d+(\.\d+)?$/,
                               regexText:'请输入正确的格式',
                               bind: {
       	                         value: '{data.sugcustspl}'
       	                      },
                               listeners:{
                            	   blur:function(value){
                            		   var sugcustspl = this.up('form').down('numberfield[name=sugcustspl]').getValue();
                            		   if(sugcustspl!=''&&sugcustspl!=null){
                                		   var sugcustsplinc = this.up('form').down('numberfield[name=sugcustsplinc]');
                                		   sugcustsplinc.setValue(sugcustspl * 1.13);
                            		   }
                            		   var sugcustsplinc = this.up('form').down('numberfield[name=sugcustsplinc]').getValue();
                                  	   var sugdealersplinc = this.up('form').down('numberfield[name=sugdealersplinc]').getValue();
                                  	   var sugdealerprofit = this.up('form').down('numberfield[name=sugdealerprofit]');
                                  	   if(sugcustsplinc!=null&&sugcustsplinc!=''&&sugcustsplinc!=0&&sugdealersplinc!=null&&sugdealersplinc!=''){
                                  		 var num=(sugcustsplinc-sugdealersplinc);
	                                   	  var count=num/sugdealersplinc;
	                                   	  sugdealerprofit.setValue(count*100);
                                 	  }
                            		   
                            	   }
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
                        	   xtype: 'numberfield',
                        	   width: 350,
                               labelWidth: 200,
                               decimalPrecision:4,  //精确到小数点后4位 
                               allowDecimals:true,
                               fieldLabel: '<font color ="red">* </font>建议经销商成本价（含税）',
                               allowBlank:false,
                               name:'sugdealersplinc',
                               regex:/^\d+(\.\d+)?$/,
                               regexText:'请输入正确的格式',
                               bind: {
       	                         value: '{data.sugdealersplinc}'
       	                      },
                               listeners:{
                            	   blur:function(value){
                            		   
                            		   var sugdealersplinc = this.up('form').down('numberfield[name=sugdealersplinc]').getValue();
                            		   if(sugdealersplinc!=''&&sugdealersplinc!=null){
                            			   var sugdealerspl = this.up('form').down('numberfield[name=sugdealerspl]');
                            			   sugdealerspl.setValue(sugdealersplinc / 1.13);
                            		   } 
                            		   
                            		   var sugcustsplinc = this.up('form').down('numberfield[name=sugcustsplinc]').getValue();
                                  	   var sugdealerprofit = this.up('form').down('numberfield[name=sugdealerprofit]');
                                  	   if(sugcustsplinc!=null&&sugcustsplinc!=''&&sugcustsplinc!=0&&sugdealersplinc!=null&&sugdealersplinc!=''){
                                  		 var num=(sugcustsplinc-sugdealersplinc);
	                                   	  var count=num/sugdealersplinc;
	                                   	  sugdealerprofit.setValue(count*100);
                                 	  }
                            	   }
                               }
                           },
                           {
                               xtype: 'numberfield',
                               decimalPrecision:4,  //精确到小数点后4位 
                               allowDecimals:true,
                               width: 350,
                               labelWidth: 200,
                               padding: '0 0 0 50',
                               fieldLabel: '<font color ="red">* </font>建议经销商成本价（未税）',
                               allowBlank:false,
                               name:'sugdealerspl',
                               regex:/^\d+(\.\d+)?$/,
                               regexText:'请输入正确的格式',
                               bind: {
       	                         value: '{data.sugdealerspl}'
       	                      },
                               listeners:{
                            	   blur:function(value){
                            		   var sugdealerspl = this.up('form').down('numberfield[name=sugdealerspl]').getValue();
                            		   if(sugdealerspl!=''&&sugdealerspl!=null){
                            			   var sugdealersplinc = this.up('form').down('numberfield[name=sugdealersplinc]');
                                		   sugdealersplinc.setValue(sugdealerspl * 1.13);
                            		   }
                            		   var sugcustsplinc = this.up('form').down('numberfield[name=sugcustsplinc]').getValue();
                                  	   var sugdealersplinc = this.up('form').down('numberfield[name=sugdealersplinc]').getValue();
                                  	   var sugdealerprofit = this.up('form').down('numberfield[name=sugdealerprofit]');
                                  	   if(sugcustsplinc!=null&&sugcustsplinc!=''&&sugcustsplinc!=0&&sugdealersplinc!=null&&sugdealersplinc!=''){
                                  		 var num=(sugcustsplinc-sugdealersplinc);
	                                   	  var count=num/sugdealersplinc;
	                                   	  sugdealerprofit.setValue(count*100);
                                 	  }
                            		  
                            		   
                            	   }
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
                        	   xtype: 'numberfield',
                               decimalPrecision:4,  //精确到小数点后4位 
                               allowDecimals:true,
                               width: 350,
                               labelWidth: 200,
                               readOnly:true,
                               fieldLabel: '经销商利润率',
                               name:'sugdealerprofit',
                               bind: {
       	                         value: '{data.sugdealerprofit}'
       	                      }
                               
                           },
                           {
                        	   xtype:'label',
                        	   text:'%'
                           },
                           {
     						    xtype: 'combobox',
     						    padding: '0 0 0 40',
     						    fieldLabel: '<font color ="red">* </font>币种',
     						    allowBlank:false,
     						    displayField: 'code',
     						    valueField: 'value',
     						    width: 350,
                                labelWidth: 200,
     						    name: 'currencys',
     						    bind: {
     						    	 value: '{data.currencys}',
     						        store: '{currencytype}'
     						
     						    }
     						},
     						,
      						{
                                xtype: 'textfield',
                                padding: '0 0 0 40',
                                name:'materialname',
                                fieldLabel: '<font color ="red">* </font>产品名称',
                                width: 350,
                                labelWidth: 200,
                                readOnly:true,
                                bind: {
          	                         value: '{data.materialname}'
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
   						    fieldLabel: '<font color ="red">* </font>特价出货',
   						    emptyText: "全部",
   						    allowBlank:false,
   						    displayField: 'code',
   						    width: 350,
                            labelWidth: 200,
   						    valueField: 'value',
   						    //value:'0',
   						    name: 'isspl',
   						    bind: {
   						        store: '{noOrYes}',
   						        value: '{data.isspl}'
   						
   						    }
   						},
   						{
   						    xtype: 'combobox',
   						    fieldLabel: '<font color ="red">* </font>特价返货',
   						    emptyText: "全部",
                            padding: '0 0 0 50',
   						    allowBlank:false,
   						    width: 350,
                            labelWidth: 200,
   						   // value:'0',
   						    displayField: 'code',
   						    valueField: 'value',
   						    name: 'isrebate',
   						    bind: {
   						        store: '{noOrYes}',
   						        value: '{data.isrebate}'
   						
   						    }
   						},
   						,
   						{
 						    xtype: 'combobox',
 						    padding: '0 0 0 50',
 						    fieldLabel: '<font color ="red">* </font>产品编码',
 						    editable:false,
 						    width: 350,
                            labelWidth: 200,
 						    displayField: 'materialcode',
 						    valueField: 'id',
 						    name: 'materialid',
 						    bind: {
 						        store: '{material}',
 						        value: '{data.materialid}'
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
                               width: 350,
                               labelWidth: 200,
                               fieldLabel: '<font color ="red">* </font>生效日期',
                               format: 'Y-m-d',
                               allowBlank:false,
                               bind: {
       	                         value: '{data.activedate}'
       	                      },
                               name:'activedate'
                           },
                           {
                               xtype: 'textfield',
                               padding: '0 0 0 50',
                               fieldLabel: '特价批复号',
                               width: 350,
                               labelWidth: 200,
                               bind: {
       	                         value: '{data.splno}'
       	                      },
                               name:'splno'
                           },
                           {
                               xtype: 'combobox',
                               padding: '0 0 0 50',
                               name:'isnotrebates',
                               width: 300,
                               labelWidth: 200,
                               fieldLabel: '一次性',
      		                   displayField: 'code',
      						   valueField: 'value',
                               bind: {
         	                         value: '{data.isnotrebate}',
         	                         store:'{noOrYes}'
         	                   }
                           }
                       ]
                   },
                   {
                       xtype: 'textareafield',
                       padding: '10 0 0 0',
                       width: 1473,
                       fieldLabel: '审批意见',
                       labelWidth: 200,
                       bind: {
	                         value: '{data.auditremark}'
	                      },
                       name:'auditremark'
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
                              text: '保存',
                              iconCls: 'common_icon_save',
                              listeners: {
                                  click: 'onSaveClick'
                              }
                          },
                          {
                              xtype: 'button',
                              text: '取消',
                              iconCls:'common_icon_cancel',
                              listeners: {
                                  click: 'onCancelClick'
                              }
                          }
                      ]
                  }
              ]

});