Ext
		.define(
				'BPSPortal.view.customer.customerBatc.CustomerBatchProc',
				{
					extend : 'Ext.form.Panel',
					alias : 'widget.customerbatchproc',

					requires : [
							'BPSPortal.view.customer.customerBatc.CustomerBatchProcViewModel',
							'BPSPortal.view.customer.customerBatc.CustomerBatchProcViewController',
							'Ext.form.Panel', 'Ext.form.field.ComboBox',
							'Ext.toolbar.Toolbar', 'Ext.grid.Panel',
							'Ext.grid.column.Number', 'Ext.grid.column.Date',
							'Ext.grid.column.Boolean', 'Ext.view.Table',
							'Ext.tab.Panel', 'Ext.tab.Tab' ],

					viewModel : {
						type : 'customerbatchproc'
					},
					controller : 'customerbatchproc',
					title : '客户批量处理',
					closable : true,
					scrollable : true,
					items : [
							{
								xtype : 'form',
								itemId : 'uploadForm',
								bodyPadding : 10,
								api : {
									submit : customerControl.customerBatchUpload
								},
								dockedItems : [ {
									xtype : 'toolbar',
									dock : 'bottom',
									items : [ {
										xtype : 'button',
										text : '查询',
										itemId : 'searchBtn',
										iconCls : 'common-icon-serach',
										listeners : {
											click : 'onSearchClick'
										}
									}, {
										xtype : 'button',
										iconCls : 'common_icon_save',
										text : 'Save',
										listeners : {
											click : 'onSaveClick'
										}
									}, {
										xtype : 'button',
										iconCls : 'common_icon_update',
										text : '执行操作',
										listeners : {
											click : 'onSubmitClick'
										}
									} ]
								} ],
								items : [ {
									xtype : 'container',
									layout : 'table',
									margin : '20 0 0 0 ',
									items : [ {
										xtype : 'textfield',
										fieldLabel : 'REQ',
										name : 'seq',
										labelWidth : '4'
									}, {
										xtype : 'fileuploadfield',
										buttonOnly : true,
										hideLabel : true,
										padding : '0 0 0 10',
										buttonText : 'Upload',
										name : 'fileUpload',
										itemId : 'attachmentUploadBut',
										listeners : {
											change : 'onUploadClick'
										}
									} ]
								} ]
							},
							{
								xtype : 'fieldset',
								collapsed : false,
								collapsible : true,
								title : 'Customer Info',
								items : [ {
									xtype : 'gridpanel',
									reference : 'customerGrid',
									height : 500,
									border : false,
									forceFit : true,
									bind : {
										store : '{CustomerBatch}'
									},
									viewConfig : {
										enableTextSelection : true
									},
									plugins : [ {
										ptype : 'cellediting',
										clicksToEdit : 1
									} ],
									columns : [
											{
												xtype : 'gridcolumn',
												dataIndex : 'id',
												text : 'ID'
											},
											{
												xtype : 'gridcolumn',
												dataIndex : 'status',
												text : 'STATUS'
											},
											{
												xtype : 'gridcolumn',
												dataIndex : 'dealerName',
												text : 'Dealer Name',
												editor : {
													xtype : 'textfield',
													name : 'dealerName'
												}
											},
											{
												xtype : 'gridcolumn',
												dataIndex : 'customerCode',
												text : 'Customer Code',
												editor : {
													xtype : 'textfield',
													name : 'customerCode'
												}
											},
											{
												xtype : 'gridcolumn',
												dataIndex : 'prodCode',
												text : '产品线',
												editor : {
													xtype : 'textfield',
													name : 'prodCode'
												}
											},
											{
												xtype : 'gridcolumn',
												dataIndex : 'type',
												text : '操作类型',
												editor: {
					                              	  xtype: 'combobox',
					                                  editable: false,
					                              	  displayField: 'type',
					                              	  valueField: 'id',
					                              	  name:'type',
					                              	  bind: {
					                              		  store: '{type}'
					                              	  }
					                            },
					                            renderer:function(value){
					                              	var result="";
					                              	if(value==0){
					                              		result='Open';
					                              	}else if(value==1){
					                              		result='延期';
					                              	}
					                              	return result;
					                             }
											},
											{
												xtype : 'gridcolumn',
												dataIndex : 'regDate',
												text : '延长期限',
												editor : {
													xtype : 'datefield',
													format : 'Y-m-d',
													name : 'regDate'
												},
												renderer : function(val) {
													if (val == ""
															|| val == null) {
														return "";
													}
													var dt = new Date(val);
													var update_date = Ext.Date
															.format(dt, 'Y-m-d')
													return update_date;

												}
											} ]
								} ]
							} ]
				});