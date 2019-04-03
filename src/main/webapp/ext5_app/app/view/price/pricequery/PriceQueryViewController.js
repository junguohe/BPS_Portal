
Ext.define('BPSPortal.view.price.pricequery.PriceQueryViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.pricequery',
    
    onAfterRenderW:function(){
	    	var me=this;
	    	var form = me.getView().getForm();
	    	me.getView().down('combobox[name="prodid"]').focus(true, 100)
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
        var grid1 = this.lookupReference('priceGrid');
        var store=grid1.getStore();
        var grid2 = this.lookupReference('specialpriceGrid');
        var store2=grid2.getStore();
        
        var materialcode = btn.up('form').down('textfield[name=materialcode]').getValue();
        var materialname = btn.up('form').down('textfield[name=materialname]').getValue();
        var isAccuracy = btn.up('form').down('checkboxfield[name=isAccuracy]').getValue();
        if(isAccuracy==true){
        	isAccuracy=1;
        }else{
        	isAccuracy=0;
        }
        var prodid = btn.up('form').down('combobox[name=prodid]').getValue();
        var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
        var startdate = btn.up('form').down('datefield[name=startdate]').getValue();
        var enddate = btn.up('form').down('datefield[name=enddate]').getValue();
        var isRelease = btn.up('form').down('combobox[name=isRelease]').getValue(); 
        var name = btn.up('form').down('textfield[name=name]').getValue();
        
        var isMain = btn.up('form').down('combobox[name=isMain]').getValue();
        var istax = btn.up('form').down('combobox[name=istax]').getValue();
        /*传name到后台查询*/
        var isSpecial = btn.up('form').down('combobox[name=isSpecial]').getValue();
        var isnotrebate = btn.up('form').down('combobox[name=isnotrebate]').getValue();
        var ispublic = btn.up('form').down('combobox[name=ispublic]').getValue();
    	var extraParams = {materialcode:materialcode,materialname:materialname,isAccuracy:isAccuracy,prodid:prodid,dealername:dealername,
        		startdate:startdate,enddate:enddate,isRelease:isRelease,name:name,isMain:isMain,isnotrebate:isnotrebate,ispublic:ispublic,istax:istax
        };
        if(isSpecial==1){
        	//特价
        	store.removeAll();
        	grid1.setHidden(true);
        	grid2.setHidden(false);
        	store2.on("beforeload", function (store2, operation, eOpts) {
				 Ext.apply(store2.proxy.extraParams, extraParams);
				});
        	store2.load();	
        	
        }else{
        	//标准价
        	store2.removeAll();
        	grid2.setHidden(true);
        	grid1.setHidden(false);
        	store.on("beforeload", function (store, operation, eOpts) {
    					 Ext.apply(store.proxy.extraParams, extraParams);
    					});
        	store.load();	
        }
        
        		
    },
 
    onDownloadClick:function(btn){
        var materialcode = btn.up('form').down('textfield[name=materialcode]').getValue();
        var materialname = btn.up('form').down('textfield[name=materialname]').getValue();
        var isAccuracy = btn.up('form').down('checkboxfield[name=isAccuracy]').getValue();
        if(isAccuracy==true){
        	isAccuracy=1;
        }else{
        	isAccuracy=0;
        }
        var prodid = btn.up('form').down('combobox[name=prodid]').getValue();
        var dealername = btn.up('form').down('textfield[name=dealername]').getValue();
        var startdate = btn.up('form').down('datefield[name=startdate]').getValue();
        var enddate = btn.up('form').down('datefield[name=enddate]').getValue();
        var isRelease = btn.up('form').down('combobox[name=isRelease]').getValue(); 
        var name = btn.up('form').down('textfield[name=name]').getValue();
        
        var isMain = btn.up('form').down('combobox[name=isMain]').getValue();

        var istax = btn.up('form').down('combobox[name=istax]').getValue();
        /*传name到后台查询*/
        var isSpecial = btn.up('form').down('combobox[name=isSpecial]').getValue();
        
    	var extraParams = {materialcode:materialcode,materialname:materialname,isAccuracy:isAccuracy,
    			prodid:prodid,dealername:dealername,startdate:startdate,enddate:enddate,isRelease:isRelease,name:name,
    			isMain:isMain
        };
    	if(materialcode == null || materialcode == "" || materialcode == undefined){
    		materialcode=null
    	}
    	if(materialname == null || materialname == "" || materialname == undefined){
    		materialname=null
    	}
    	if(prodid == null || prodid == "" || prodid == undefined){
    		prodid=null
    	}
    	if(dealername == null || dealername == "" || dealername == undefined){
    		dealername=null
    	}
    	if(startdate == null || startdate == "" || startdate == undefined){
    		startdate=null
    	}
    	if(enddate == null || enddate == "" || enddate == undefined){
    		enddate=null
    	}
    	if(isRelease == null || isRelease == "" || isRelease == undefined){
    		isRelease=null
    	}
    	if(name == null || name == "" || name == undefined){
    		name=null
    	}
        if(istax == null || istax == "" || istax == undefined){
            istax=null
        }
        if(isSpecial==1){
        	//特价
        	window.open("DowloadController/PriceDownload?materialcode="+materialcode
        			+"&materialname="+encodeURI(materialname)+"&isAccuracy="+isAccuracy+"&prodid="+prodid+"&dealername="+encodeURI(dealername)+
        			"&startdate="+startdate+"&enddate="+enddate+"&isRelease="+isRelease+"&name="+encodeURI(name)
        			+"&isMain="+isMain+"&istax="+istax);
            
        }
        
    	},
    	onInActiveClick:function(){
    		
    		var grid = this.lookupReference('specialpriceGrid');//Ext.ComponentQuery.query("viewport pricequery grid")[0];
    		var store=grid.getStore();
			if(grid.getSelectionModel().getSelection().length<=0){
				Ext.Msg.alert('提示', '请选择数据');
				return;
			}
			
			
			var record=grid.getSelectionModel().getSelection()[0];
			var did=record.data.did;
		    var spid=record.data.spid;
		    var param = {did:did,spid:spid}; 
			 priceSpecialControl.inActivePrice(param,function(result){ 
			    	if(result.success){
			    		 Ext.toast({
			                    title: 'Tips', html: result.msg, align: 't',
			                    width: 240,
			                    bodyPadding: 10
			                });
			    		 store.load();
			    	}else{
     	        		 Ext.toast({
    		                    title: 'Tips', html: result.msg, align: 't',
    		                    width: 240,
    		                    bodyPadding: 10
    		                });
    	        	}
		       	});
    	}
});
