
Ext.define('BPSPortal.view.help.MaterialPickerss', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.materialpickerss', // 这个组件是选择产品的组件 
    emptyText: '',
    editable: false,
    matchFieldWidth: true,

    store: Ext.create('Ext.data.Store', {
        model: 'BPSPortal.model.MaterialInfo',
        proxy: {
            type: 'direct',
            api: {
            	read:materialInfoControl.getAllLists
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

