
Ext.define('BPSPortal.view.main.MainViewViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.mainview',
  //给菜单赋点击事件
    control: {
        "menuitem": {
            click: 'onMenuitemClick'
        }
    },
    //菜单的点击事件
    onMenuitemClick: function (item, e, eOpts) {
        var nameEn = item.url;
        var code = item.itemId;
        var tabCon = Ext.ComponentQuery.query("viewport tabpanel")[0];
        var className = "BPSPortal.view." + nameEn;
        var tabOld = tabCon.getComponent('tab_' + code); //('tab_' + code);
        if (tabOld) {
            tabCon.setActiveTab(tabOld);
        } else {
        	
        	if(nameEn!=undefined){
        		 var tabNew = Ext.create(className, {
                     listeners: {
                         viewObject: 'onViewObject'   //给每个打开的菜单tab添加监听事件
                     },
                     itemId: 'tab_' + code
                 });
                 tabCon.add(tabNew);
                 tabCon.setActiveTab(tabNew);
        	}
           
        }
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
        //alert(record);
        //Ext.Msg.wait('Operation', 'Saving ...');
        record.save({
            scope: this,
            //callback: this.onEditObjectComplete
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
        //Ext.Msg.hide();
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

    ///菜单点击方法,obj是 你点击的菜单项
    onMainMenuClick: function (obj) {
        // alert(123);
        //先找到tabpanel控件
        var maincenter = Ext.ComponentQuery.query("viewport tabpanel")[0]; //this.getView().down('maincenter');
        //取到点击菜单的 id
        var tabId = "tab-" + obj.itemId;


        //根据id去tabpanel里面找，是否有这个tab
        var newTab = maincenter.getComponent(tabId);
        //如果没有tab，就开打
        if (!newTab) {
            //最大开打tab数量 存在 localStorage,默认是8
            var maxCount = localStorage ? (localStorage.getItem('max_tabcount') || 100) : 100;
            //获取已经打开多少tab数量
            var currentCount = maincenter.items.getCount()

            var xtype = obj.itemId.substr(5);
            var ERROR404 = obj.itemId.indexOf("ERROR404")
            if (currentCount < maxCount) {
                //如果找不到对应的界面 则弹出正在建设中
                //判断是否是ERROR404
                if (ERROR404 > 0) {
                    xtype = 'ERROR404';
                }
                maincenter.setActiveTab(maincenter.add({
                    xtype: xtype,  //  menu-userman
                    itemId: tabId,
                    title: obj.text,
                    closable: true,
                    reorderable: true,
                    listeners: {
                        viewObject: 'onViewObject',   //给每个打开的菜单tab添加监听事件
                        editObject: 'onEditObject',
                        activeOrInactiveObject: 'onActiveOrInactiveObject'
                    },
                    glyph: obj.glyph
                }));
            } else {
                Ext.Msg.alert("错误", "您打开的页签数量已经超过了最大限制!\n您可以右键tab设置最大开打数.");
                return;
            }
        } else {
            //如果有，那么久激活这个tab
            maincenter.setActiveTab(newTab);
        }
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
