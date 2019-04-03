
Ext.define('BPSPortal.view.price.applicationapproval.ApplicationApprovalViewController',{
	extend : 'Ext.app.ViewController',
	alias : 'controller.applicationapproval',

	   onAfterRenderW:function(){
			var me=this;
			var form = me.getView().getForm();
			me.getView().down('textfield[name="dealername"]').focus(true, 100)
			var button = me.getView().down('button[itemId=searchBtn]');
			this.keyNav=Ext.create('Ext.util.KeyNav',this.getView().down('form').el,{
				enter:function(){
					me.onSearchClick(button);
				},
				scope:this
			})
		},
	// 搜索
	onSearchClick : function(btn) {
		var grid = this.lookupReference('priceGrid');
		var store = grid.getStore();

		var dealername = btn.up('form').down(
				'textfield[name=dealername]').getValue();
		var billstatus = btn.up('form').down(
				'combobox[name=billstatus]').getValue();
		var custcode = btn.up('form').down(
				'textfield[name=custcode]').getValue();
		var custname = btn.up('form').down(
				'textfield[name=custname]').getValue();
		var billno = btn.up('form').down(
				'textfield[name=billno]').getValue();
		var materialname = btn.up('form').down(
				'textfield[name=materialname]').getValue();
		var startdate = btn.up('form').down(
				'datefield[name=startdate]').getValue();
		var enddate = btn.up('form').down(
				'datefield[name=enddate]').getValue();
		var region = btn.up('form').down(
				'textfield[name=region]').getValue();


		var extraParams = {
			dealername : dealername,
			billstatus : billstatus,
			custcode : custcode,
			custname : custname,
			billno : billno,
			materialname : materialname,
			startdate : startdate,
			enddate : enddate,
			region:region,
			status:null
		};
		store.on("beforeload",
				function(store, operation, eOpts) {
					Ext.apply(store.proxy.extraParams,
							extraParams);
				});
		store.load();

	},
	onApproveClick : function(button) {
		var grid = Ext.ComponentQuery.query("viewport applicationapproval grid")[0];
		if(grid.getSelectionModel().getSelection().length<=0){
			Ext.Msg.alert('提示', '请选择数据');
			return;
		}
		var	record =grid.getSelectionModel().getSelection()[0];
		if(record.data.billstatus==3){
			Ext.Msg.alert('提示', '此数据已被审批驳回');
			return;
		}
		if(record.data.billstatus==2){
			Ext.Msg.alert('提示', '此数据已被审批通过');
			return;
		}
		record.phantom = true;
		   if(record.data.lastcustsplinc!=null&&record.data.lastsplinc!=null){
				var num1=record.data.lastcustsplinc;
				var num2=record.data.lastsplinc;
				var num=(num1-num2);
				var count=num/num2;
					record.data.lastsugdealerprofit=(count*100);
			}
		var vmData = {};
		vmData.data = record;
		vmData.id = record.data.spid;

		this.fireViewEvent('viewObject', this.getView(),'approvaldetail', vmData);

	},
	onWaitClick:function(button){
		var grid = Ext.ComponentQuery.query("viewport applicationapproval grid")[0];
		if(grid.getSelectionModel().getSelection().length<=0){
			Ext.Msg.alert('提示', '请选择数据');
			return;
		}
		var	record =grid.getSelectionModel().getSelection()[0];

		if(record.data.billstatus==4){
			Ext.Msg.alert('提示', '此数据已是待处理状态');
			return;
		}
		if(record.data.billstatus==3){
			Ext.Msg.alert('提示', '此数据已被审批驳回');
			return;
		}
		if(record.data.billstatus==2){
			Ext.Msg.alert('提示', '此数据已被审批通过');
			return;
		}
		var spid=record.data.spid;
		 var param = {spid:spid};
		 priceSpecialControl.updatePriceStatus(param,function(result){
				if(result.success){
					 Ext.toast({
							title: 'Tips', html: result.msg, align: 't',
							width: 240,
							bodyPadding: 10
						});
				}else{
					 Ext.toast({
							title: 'Tips', html: result.msg, align: 't',
							width: 240,
							bodyPadding: 10
						});
				}
			});
	},
	onItemDbClick:function(dataview,rec,item,index,e,eOpts){
		if(rec.data.billstatus==3){
			Ext.Msg.alert('提示', '此数据已被审批驳回');
			return;
		}
		if(rec.data.billstatus==2){
			Ext.Msg.alert('提示', '此数据已被审批通过');
			return;
		}
		var vmData = {};
		vmData.data = rec;
		   if(rec.data.lastcustsplinc!=null&&rec.data.lastsplinc!=null){
				var num1=rec.data.lastcustsplinc;
				var num2=rec.data.lastsplinc;
				var num=(num1-num2);
				var count=num/num2;
					rec.data.lastsugdealerprofit=(count*100);
			}
		rec.phantom = true;
		vmData.id = rec.data.spid;
		this.fireViewEvent('viewObject', this.getView(),'approvaldetail', vmData);
	},
	//修改
	onUpdateClick:function(){
		var grid = Ext.ComponentQuery.query("viewport applicationapproval grid")[0];
		if(grid.getSelectionModel().getSelection().length<=0){
			Ext.Msg.alert('提示', '请选择数据');
			return;
		}
		var	record =grid.getSelectionModel().getSelection()[0];
		if(record.data.billstatus!=2){
			Ext.Msg.alert('提示', '只能对已经审批通过的数据进行修改');
			return;
		}
		record.phantom = true;
	//						console.log(record);
		var vmData = {};
		vmData.data = record;
		if(record.data.lastcustsplinc!=null&&record.data.lastsplinc!=null){
			var num1=record.data.lastcustsplinc;
			var num2=record.data.lastsplinc;
			var num=(num1-num2);
			var count=num/num2;
				record.data.lastsugdealerprofit=(count*100);
		}
		vmData.id = record.data.spid;
		this.fireViewEvent('viewObject', this.getView(), 'approvalupdate', vmData);
	},
	onDownloadClick:function(btn){
        var grid = this.lookupReference('priceGrid');
        var store = grid.getStore();

        var dealername = btn.up('form').down(
            'textfield[name=dealername]').getValue();
        var billstatus = btn.up('form').down(
            'combobox[name=billstatus]').getValue();
        var custcode = btn.up('form').down(
            'textfield[name=custcode]').getValue();
        var custname = btn.up('form').down(
            'textfield[name=custname]').getValue();
        var billno = btn.up('form').down(
            'textfield[name=billno]').getValue();
        var materialname = btn.up('form').down(
            'textfield[name=materialname]').getValue();
        var startdate = btn.up('form').down(
            'datefield[name=startdate]').getValue();
        var enddate = btn.up('form').down(
            'datefield[name=enddate]').getValue();
        var region = btn.up('form').down(
            'textfield[name=region]').getValue();

        window.open("DowloadController/priceApprovalDowload?dealername="+encodeURI(dealername)+"&billstatus="+billstatus +
			"&custcode="+encodeURI(custcode)+"&custname="+encodeURI(custname)+"&billno="+billno+"&materialname="+materialname +
			"&startdate="+startdate+"&region="+encodeURI(region)+"&enddate="+enddate);
	}
});
