
Ext.define('BPSPortal.view.menu.MenuPanelViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.menupanel',

    control: {
    	'#': {
        	afterrender: 'onMenuAfterrender'
        }
    },
    menuAssemble: function(jsonParam, component, menuPanel) {
        Ext.each(jsonParam,function(obj,index){
            var subPanel = Ext.create('Ext.Panel',{
                title:obj.text,
              //  icon:obj.icon,
                border:0,
                border: false,
                layout :'fit',
                border: 0,
                items:[{
                    xtype:'menu',
                    border: 0,
                    border: false,
                    border:0,
                    border: false,
                    floating: false
                }]
            });
          
            //遍历第一层children,创建menuItem
            if(obj.children){
                Ext.each(obj.children,function(obj1,index1){
                    var menuItem = Ext.create('Ext.menu.Item',{
                        text:obj1.text,
                        margin:'0 25'
                    });
                    menuItem.on('click',function(){
		                  if(obj1.children.length!=0){
		                         var workSpace = Ext.create('Ext.tab.Panel',{
				                        param:obj1.children,
				                        width:600,
				                        height:400,
				                        defaults :{
				                            bodyPadding: 10
				                        },
				                        items:[
				                            {
				                                title:'子功能列表',
				                                itemId:'list'
				                            }
				                        ]
		                        });
		                         
		                    this.up('panel').up('viewport').getController().workSpaceAssemble(workSpace.param,workSpace,component);
		                }else{
		                	var path = obj1.path;
		                	var text = obj1.text;
		                	this.up('panel').up('panel').up('panel').getController().newWorkSpace(component,path,text,obj1.remark);
		                }
                    });
                    subPanel.down('menu').add(menuItem);
                });
            }
            menuPanel.add(subPanel);
        });
    },
    
    newWorkSpace:function(component,path,text,itemid){
        var className = 'BPSPortal.view.'+path;
        var contentPanel = component;
        var flag;
        var functionTab = Ext.create(className,{
        	title:text,
        	listeners: {
                viewObject: 'onViewObject'   //给每个打开的菜单tab添加监听事件
            },
        	itemId:itemid
        });
        Ext.each(contentPanel.items.items,function(obj3,index3){
            if(obj3.itemId == functionTab.itemId){
            	contentPanel.setActiveTab(obj3);
                flag = 1;
            }
        });
        if(flag!=1){
        	contentPanel.add(functionTab);
        	contentPanel.setActiveTab(functionTab);
        }
    },
    
    workSpaceAssemble: function(workSpaceParam, workSpace, component) {
        var viewCard = Ext.create('Ext.view.View',{
            store:'UsersStore',
            tpl:new Ext.XTemplate(
                '<tpl for=".">',
                '<div style="margin: 10px 10px;float:left;width:140px;height:80px;background:#6CA6CD" class="thumb-wrap" >',
                      '<span>{text}</span>',
                    '</div>',
                '</tpl>'
            ),
            itemSelector:'div.thumb-wrap'

        });
        viewCard.store.loadData(workSpaceParam);
        workSpace.down('#list').add(viewCard);
        component.down('#contentPanel').add(workSpace);
    

    viewCard.on('itemclick',function(view,record,item,index){
        if(record.data.children){
            var subViewCard = Ext.create('Ext.view.View',{
                store:'UsersStore',
                tpl:new Ext.XTemplate(
                '<tpl for=".">',
                '<div style="margin: 10px 10px;float:left;width:140px;height:80px;background:#6CA6CD" class="thumb-wrap" >',
                      '<span>{text}</span>',
                    '</div>',
                '</tpl>'
                ),
                itemSelector:'div.thumb-wrap'
            });
            subViewCard.store.loadData(record.data.children);
            workSpace.down('#list').removeAll();
            workSpace.down('#list').add(subViewCard);

        }else{
            var flag;
            var functionTab = Ext.create('Ext.Panel',{
                html:'<span>这是'+record.data.text+'的工作区</span>',
                title:record.data.text,
                itemId:index
            });
            Ext.each(workSpace.items.items,function(obj3,index3){
                if(obj3.itemId == functionTab.itemId){
                    workSpace.setActiveTab(obj3);
                    flag = 1;
                }

            });
            if(flag!=1){
            workSpace.add(functionTab);
            workSpace.setActiveTab(functionTab);
            }
        }
    });
},
    
    onMenuAfterrender: function(component, eOpts) {
    	var tabPanel = Ext.ComponentQuery.query("viewport tabpanel")[0];
   	    var menuPanel = this.getView();
   	    menuController.getMenu(function(result,status){
   	    	if(result.children.length<=0){
   	    		Ext.Msg.alert('提示', '您没有任何菜单权限');
   	        	return;
   	    	}
   	    	this.BPSPortal.view.menu.MenuPanelViewController.prototype.menuAssemble(result.children,tabPanel,menuPanel);
   	    	
   	    	
   	    })
   },
 ///点击菜单加载tab
   createTab: function (prefix, tabId, cfg) {
       var tabs = this.lookupReference('main'),
           id = prefix + '_' + tabId,
           tab = tabs.items.getByKey(id);
       if (!tab) {
           var maxCount = localStorage ? (localStorage.getItem('max_tabcount') || 100) : 100;
           var currentCount = tabs.items.getCount()
           if (currentCount < maxCount) {
               cfg.id = id;
               cfg.closable = true;
               cfg.reorderable = true,
               tab = tabs.add(cfg);
           } else {
               Ext.Msg.alert("错误", "您打开的页签数量已经超过了最大限制!\n您可以右键tab设置最大开打数.");
               return;
           }
       }
       tabs.setActiveTab(tab);
   },

   onViewObject: function (view, className, obj) {
       var dataObject = {};
       this.createTab(className, obj.id, {
           xtype: className,
           listeners: {
               editObject: 'onEditObject',
               viewObject: 'onViewObject'  //给每个打开的菜单tab添加监听事件
           },
           session: true,
           viewModel: {
               data: obj
           }
       });
   },

   //tab内的增删查改操作方法
   onEditObject: function (view, record, className) {
       record.save({
           scope: this,
           callback: function (record, op, success) {
               var obj = Ext.JSON.decode(op._response.responseText);
               Ext.toast({
                   title: 'Tips', html: obj.msg, align: 't',
                   width: 240,
                   bodyPadding: 10
               });
           }
       });
   },

   //启用禁用方法
   onActiveOrInactiveObject: function (view, record, checked) {
       if (!checked) {
           content = "确认禁用此项吗?";
       } else {
           content = "确认启用此项吗?";
       }
       Ext.Msg.confirm("确认", content, function (btn) {
           if (btn == "yes") {
               record.save({
                   scope: this,
                   callback: function (record, op, success) {
                       var obj = Ext.JSON.decode(op._response.responseText);
                       Ext.toast({
                           title: 'Tips', html: obj.msg, align: 't',
                           width: 240,
                           bodyPadding: 10
                       });
                   }
               });
           }
       });
   },

   //保存按钮执行完之后的方法
   onEditObjectComplete: function (record, op, success, view) {
       var obj = Ext.JSON.decode(op._response.responseText);
       Ext.toast({
           title: 'Tips', html: obj.msg, align: 't',
           width: 240,
           bodyPadding: 10
       });
       view.setId(className + "_" + record.data.id)
   },

   onEditFormGrid: function (view, store, record) {

   },
   onSaveParamClick:function(button){
   	var sysWin = button.up('window');
   	var passwordwinform = this.lookupReference('PassWordWinform');
   	var valid = passwordwinform.getForm().isValid();
   	if(valid){
   		passwordwinform.getForm().submit({
   			success : function(form, action) {
               	if(action.result.info == null){
               		Ext.Msg.alert("提示","修改密码成功。");
               		sysWin.close();
               	}else{
               		Ext.Msg.alert("提示",action.result.info);
               		passwordwinform.getForm().reset();
               	}
   			},
   			failure : function(form, action) {
   				Ext.Msg.alert("提示","系统错误，请联系管理员。");
   				sysWin.close();
   			}
   		}); 
   	}else{
   		Ext.Msg.alert("提示","请把字段填写完整.");
   	}
   }

});
