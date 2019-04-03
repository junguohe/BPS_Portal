
Ext.define('BPSPortal.view.price.applicationquery.ApplicationQueryViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.applicationquery',
    
    onAfterRenderW:function(){
    	var me=this;
    	var form = me.getView().getForm();
    	me.getView().down('textfield[name="billno"]').focus(true, 100)
    	var button = me.getView().down('button[itemId=searchBtn]');
    	this.keyNav=Ext.create('Ext.util.KeyNav',this.getView().down('form').el,{
    		enter:function(){
    			me.onSearchClick(button);
    		},
    		scope:this
    	})
    },
    //搜索
    onSearchClick: function (btn){
        var grid = this.lookupReference('specialprice');
        var store=grid.getStore();

        var billno = btn.up('form').down('textfield[name=billno]').getValue();
        var billstatus = btn.up('form').down('combobox[name=billstatus]').getValue();
        var custcode = btn.up('form').down('textfield[name=custcode]').getValue();
        var custname = btn.up('form').down('textfield[name=custname]').getValue();
        var applicator = btn.up('form').down('textfield[name=applicator]').getValue();
        var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
        var startdate = btn.up('form').down('datefield[name=startdate]').getValue();
        var enddate = btn.up('form').down('datefield[name=enddate]').getValue();      
        var materialcode = btn.up('form').down('textfield[name=materialcode]').getValue();
        var materialname = btn.up('form').down('textfield[name=materialname]').getValue();
        
        var isAccuracy = btn.up('form').down('checkboxfield[name=isAccuracy]').getValue();
        if(isAccuracy==true){
        	isAccuracy=1;
        }else{
        	isAccuracy=0;
        }
        var activedate = btn.up('form').down('datefield[name=activedate]').getValue();   
        
    	var extraParams = {billno:billno,billstatus:billstatus,custcode:custcode,custname:custname,applicator:applicator,
    			dealername:dealername,startdate:startdate,enddate:enddate,materialcode:materialcode,materialname:materialname,
    			isAccuracy:isAccuracy,publicdate:activedate
    	}
        	store.on("beforeload", function (store, operation, eOpts) {
    					 Ext.apply(store.proxy.extraParams, extraParams);
    					});
        	store.load();	
        
        		
    },
    onDownloadClick:function(btn){
//    	var extraParams = {dealername:dealername,prodid:prodid,isAccuracy:isAccuracy,materialid:materialid,
//    			mname:mname,pricea:pricea,priceb:priceb
//        };
        window.open("DowloadController/PriceDownload");
    },
    onItemDbClick:function(dataview,rec,item,index,e,eOpts){
		var vmData = {};
		vmData.data = rec;
		rec.phantom = true;
		this.fireViewEvent('viewObject', this.getView(),'approvaldetailview', vmData);
	}
});
