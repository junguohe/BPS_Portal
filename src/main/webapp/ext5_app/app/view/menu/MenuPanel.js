Ext.define('BPSPortal.view.menu.MenuPanel',
				{
					extend : 'Ext.panel.Panel',
					alias : 'widget.menupanel',

					requires : [ 'BPSPortal.view.menu.MenuPanelViewModel',
							'BPSPortal.view.menu.MenuPanelViewController',
							'BPSPortal.view.help.HelpWindow', 'Ext.menu.Menu',
							'Ext.menu.Item' ],

					controller : 'menupanel',
					viewModel : {
						type : 'menupanel'
					},

					reference : 'menupanel',
					itemId : 'menuPanel',
					width : 250,
					border : false,
					border : 0,
					layout : 'accordion',
					collapseDirection : 'left',
					collapsed: false,
				    collapsible: true,
					title : '菜单',
//					items : [
//							{
//								xtype : 'panel',
//								layout : 'fit',
//								title : '客户管理',
//								// iconCls:'common-icon-add',
//								items : [ {
//									xtype : 'menu',
//									floating : false,
//									itemId : 'basicinfo',
//									items : [
//											{
//												xtype : 'menuitem',
//												// iconCls:'common-icon-searchs',
//												url : 'customer.customerquery.CustomerQuery',
//												itemId : 'customerquery',
//												text : '客户查询',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												hidden : true,
//												url : 'customer.customeradd.CustomerAdd',
//												itemId : 'customeradd',
//												text : '客户新增/报备',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'customer.reportapprove.ReportApprove',
//												itemId : 'reportapprove',
//												text : '报备审批',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'customer.customerupdate.CustomerUpdate',
//												itemId : 'customerupdate',
//												text : '客户信息更新',
//												focusable : true
//											} ]
//								} ]
//							},
//
//							{
//								xtype : 'panel',
//								layout : 'fit',
//								title : '价格管理',
//								items : [ {
//									xtype : 'menu',
//									floating : false,
//									itemId : 'basicinfo',
//									items : [
//											{
//												xtype : 'menuitem',
//												url : 'price.pricequery.PriceQuery',
//												itemId : 'pricequery',
//												text : '价格查询',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'price.policyquery.PolicyQuery',
//												itemId : 'policyquery',
//												text : '价格政策查询',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'price.pricepolicy.PricePolicy',
//												itemId : 'pricepolicy',
//												text : '价格政策表',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'price.pricetrial.PriceTrial',
//												itemId : 'pricetrial',
//												text : '调价试算',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'price.priceapplication.PriceApplication',
//												itemId : 'priceapplication',
//												text : '特价申请',
//												hidden : true,
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'price.applicationquery.ApplicationQuery',
//												itemId : 'applicationquery',
//												text : '特价申请查询',
//												hidden : true,
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'price.applicationapproval.ApplicationApproval',
//												itemId : 'applicationapproval',
//												text : '特价审批',
//												focusable : true
//											} ]
//								} ]
//							},
//
//							{
//								xtype : 'panel',
//								layout : 'fit',
//								title : '返货（折扣）管理',
//								items : [ {
//									xtype : 'menu',
//									floating : false,
//									itemId : 'basicinfo',
//									items : [
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.goodsquery.GoodsQuery',
//												itemId : 'goodsquery',
//												text : '返货查询',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.returninput.ReturnInput',
//												itemId : 'returninput',
//												text : '返货明细录入',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.priceinput.PriceInput',
//												itemId : 'priceinput',
//												text : '调价返货录入',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.specialpriceinput.SpecialPriceInput',
//												itemId : 'specialpriceinput',
//												text : '特价返货录入',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.executiveinput.ExecutiveInput',
//												itemId : 'executiveinput',
//												text : '执行明细录入',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.monthoperation.MonthOperation',
//												itemId : 'monthoperation',
//												text : '月结操作',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.salesconfirm.SalesConfirm',
//												itemId : 'salesconfirm',
//												text : '经销商销售接口数据确认',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.Inventory.Inventory',
//												itemId : 'Inventory',
//												text : '经销商期末库存接口数据',
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'returngoods.InventoryAdjustment.InventoryAdjustment',
//												itemId : 'InventoryAdjustment',
//												text : '经销商库存调整接口数据',
//												focusable : true
//											} ]
//								} ]
//							}, {
//								xtype : 'panel',
//								layout : 'fit',
//								title : '用户管理',
//								items : [ {
//									xtype : 'menu',
//									floating : false,
//									itemId : 'basicinfo',
//									items : [ {
//										xtype : 'menuitem',
//										url : 'user.DealerAdd',
//										itemId : 'dealeradd',
//										text : 'Dealer用户管理',
//										focusable : true
//									}, {
//										xtype : 'menuitem',
//										url : 'user.BPSAdd',
//										itemId : 'bpsadd',
//										text : 'BPS用户管理',
//										focusable : true
//									}
//
//									]
//								} ]
//							}, 
//							{
//								xtype : 'panel',
//								layout : 'fit',
//								title : '系统数据维护',
//								items : [
//									{
//										xtype : 'menu',
//										floating : false,
//										itemId : 'basicinfo',
//										items : [ 
//											{
//												xtype : 'menuitem',
//												url : 'systemadministration.prodname.Prodname',
//												itemId : 'prodname',
//												text : '产品线维护',
//												focusable : true
//											}, 
//											{
//												xtype : 'menuitem',
//												url : 'systemadministration.city.UpdateCity',
//												itemId : 'city',
//												text : '省市维护',
//												hidden:true,
//												focusable : true
//											},
//											{
//												xtype : 'menuitem',
//												url : 'systemadministration.directory.Directory',
//												itemId : 'directory',
//												text : '字典表维护',
//												focusable : true
//											}
//	
//										]
//									}
//								]
//							}
//
//					]

				});