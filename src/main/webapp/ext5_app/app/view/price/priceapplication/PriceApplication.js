
Ext.define('BPSPortal.view.price.priceapplication.PriceApplication',
				{
					extend : 'Ext.form.Panel',
					alias : 'widget.priceapplication',

					requires : [
							'BPSPortal.view.price.priceapplication.PriceApplicationViewModel',
							'BPSPortal.view.price.priceapplication.PriceApplicationViewController',
							'Ext.form.FieldSet',
							'Ext.form.field.Text',
							'Ext.toolbar.Toolbar',
							'Ext.toolbar.Fill',
							'Ext.button.Button', 
							'Ext.grid.Panel',
							'Ext.grid.column.Column',
							'Ext.view.Table' ],

					viewModel : {
						type : 'priceapplication'
					},
					controller : 'priceapplication',
					title : '特价申请',
					closable : true,
					scrollable : true,
					listeners : {
						afterrender : 'onAfterRender'
					},
					items : [
							{
								xtype : 'container',
								padding : '10 0 0 0',
								layout : {
									type : 'hbox',
									align : 'stretch'
								},
								items : [ {
									xtype : 'textfield',
									fieldLabel : '单据编号',
									readOnly:true,
									name : 'billno'
								}, {
									xtype : 'combobox',
									padding : '0 0 0 50',
									fieldLabel : '申请类型',
									emptyText : "请选择",
									editable : false,
									value:'折扣',
									displayField : 'code',
									valueField : 'value',
									name : 'applytype',
									bind : {
										store : '{applytype}'

									}

								} ]
							},
							{
								xtype : 'container',
								padding : '10 0 0 0',
								layout : {
									type : 'hbox',
									align : 'stretch'
								},
								items : [ {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '制单人',
									reference : 'applicator',
									name : 'applicator'
								}, {
									xtype : 'datefield',
									padding : '0 0 0 50',
									fieldLabel : '制单时间',
									name : 'applydate',
									readOnly : true,
									value : new Date(),
									format : 'Y-m-d'
								} ]
							},
							{
								xtype : 'gridpanel',
								minHeight : 200,
								reference : 'pricespecialdetail',
								bind : {
									store : '{pricespecialdetailstore}'
								},
								plugins : [ {
									ptype : 'cellediting',
									clicksToEdit:1
								} ],
								  viewConfig:{
						            	enableTextSelection:true
						            },
								title : '',
								columns : [
										{
											xtype : 'gridcolumn',
											dataIndex : 'seqno',
											text : '<br/>行号'
										},
										{
											xtype : 'gridcolumn',
											dataIndex : 'applytype',
											text : '<br/><font color ="red">* </font>申请类型',
											renderer : function(value) {
												var type=this.up('form').down('combobox[name=applytype]').getValue();
												if (type != null) {
													return type;
												}
											}
										},
										{
		                                    xtype: 'gridcolumn',
		                                    dataIndex: 'cpid',
		                                    width:250,
		                                    text: '<br/><font color ="red">* </font>客户名称',
		                                    editor: {
		                                        xtype: 'customerpicker',
		                                        displayField: 'custname',
		                    				    valueField: 'cpid',
		                    				    name:'cpid'
		                                   },
										renderer : function(value) {
											if (value != null&&value !=''&&value !=-1&&value !=undefined) {
												var gridStore = this.up('form').getViewModel().getStore('custprodline');
												if((gridStore.findRecord('cpid',value))==null){
									            	return null;
												}else{
													return gridStore.findRecord('cpid',value).data.custname;
												}
											}else{
												return "";
											}
										}
		                                },
										{
											xtype : 'gridcolumn',
											dataIndex : 'materialid',
											width:200,
											text : '<br/><font color ="red">* </font>产品名称',
											editor : {
												xtype: 'materialpickers',
		                                        displayField: 'materialname',
		                    				    valueField: 'id',
		                    				    name:'materialid'

											},
											renderer : function(value) {
												if (value != null&&value !=''&&value !=-1&&value !=undefined) {
													var gridStore = this.up('form').getViewModel().getStore('material');
													var grid=Ext.ComponentQuery.query("viewport priceapplication grid")[0];
													var record = grid.getSelectionModel().getSelection()[0];
													if(gridStore.findRecord('id',value)==null){
										            	return null ;
													}else{
														var name = gridStore.findRecord('id',value).data.materialcode;
														record.set('materialcode',name);
														return gridStore.findRecord('id',value).data.materialname;
													}
													
												}else{
													return "";
												}
											}
										}, 
										{
											xtype : 'gridcolumn',
											dataIndex : 'materialcode',
											text : '<br/><font color ="red">* </font>产品编号'
										}, 
										{
											xtype : 'gridcolumn',
											dataIndex : 'projname',
											text : '<br/><font color ="red">* </font>项目名称',
											editor : {
												xtype : 'textfield',
												name : 'projname'
												

											}
										}, 
										{
											xtype : 'gridcolumn',
											dataIndex : 'projstatus',
											text : '<br/><font color ="red">* </font>项目状态',
											editor: {
		                                    	  editable:false,
			          		                      xtype: 'combobox',
			          		                      bind: {
			          		                         store: '{projtype}'
			          		                      },
			          		                      displayField: 'code',
			          						      valueField: 'value',
			          						      name:'projstatus'
		                                    }
										}, 
										{
											xtype : 'gridcolumn',
											dataIndex : 'volyear',
											text : '<br/><font color ="red">* </font>年用量',
											editor : {
												xtype : 'textfield',
												allowBlank : false,
												name : 'volyear'
											}
										}, {
											xtype : 'gridcolumn',
											dataIndex : 'compmaterial',
											text : '<br/><font color ="red">* </font>竞争对手型号',
											editor : {
												xtype : 'textfield',
												name : 'compmaterial'
											}
										}, 
										{
											xtype : 'numbercolumn',
											dataIndex : 'comppriceinc',
											text : '<font color ="red">* </font>竞争对手价格<br/>（含税）',
											align:'right',
											format:'0,000.000', 
											editor : {
												xtype : 'numberfield',
												decimalPrecision:3,  
												allowDecimals:true,
												minValue:0,
												name : 'comppriceinc'
													
											}
										}, 
										{
											xtype : 'numbercolumn',
											dataIndex : 'applypriceinc',
											text : '<font color ="red">* </font>客户期望价格<br/>（含税）',
											align:'right',
											format:'0,000.000', 
											editor : {
												xtype : 'numberfield',
												name : 'applypriceinc',
												decimalPrecision:3,  
												allowDecimals:true,
												listeners: {
			                                           blur:function(value){
			                                        	   var count=value.getValue();
//			                                        	  // console.log(count);
			                                        	   if((count!=null&&count!='') || count==0){
				                                        	//   console.log(count);
			                                        		   var grid=Ext.ComponentQuery.query("viewport priceapplication grid")[0];
				                                        	   var record = grid.getSelectionModel().getSelection()[0];
				                                        	   record.set('applyprice',count / 1.130);
			                                        	   }
			                                        	  
			                                           }
			                                       }
											}
										},
										{
											xtype : 'numbercolumn',
											dataIndex : 'applyprice',
											align:'right',
											text : '<font color ="red">* </font>客户期望价格<br/>（未税）',
											format:'0,000.000', 
											editor : {
												xtype : 'numberfield',
												name : 'applyprice',
												decimalPrecision:3,
												allowDecimals:true,
												listeners: {
			                                           blur:function(value){
			                                        	   var count=value.getValue();
			                                        	   if((count!=null&&count!='') || count==0){
			                                        		   var grid=Ext.ComponentQuery.query("viewport priceapplication grid")[0];
				                                        	   var record = grid.getSelectionModel().getSelection()[0];
				                                        	   record.set('applypriceinc',count * 1.130);
			                                        	   }
			                                        	  
			                                           }
			                                       }
											}
											
											
											 
										}, 
										
										{
											xtype : 'gridcolumn',
											dataIndex : 'remark',
											text : '<br/><font color ="red">* </font>申请说明',
											editor : {
												xtype : 'textfield',
												name : 'remark'
											}
										}, 
										{
											xtype : 'gridcolumn',
											dataIndex : 'currency',
											text : '<br/><font color ="red">* </font>币种',
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
		                                    xtype: 'gridcolumn',
		                                    dataIndex: 'isnotrebate',
		                                    text: '<br/><font color ="red">* </font>一次性',
		                                    editor: {
		                                    	  editable:false,
			          		                      xtype: 'combobox',
			          		                      bind: {
			          		                         store: '{noOrYes}'
			          		                      },
			          		                      displayField: 'code',
			          						      valueField: 'value',
			          						      name:'isnotrebate'
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
		                                },
										{
											xtype : 'widgetcolumn',
											text : '操作',
											widget : {
												xtype : 'button',
												width : 100,
												text : '删除',
												listeners : {
													click : 'onDelClice'
												}
											}
										} ],
								dockedItems : [ 
								  {
									xtype : 'toolbar',
									dock : 'top',
									items : [
									{
										xtype : 'button',
										text : '新增',
										iconCls: 'common-icon-add',
										listeners : {
											click : 'onAddClice'
										}
									} ]
								} ]
							} ],
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'bottom',
						items : [  
						{
							xtype : 'button',
							text : '提交',
							iconCls:'common_icon_save',
							listeners : {
								click : 'onSumbitClick'
							}
						} ]
					} ]

				});