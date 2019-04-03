Ext.define('BPSPortal.view.returngoods.salesconfirm.SalesConfirmViewController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.salesconfirm',

					onAfterRenderW : function() {
						var date=new Date();
						var gedt = Ext.util.Format.date(date, 'Y-m-d');
						var tarr = gedt.split('-');
						var year = tarr[0]; 
						var month =tarr[1];
//						var months= ''+ (month - 01);
//						if(month=='01'){
//							months=''+12;
//							year=''+ (year-1);
//						}
						if(month.length!=2){
							month='0'+month
						}
						var year =this.lookupReference('years').setValue(year);
						var month =this.lookupReference('months').setValue(month);
					},
					onSearchClick : function(btn) {
						var grid = Ext.ComponentQuery
								.query("viewport salesconfirm grid")[0];
						var year =btn.up('form').down('combobox[name=year]').getValue();
				    	var month =btn.up('form').down('combobox[name=month]').getValue();
				    	var period =null;
				    	if(year!=null&&year!=''&&month!=null&&month!=''){
				    		var period =year+'-'+month;
				    	}
				    	if(year!=null&&year!=''&&month==null&&month==''){
				    		var period =year;
				    	}
				    	if(year==null&&year==''&&month==null&&month==''){
				    		var period =month;
				    	}
						var dealername = btn.up('form').down(
								'textfield[name=dealername]').getValue();
						var remark = btn.up('form').down(
						'textfield[name=remark]').getValue();
						var taskconfirm = btn.up('form').down(
								'combobox[name=taskconfirm]').getValue();
						var store = grid.getStore();
						/* 传name到后台查询 */
						var extraParams = {
							period : period,
							dealername : dealername,
							remark : remark,
							taskconfirm : taskconfirm,
							taskcontent : 'resale'
						};

						store.on("beforeload",
								function(store, operation, eOpts) {
									Ext.apply(store.proxy.extraParams,
											extraParams);
								});
						store.load({
							callback : function() {
								store.proxy.extraParams = {};
							}
						});
					},
					 onExportClicks:function(btn){
							var grid = Ext.ComponentQuery
									.query("viewport salesconfirm grid")[0];
							var year =btn.up('form').down('combobox[name=year]').getValue();
					    	var month =btn.up('form').down('combobox[name=month]').getValue();
					    	var period =null;
					    	if(year!=null&&year!=''&&month!=null&&month!=''){
					    		var period =year+'-'+month;
					    	}
					    	if(year!=null&&year!=''&&month==null&&month==''){
					    		var period =year;
					    	}
					    	if(year==null&&year==''&&month==null&&month==''){
					    		var period =month;
					    	}
							var dealername = btn.up('form').down(
									'textfield[name=dealername]').getValue();
							var remark = btn.up('form').down(
							'textfield[name=remark]').getValue();
							var taskconfirm = btn.up('form').down(
									'combobox[name=taskconfirm]').getValue();
							var type='resale';
							window.open("uploadtaskinfocontrol/UploadDownload?period="+period+'&dealername='+dealername+'&remark='+remark+'&taskconfirm='+taskconfirm+'&type='+type);
					    },
					onDeleteClick : function(button) {
						var grid = this.lookupReference('taskGrid');
						var store = grid.getStore();
						if (grid.getSelectionModel().getSelection().length <= 0) {
							Ext.Msg.alert('提示', '请选择数据');
							return;
						}
						var records = grid.getSelectionModel().getSelection()[0];
						var tid = records.data.id;
						var content = '是否删除此数据？';
						Ext.Msg.confirm("删除", content, function(btn) {
							if (btn == "yes") {
								var param = {
									tid : tid,
									type : 'resale'
								};
								Ext.MessageBox.wait("正在执行操作......", "提示");
								UploadTaskInfoController.DeleteTask(param,
										function(result) {
											if (result.success) {
												Ext.MessageBox.hide();
												Ext.toast({
													title : 'Tips',
													html : result.msg,
													align : 't',
													width : 240,
													bodyPadding : 10
												});
												store.load();
											} else {
												Ext.MessageBox.hide();
												Ext.toast({
													title : 'Tips',
													html : result.msg,
													align : 't',
													width : 240,
													bodyPadding : 10
												});
											}
										});
							}
						});
					},
					onDetailClick : function(btn) {
						var grid = this.lookupReference('taskGrid');
						var store = grid.getStore();
						var records=grid.getSelectionModel().getSelection();
						if (records.length <= 0) {
							Ext.Msg.alert('提示', '请选择数据');
							return;
						}
						if(records[0].data.taskconfirm==2){
							Ext.Msg.alert('提示', '此数据已被驳回');
							return;
						}
						var records = grid.getSelectionModel().getSelection()[0];
						var vmData = {};
						vmData.data = records;
						records.phantom = true;
						vmData.id = records.data.id;

						this.fireViewEvent('viewObject', this.getView(),'salesdetail', vmData);

					},

					onAfterRenders : function() {
						var me = this;
						var records = me.getViewModel().get('data');
						var tid = records.data.id;
						var grid = me.lookupReference('uploadresaleGrid'), store = me
								.getViewModel().getStore("uploadresale");
						var paras = {
							tid : tid
						};
						Ext.apply(store.proxy.extraParams, paras);
						store.load();

					},
					onsubmitClick : function(button) {
						var me = this;
						var dealeruploadresale = [];
						var grid = this.lookupReference('uploadresaleGrid');
						var store = grid.getStore();
						for ( var i = 0; i < store.getCount(); i++) {
							var records = Ext
									.create('BPSPortal.model.UploadReSale');

							if (store.getAt(i).data.dealerstdact == null
									|| store.getAt(i).data.dealerstdact == '') {
								Ext.Msg.alert('提示', '经销商实际采购额（未税）不能为空');
								return;
							}
							if (store.getAt(i).data.bpsstdcost == null
									|| store.getAt(i).data.bpsstdcost == '') {
								Ext.Msg.alert('提示', 'BPS标准成本价（未税）不能为空');
								return;
							}
							if (store.getAt(i).data.bpssplcost == null
									|| store.getAt(i).data.bpssplcost == '') {
								Ext.Msg.alert('提示', 'BPS特价成本价（未税 ）不能为空');
								return;
							}
//							if(store.getAt(i).data.seqno=='YXKJ201605429'){
//								console.log(store.getAt(i).data);
//							}
							records.data.errormsg = store.getAt(i).data.errormsg;
							records.data.id = store.getAt(i).data.id;
							records.data.tid = store.getAt(i).data.tid;
							records.data.region = store.getAt(i).data.region;
							records.data.dealername = store.getAt(i).data.dealername;
							records.data.period = store.getAt(i).data.period;
							records.data.seqno = store.getAt(i).data.seqno;
							records.data.custname = store.getAt(i).data.custname;
							records.data.materialname = store.getAt(i).data.materialname;
							records.data.materialcode = store.getAt(i).data.materialcode;
							records.data.batchno = store.getAt(i).data.batchno;
							records.data.qty = store.getAt(i).data.qty;
							records.data.paymenttype = store.getAt(i).data.paymenttype;
							records.data.deliverydate = store.getAt(i).data.deliverydate;
							records.data.unitprice = store.getAt(i).data.unitprice;
							records.data.istax = store.getAt(i).data.istax;
							records.data.contractamount = store.getAt(i).data.contractamount;
							records.data.isspl = store.getAt(i).data.isspl;
							records.data.dealerstdcost = store.getAt(i).data.dealerstdcost;
							records.data.dealersplcost = store.getAt(i).data.dealersplcost;
							records.data.dealerstdpo = store.getAt(i).data.dealerstdpo;
							records.data.dealerstdact = store.getAt(i).data.dealerstdact;
							records.data.rebateamount = store.getAt(i).data.rebateamount;
							records.data.bpsstdcost = store.getAt(i).data.bpsstdcost;
							records.data.bpssplcost = store.getAt(i).data.bpssplcost;
							
							records.data.rebatediff = store.getAt(i).data.rebatediff;
							records.data.currency = store.getAt(i).data.currency;
							records.data.remark = store.getAt(i).data.remark;
							dealeruploadresale.push(records.data);
						}
						var param = {
							dealeruploadresale : dealeruploadresale
						};
						Ext.MessageBox.wait("正在执行操作......", "提示");
						uploadresalecontrol.SaveOrCommit(param,function(result) {
											if (result.success) {
												Ext.MessageBox.hide();
												Ext.toast({
													title : 'Tips',
													html : result.msg,
													align : 't',
													width : 240,
													bodyPadding : 10
												});

												me.getView().close();
												var grid = Ext.ComponentQuery
														.query("viewport salesconfirm grid")[0];
												var store = grid.getStore();
												store.load();
											} else {
												Ext.MessageBox.hide();
												Ext.toast({
													title : 'Tips',
													html : result.msg,
													align : 't',
													width : 240,
													bodyPadding : 10
												});
											}
										});

					},
			        onValidateClick:function(button){
                        var grid = this.lookupReference('taskGrid');
                        var store = grid.getStore();
                        if (grid.getSelectionModel().getSelection().length <= 0) {
                            Ext.Msg.alert('提示', '请选择数据');
                            return;
                        }
                        var records = grid.getSelectionModel().getSelection()[0];

                        if(records.data.taskconfirm!=0){
                            Ext.Msg.alert('提示', '请选择待确认的数据进行操作');
                            return;
                        }

						var param = {tid:records.data.id}
                        Ext.MessageBox.wait("正在执行操作......", "提示");
                        UploadTaskInfoController.validateReseales(param,
                            function(result) {
                                Ext.MessageBox.hide();
                                if (result.success) {
                                    Ext.toast({
                                        title : 'Tips',
                                        html : result.msg,
                                        align : 't',
                                        width : 240,
                                        bodyPadding : 10
                                    });
                                } else {
                                    Ext.toast({
                                        title : 'Tips',
                                        html : result.msg,
                                        align : 't',
                                        width : 240,
                                        bodyPadding : 10
                                    });
                                }
                                store.load();
                            });
					},
					onRejectClick:function(button){
						var grid = this.lookupReference('taskGrid');
						var store = grid.getStore();
						if (grid.getSelectionModel().getSelection().length <= 0) {
							Ext.Msg.alert('提示', '请选择数据');
							return;
						}
						var records = grid.getSelectionModel().getSelection()[0];
						
						if(records.data.taskconfirm!=0){
							Ext.Msg.alert('提示', '请选择待确认的数据进行操作');
							return;
						}
						
						var tid = records.data.id;
						var content = '是否驳回此数据？';
						Ext.Msg.confirm("驳回", content, function(btn) {
							if (btn == "yes") {
		            	        Ext.create("Ext.window.Window", {
		            	        	requires: [
		            	        	           'BPSPortal.view.returngoods.salesconfirm.SalesConfirmViewController',
		            	        	       ],
		            	        	       controller: 'salesconfirm',
		            	        	title:'驳回意见',
		            	        	modal:true,
		            	        	height: 200,
		            	            width: 400,
		            	            items: [
		                                    {
		                                        xtype: 'fieldcontainer',
		                                        layout: 'column',
		                                        padding: '40 20 20 20',
		                                        fieldLabel: '',
		                                        items: [
		                	                        {
		                	                            xtype: 'textfield',
		                	                            fieldLabel: '意见',
		                	                            name: 'remark',
		                	                            reference:'remark'
		                	                        }
		                                        ]
		                                    }
		                                ],
		                                dockedItems:[
		                                             {
		                                                 xtype: 'toolbar',
		                                                 dock: 'bottom',
		                                                 items: [
		                                                     {
		                                                         xtype: 'button',
		                                                         text: '确认',
		                                                         listeners: {
		                                                             click:function(button){
		                                                            	var remark = button.up('window').down('textfield[name=remark]').getValue();
		                                                            	if (remark==null||remark=='') {
		                                        							Ext.Msg.alert('提示', '请填写意见');
		                                        							return;
		                                        						}
		                                 								var param = {
		                                 									tid : tid,
		                                 									remark:remark,
		                                 									type : 'resale'
		                                 								};
                                                                         Ext.MessageBox.wait("正在执行操作......", "提示");
		                                								UploadTaskInfoController.DeleteTask(param,
		                                										function(result) {
		                                											if (result.success) {
		                                												Ext.MessageBox.hide();
		                                												Ext.toast({
		                                													title : 'Tips',
		                                													html : result.msg,
		                                													align : 't',
		                                													width : 240,
		                                													bodyPadding : 10
		                                												});
		                                												store.load();
		                                												button.up('window').close();
		                                											} else {
		                                												Ext.MessageBox.hide();
		                                												Ext.toast({
		                                													title : 'Tips',
		                                													html : result.msg,
		                                													align : 't',
		                                													width : 240,
		                                													bodyPadding : 10
		                                												});
		                                											}
		                                										});
		                                                             }
		                                                         }
		                                                     }
		                                                 ]
		                                             }
		                                         ]
		            	          }).show();
						
							}
						});
					},
					onSaveRemarkClick:function(btn){
						var grid = this.lookupReference('taskGrid');
			    		var store = grid.getStore();
			    		var record = btn.getWidgetRecord();
			    	//	console.log(record.data.id);
			    		var param = {
									id : record.data.id,
									remark:record.data.remark
								};
			    		Ext.MessageBox.wait("正在执行操作......", "提示");
			    		UploadTaskInfoController.UploadRemark(param,
								function(result) {
									if (result.success) {
										Ext.MessageBox.hide();
										Ext.toast({
											title : 'Tips',
											html : result.msg,
											align : 't',
											width : 240,
											bodyPadding : 10
										});
										store.load();
										//button.up('window').close();
									} else {
										Ext.MessageBox.hide();
										Ext.toast({
											title : 'Tips',
											html : result.msg,
											align : 't',
											width : 240,
											bodyPadding : 10
										});
									}
								});
					}
				});
