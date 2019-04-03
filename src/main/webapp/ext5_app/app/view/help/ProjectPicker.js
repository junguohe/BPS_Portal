Ext.define('BPSPortal.view.help.ProjectPicker', {
    extend: 'Ext.form.field.Picker',
    alias: 'widget.projectpicker', // 此类的xtype类型为buttontransparent
    emptyText: '选择项目',
    editable: false,
    labelAlign: 'right',
    requires: [
               'BPSPortal.view.help.MaterialPickerViewController'
           ],
    margin: '0 0 0 10',

    codeRender: function (val) {
        return val;
    },
    controller: 'materialpicker',
    
    gridStore: Ext.create('Ext.data.Store', {
        model: 'BPSPortal.model.ProjectInfo',
        proxy: {
        	 type: 'direct',
        	    api: {
        	    	read: custprojectinfoContro.readDTO
        	    },
            reader: {
            	type: 'json',
		        rootProperty: 'records'
            }
    
    
   
        },
        autoDestroy: false
    }),

    setDisCode: function (code) {
        this.disCode = code;
    },
    setDefualtValue: function () {
        this.setValue(this.emptyText);
        this.setDisCode("*");
    },

    getDisCode: function () {
        return this.disCode;
    },

    loadData: function (data) {
        this.gridStore.clearData()
        this.gridStore.loadData(data);
    },

    getStore: function () {
        return this.gridStore;
    },

    setStore: function (store) {
        this.gridStore = store;
    },

    createPicker: function () {
        var me = this;
        var searchInput = Ext.widget('textfield', {
            xtype: '',
            fieldLabel: '搜索',
            width: 150,
            labelWidth: 30
        });

        var picker = Ext.create('Ext.grid.Panel', {
            floating: true,
            width: 100,
            minHeight: 300,
            itemId: 'pickerGrid',
            store: me.gridStore,
            listeners:{
              	itemclick: 'onProjSelected'
                },
            tbar: [searchInput, {
                xtype: 'button',
                text: '查询',
                handler: filterGridStore
            }],

            columns: [{
                dataIndex: 'projname',
                text: '项目名称',
                width: 80,
                align: 'right',
                renderer: me.codeRender
            }
            , {
                dataIndex: 'projtype',
                text: '项目类型',
                flex: 1
            }
            ]
        });

        function filterGridStore() {
            var searcher = searchInput.getValue();
            var store = me.gridStore;
            if (store) {
                store.getProxy().extraParams = { name: searcher };
                store.load({
                    callback: function () {
                        //加载完后 将 store的参数清空
                        store.proxy.extraParams = {};
                    }
                });
            }
        }
        return picker;
    }
});
