
Ext.define(
				'BPSPortal.view.price.pricetrial.PriceTrialViewController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.pricetrial',

					onTrailClick : function(btn) {
						var grid1 = this.lookupReference('trialGrid');
						var store = grid1.getStore();

						var dealername = btn.up('form').down('combobox[name=dealername]').getValue();
						var year =btn.up('form').down('combobox[name=year]').getValue();
				    	var month =btn.up('form').down('combobox[name=month]').getValue();
						var pricea = btn.up('form').down('textfield[name=pricea]').getValue();
						var priceb = btn.up('form').down('textfield[name=priceb]').getValue();
						if(year==null||year==''||month==null||month==''){
				    		Ext.Msg.alert('提示', '会计周期的年和月不能为空');
				        	return;
				    	}
						var period =year+'-'+month;
						if (pricea == '' || pricea == null) {
							Ext.Msg.alert('提示', '价格策略A不能为空');
							return;
						}
						if (priceb == '' || priceb == null) {
							Ext.Msg.alert('提示', '价格策略B不能为空');
							return;
						}
						var extraParams = {
							dealername : dealername,
							period : period,
							pricea : pricea,
							priceb : priceb
						};
						store.on("beforeload",
								function(store, operation, eOpts) {
									Ext.apply(store.proxy.extraParams,
											extraParams);
								}); 
						store.load();

					},
					onDownloadClick : function(btn) {
						var grid = Ext.ComponentQuery.query("viewport pricetrial grid")[0];
						var records=grid.getSelectionModel().getSelection();
						if(records.length <=0){
							Ext.Msg.alert('提示', '请选择数据');
				         	return;
						}
						var dealername = records[0].data.dealername;
						var pricea = btn.up('form').down('textfield[name=pricea]').getValue();
						var priceb = btn.up('form').down('textfield[name=priceb]').getValue();
						var year =btn.up('form').down('combobox[name=year]').getValue();
				    	var month =btn.up('form').down('combobox[name=month]').getValue();
				    	var period =year+'-'+month;
						if (pricea == '' || pricea == null) {
							Ext.Msg.alert('提示', '价格策略A不能为空');
							return;
						}
						if (priceb == '' || priceb == null) {
							Ext.Msg.alert('提示', '价格策略B不能为空');
							return;
						}
						var extraParams = {
							dealername : dealername,
							pricea : pricea,
							priceb : priceb
						};
						window.open("PriceStrategyDetailController/pricediffdownload?dealername="
										+ encodeURI(dealername)
										+ "&pricea="
										+ pricea + "&priceb=" + priceb+"&period="+period);
					},
					onDetailClick : function(btn) {
						var pricea = btn.up('form').down('textfield[name=pricea]').getValue();
						var priceb = btn.up('form').down('textfield[name=priceb]').getValue();
						var year =btn.up('form').down('combobox[name=year]').getValue();
				    	var month =btn.up('form').down('combobox[name=month]').getValue();
				    	var period =year+'-'+month;
						var grid = Ext.ComponentQuery.query("viewport pricetrial grid")[0];
						var records=grid.getSelectionModel().getSelection();
						if(records.length <=0){
							Ext.Msg.alert('提示', '请选择数据');
				         	return;
						}
						var record = grid.getSelectionModel().getSelection()[0];
						 Ext.create("BPSPortal.view.price.pricetrial.PriceTrialDetail", {
							 dealername : record.data.dealername,
			 	        	 pricea : pricea,
			 	        	 priceb : priceb,
			 	        	period:period
			 	          }).show();
					},
					onAfterRender:function(){
						var date=new Date();
						var gedt = Ext.util.Format.date(date, 'Y-m-d');
						var tarr = gedt.split('-');
						var year = tarr[0]; 
						var month =tarr[1];
						if(month.length!=2){
							month='0'+month
						}
						var year =this.lookupReference('years').setValue(year);
						var month =this.lookupReference('months').setValue(month);
					},
					onAfterRenders:function(){
						var me = this;
						var dealername = me.getView().dealername;
						var pricea = me.getView().pricea;
						var priceb = me.getView().priceb;
						
						var grid = me.lookupReference('pricetrialdetailGrid'),
						    store = me.getViewModel().getStore("pricetrialdetail");
						
						var paras = {
								dealername : dealername,
								pricea:pricea,
								priceb:priceb
						};
						Ext.apply(store.proxy.extraParams, paras);
						
						store.load();
					}
					

				
				});
