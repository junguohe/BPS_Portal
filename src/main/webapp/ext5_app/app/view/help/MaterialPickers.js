
Ext.define('BPSPortal.view.help.MaterialPickers', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.materialpickers', // 这个组件是选择产品的组件 
    emptyText: '',
    editable: false,
    matchFieldWidth: true,

    store: Ext.create('Ext.data.Store', {
        model: 'BPSPortal.model.MaterialInfo',
        proxy: {
            type: 'direct',
            api: {
            	read:materialInfoControl.getAllList
    	    },
            reader: {
                type: 'json',
                rootProperty: 'records'
            },
            listeners: {
               // exception: app.ProxyException
            }
        }

    }),
    listConfig: {
        itemTpl: [
                  '<div data-qtip="materialname:{materialname},materialcode:{materialcode}">{materialname}—{materialcode}</div>'
		]
    },

    typeAhead: true,
    minChars: 1,
    queryMode: 'remote',
    queryParam: 'materialname',
    typeAhead: true,
    editable: true,
    queryDelay : 700
});

