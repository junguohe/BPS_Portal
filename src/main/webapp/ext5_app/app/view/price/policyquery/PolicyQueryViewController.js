
Ext.define('BPSPortal.view.price.policyquery.PolicyQueryViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.policyquery',  
    
    onAfterRenderW:function(){
    	var me=this;
    	var form = me.getView().getForm();
    	me.getView().down('textfield[name="code"]').focus(true, 100)
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
            var grid = this.lookupReference('policyGrid');
            var store=grid.getStore();

            var code = btn.up('form').down('textfield[name=code]').getValue();
            var versionno = btn.up('form').down('textfield[name=versionno]').getValue();
            var materialcode = btn.up('form').down('textfield[name=materialcode]').getValue();
            var materialname = btn.up('form').down('textfield[name=materialname]').getValue();
            var isAccuracy = btn.up('form').down('checkboxfield[name=isAccuracy]').getValue();
            if(isAccuracy==true){
            	isAccuracy=1;
            }else{
            	isAccuracy=0;
            }
            var startdate = btn.up('form').down('datefield[name=startdate]').getValue();
            var enddate = btn.up('form').down('datefield[name=enddate]').getValue();      
            var status = btn.up('form').down('combobox[name=status]').getValue(); 
            var isMain = btn.up('form').down('combobox[name=isMain]').getValue();
        	var extraParams = {code:code,versionno:versionno,materialcode:materialcode,materialname:materialname,
        			isAccuracy:isAccuracy,startdate:startdate,enddate:enddate,status:status,isMain:isMain};
            	store.on("beforeload", function (store, operation, eOpts) {
        					 Ext.apply(store.proxy.extraParams, extraParams);
        					});
            	store.load();	
            
            		
        },
        onDownloadClick:function(btn){

            var grid = this.lookupReference('policyGrid');
            var store=grid.getStore();

            var code = btn.up('form').down('textfield[name=code]').getValue();
            var versionno = btn.up('form').down('textfield[name=versionno]').getValue();
            var materialcode = btn.up('form').down('textfield[name=materialcode]').getValue();
            var materialname = btn.up('form').down('textfield[name=materialname]').getValue();
            var isAccuracy = btn.up('form').down('checkboxfield[name=isAccuracy]').getValue();
            if(isAccuracy==true){
            	isAccuracy=1;
            }else{
            	isAccuracy=0;
            }
            var startdate = btn.up('form').down('datefield[name=startdate]').getValue();
            var enddate = btn.up('form').down('datefield[name=enddate]').getValue();      
            var status = btn.up('form').down('combobox[name=status]').getValue(); 
            var isMain = btn.up('form').down('combobox[name=isMain]').getValue();
            if(code == null || code == "" || code == undefined){
            	code=null
        	}
            if(versionno == null || versionno == "" || versionno == undefined){
            	versionno=null
        	}
            if(materialcode == null || materialcode == "" || materialcode == undefined){
        		materialcode=null
        	}
            if(materialname == null || materialname == "" || materialname == undefined){
            	materialname=null
        	}
            if(startdate == null || startdate == "" || startdate == undefined){
            	startdate=null
        	}
            if(enddate == null || enddate == "" || enddate == undefined){
            	enddate=null
        	}
            if(status == null || status == "" || status == undefined){
            	status=null
        	}
            if(isMain == null || isMain == "" || isMain == undefined){
            	isMain=null
        	}
        	
            	window.open("PriceStrategyDetailController/priceStrategydownload?code="+code
            			+"&versionno="+versionno+"&materialcode="+materialcode+"&materialname="+materialname+"&isAccuracy="+isAccuracy+
            			"&startdate="+startdate+"&enddate="+enddate+"&status="+status+"&isMain="+isMain);
                
            
        	}
});
